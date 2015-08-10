package models;

public enum BalanceState {
  NOT_BALANCED(0),
  BALANCED(1),
  PENDING(2);

  public int id;

  BalanceState(int id) {
    this.id = id;
  }
}
