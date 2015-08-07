package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ThirdParty {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public long id;

  @Constraints.Required
  @Constraints.MinLength(value = 2)
  @Constraints.MaxLength(value = 200)
  public String name;
}
