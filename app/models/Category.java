package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 100)
  public String name;

  @OneToMany(mappedBy = "category")
  public List<SubCategory> subCategories;
}
