package models;

import com.avaje.ebean.Ebean;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Account extends Model {
  public static Model.Finder<String, Account> find = new Model.Finder<>(String.class, Account.class);

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  public String name;

  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 50)
  public String bankName;

  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 50)
  public String number;

  @Constraints.Required
  public Double startingBalance;

  @Constraints.Required
  public Double finalBalance;


  public static List<Account> getAll() {
    return Account.find.all();
  }

  public static Account getById(long accountId) {
    return Ebean.find(Account.class, accountId);
  }
}
