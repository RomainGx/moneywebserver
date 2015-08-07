package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubCategory {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 100)
  public String name;

  @ManyToOne
  @Constraints.Required
  private Category category;
}
