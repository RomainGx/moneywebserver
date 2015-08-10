package models;

public enum CategoryType {
  CHARGE(0),
  CREDIT(1);

  public int id;

  CategoryType(int id) {
    this.id = id;
  }
};