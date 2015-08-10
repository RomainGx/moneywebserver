package models;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubCategory extends Model {
  public static Model.Finder<String, SubCategory> find = new Model.Finder<>(String.class, SubCategory.class);

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 100)
  public String name;

  @ManyToOne
  @Constraints.Required
  @JsonIgnore
  public Category category;


  public static SubCategory getById(long subCategoryId) {
    return Ebean.find(SubCategory.class, subCategoryId);
  }
}
