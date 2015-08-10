package models;

import com.avaje.ebean.Ebean;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Category extends Model {
  public enum Type { CHARGE, CREDIT };
  public static Model.Finder<String, Category> find = new Model.Finder<>(String.class, Category.class);

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 100)
  public String name;

  @Constraints.Required
  public Type type;

  @OneToMany(mappedBy = "category")
  public List<SubCategory> subCategories;


  public static List<Category> getAll() {
    return Category.find.all();
  }

  public static List<Category> getAll(Type type) {
    return Category.find.where().eq("type", type).findList();
  }

  public static Category getById(long categoryId) {
    return Ebean.find(Category.class, categoryId);
  }
}
