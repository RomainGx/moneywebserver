package models;

import com.avaje.ebean.Ebean;
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
  public ThirdParty thirdParty;

  @Min(value = 0)
  public Double charge;

  @Min(value = 0)
  public Double credit;

  @ManyToOne
  @Constraints.Required
  public Category category;

  @ManyToOne
  public SubCategory subCategory;

  @Constraints.MaxLength(value = 500)
  public String notes;


  public BankOperation() {
    balanceState = BalanceState.NOT_BALANCED;
  }

  /**
   * @return Montant de l'operation (negatif si c'est une charge, positif si c'est un credit).
   */
  public Double getAmount() {
    return charge != null ? -charge : credit;
  }

  public static BankOperation getById(long operationId) {
    return Ebean.find(BankOperation.class, operationId);
  }

  public static List<BankOperation> getByAccountId(long accountId) {
    return BankOperation.find.where().eq("account.id", accountId).orderBy("operationDate ASC, id ASC").findList();
  }
}
