package importation;

import models.Account;
import models.BankOperation;
import models.Category;
import models.CategoryType;
import models.SubCategory;
import models.ThirdParty;
import utils.AppUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MoneyImporter {
  private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
  private static NumberFormat FORMATTER = NumberFormat.getInstance(Locale.US);

  private static final String ADDRESS = "A";
  private static final String CLEARED_STATUS = "C";
  private static final String DATE = "D";
  private static final String THIRD_PARTY = "P";
  private static final String CATEGORY = "L";
  private static final String AMOUNT = "T";
  private static final String NOTES = "M";
  private static final String BANK_NOTE_NUM = "N";
  private static final String END_OF_BLOC = "^";

  private File uploadedFile;
  private MoneyFile moneyFile;
  private Account account;
  private Map<String, ThirdParty> thirdParties;
  private Map<String, Category> chargeCategories;
  private Map<String, Category> creditCategories;
  private Map<Category, Map<String, SubCategory>> subCategories;


  public static void importAccountData(File uploadedFile) {
    MoneyImporter importer = new MoneyImporter(uploadedFile);
    importer.parse();
    importer.saveToDatabase();
  }

  private MoneyImporter(File uploadedFile) {
    this.uploadedFile = uploadedFile;
    thirdParties = new HashMap<>();
    chargeCategories = new HashMap<>();
    creditCategories = new HashMap<>();
    subCategories = new HashMap<>();
    moneyFile = new MoneyFile();
  }

  public void parse() {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(uploadedFile), "Cp1252"))) {
      account = parseAccount(br);
      List<BankOperation> bankOperations = parseOperations(br);

      moneyFile.setAccount(account);
      moneyFile.setThirdParties(thirdParties.values());

      Collection<Category> categories = new LinkedList<>();
      categories.addAll(chargeCategories.values());
      categories.addAll(creditCategories.values());
      moneyFile.setCategories(categories);

      Collection<SubCategory> allSubCategories = new LinkedList<>();
      for (Category category : subCategories.keySet()) {
        for (SubCategory subCategory : subCategories.get(category).values()) {
          allSubCategories.add(subCategory);
        }
      }
      moneyFile.setSubCategories(allSubCategories);

      moneyFile.setBankOperations(bankOperations);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Account parseAccount(BufferedReader br) throws IOException{
    String line;
    Account account = new Account();

    // Eat first line (type of account)
    br.readLine();

    while ((line = br.readLine()) != null) {
      String action = line.substring(0, 1);
      String value = line.substring(1);

      switch (action) {
        case DATE:
        case THIRD_PARTY:
        case CLEARED_STATUS:
          break;

        case AMOUNT:
          account.startingBalance = getAmount(value);
          account.finalBalance = account.startingBalance;
          break;

        case CATEGORY:
          value = value.substring(1, value.length() - 1);
          account.name = value;
          break;

        case END_OF_BLOC:
        default:
          return account;
      }
    }

    return account;
  }

  private List<BankOperation> parseOperations(BufferedReader br) throws IOException {
    List<BankOperation> bankOperations = new LinkedList<>();
    moneyFile.setBankOperations(bankOperations);
    BankOperation bankOperation;

    do {
      bankOperation = parseOperation(br);
      if (bankOperation != null) {
        bankOperations.add(bankOperation);
      }
    } while (bankOperation != null);

    return bankOperations;
  }

  private BankOperation parseOperation(BufferedReader br) throws IOException {
    String line;
    CategoryType type = null;
    BankOperation bankOperation = new BankOperation();
    bankOperation.account = moneyFile.getAccount();


    while ((line = br.readLine()) != null) {
      String action = line.substring(0, 1);
      String value = line.substring(1);

      switch (action) {
        case DATE:
          bankOperation.operationDate = getDate(value);
          break;

        case NOTES:
          bankOperation.notes = value;
          break;

        case AMOUNT:
          double amount = getAmount(value);

          account.finalBalance = AppUtils.round(account.finalBalance.doubleValue(), 2) + amount;

          if (amount >= 0) {
            bankOperation.credit = amount;
            type = CategoryType.CREDIT;
          }
          else {
            bankOperation.charge = -amount;
            type = CategoryType.CHARGE;
          }
          break;

        case THIRD_PARTY:
          ThirdParty thirdParty = thirdParties.get(value);

          if (thirdParty == null) {
            thirdParty = new ThirdParty();
            thirdParty.name = value;
          }
          bankOperation.thirdParty = thirdParty;
          thirdParties.put(bankOperation.thirdParty.name, bankOperation.thirdParty);
          break;

        case CATEGORY:
          String[] parts = value.split(":");
          Category category = type == CategoryType.CHARGE ? chargeCategories.get(parts[0]) : creditCategories.get(parts[0]);

          if (category == null || !category.type.equals(type)) {
            category = createCategory(type, parts[0]);
            if (type == CategoryType.CHARGE) {
              chargeCategories.put(category.name, category);
            }
            else {
              creditCategories.put(category.name, category);
            }
          }
          bankOperation.category = category;


          if (parts.length > 1) {
            SubCategory subCategory = null;
            Map<String, SubCategory> goodSubCategories = subCategories.get(category);

            if (goodSubCategories != null) {
              subCategory = goodSubCategories.get(parts[1]);
            }
            else {
              goodSubCategories = new HashMap<>();
              subCategories.put(category, goodSubCategories);
            }

            if (subCategory == null) {
              subCategory = createSubCategory(category, parts[1]);
              goodSubCategories.put(subCategory.name, subCategory);
            }
            bankOperation.subCategory = subCategory;
          }
          break;

        case BANK_NOTE_NUM:
          bankOperation.bankNoteNum = value;
          break;

        case CLEARED_STATUS:
        case ADDRESS:
          break;

        case END_OF_BLOC:
        default:
          return bankOperation;
      }
    }

    return null;
  }

  private Category createCategory(CategoryType type, String name) {
    Category category = new Category();
    category.name = name;
    category.type = type;
    category.subCategories = new LinkedList<>();

    return category;
  }

  private SubCategory createSubCategory(Category category, String name) {
    SubCategory subCategory = new SubCategory();
    subCategory.name = name;
    subCategory.category = category;

    category.subCategories.add(subCategory);

    return subCategory;
  }

  private Date getDate(String value) {
    Date date = null;

    try {
      value = value.replace("\'", "/");
      date = DATE_FORMAT.parse(value);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    return date;
  }

  private double getAmount(String value) {
    double amount = 0;

    try {
      amount = FORMATTER.parse(value).doubleValue();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    return amount;
  }

  public void saveToDatabase() {
    moneyFile.getAccount().save();

    for (ThirdParty thirdParty : moneyFile.getThirdParties()) {
      thirdParty.save();
    }

    for (Category category : moneyFile.getCategories()) {
      category.save();
    }

    for (SubCategory subCategory : moneyFile.getSubCategories()) {
      subCategory.save();
    }

    for (BankOperation bankOperation : moneyFile.getBankOperations()) {
      bankOperation.account = moneyFile.getAccount();
      bankOperation.save();
    }
  }
}
