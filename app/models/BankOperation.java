package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Entity
public class BankOperation extends Model {
  public static Model.Finder<String, BankOperation> find = new Model.Finder<>(String.class, BankOperation.class);

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @ManyToOne
  @Constraints.Required
  public Account account;

  @Constraints.MaxLength(50)
  public String bankNoteNum;

  @Constraints.Required
  @Formats.DateTime(pattern = "dd/MM/yyyy")
  public Date operationDate;

  @Constraints.Required
  public BalanceState balanceState;

  @ManyToOne
  @Constraints.Required
  public ThirdParty payee;

  @Min(value = 0)
  private Double charge;

  @Min(value = 0)
  private Double credit;

  @ManyToOne
  @Constraints.Required
  private Category category;

  @ManyToOne
  private SubCategory subCategory;

  @Constraints.MaxLength(value = 500)
  private String notes;


  public BankOperation() {
    balanceState = BalanceState.NOT_BALANCED;
  }

  public static List<BankOperation> getByAccountId(long accountId) {
    return BankOperation.find.where().eq("account.id", accountId).findList();
  }
}
