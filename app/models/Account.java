package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account extends Model {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;

  @Constraints.Required
  public String name;


  public long getId() {
    return id;
  }
}
