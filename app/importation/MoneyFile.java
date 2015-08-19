package importation;

import models.Account;
import models.BankOperation;
import models.Category;
import models.SubCategory;
import models.ThirdParty;

import java.util.Collection;

public class MoneyFile {
  private Account account;
  private Collection<ThirdParty> thirdParties;
  private Collection<Category> categories;
  private Collection<SubCategory> subCategories;
  private Collection<BankOperation> bankOperations;


  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Collection<Category> getCategories() {
    return categories;
  }

  public void setCategories(Collection<Category> categories) {
    this.categories = categories;
  }

  public Collection<SubCategory> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(Collection<SubCategory> subCategories) {
    this.subCategories = subCategories;
  }

  public Collection<BankOperation> getBankOperations() {
    return bankOperations;
  }

  public void setBankOperations(Collection<BankOperation> bankOperations) {
    this.bankOperations = bankOperations;
  }

  public Collection<ThirdParty> getThirdParties() {
    return thirdParties;
  }

  public void setThirdParties(Collection<ThirdParty> thirdParties) {
    this.thirdParties = thirdParties;
  }
}
