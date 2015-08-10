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
public class ThirdParty extends Model {
  public static Model.Finder<String, ThirdParty> find = new Model.Finder<>(String.class, ThirdParty.class);

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 200)
  public String name;


  public static List<ThirdParty> getAll() {
    return ThirdParty.find.all();
  }

  public static ThirdParty getById(long thirdPartyId) {
    return Ebean.find(ThirdParty.class, thirdPartyId);
  }
}
