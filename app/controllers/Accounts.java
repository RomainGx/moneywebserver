package controllers;

import models.Account;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.AppUtils;

import java.util.List;

public class Accounts extends Controller {
  public static Result getAccounts() {
    AppUtils.setHeaders(response());

    List<Account> accounts = Account.getAll();
    return Results.ok(Json.toJson(accounts));
  }

  public static Result getAccount(long accountId) {
    AppUtils.setHeaders(response());

    Account account = Account.getById(accountId);
    return Results.ok(Json.toJson(account));
  }

  public static Result editAccount(long accountId) {
    AppUtils.setHeaders(response());

    if (accountId == 0) {
      try {
        Account account = Form.form(Account.class).bindFromRequest().get();
        account.save();

        return Results.ok(AppUtils.okJsonResponse());
      }
      catch (Exception e) {
        e.printStackTrace();
        return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
      }
    }

    return Results.ok(AppUtils.okJsonResponse());
  }

  public static Result addAccount() {
    AppUtils.setHeaders(response());

    try {
      Account account = Form.form(Account.class).bindFromRequest().get();
      account.save();

      return Results.ok(AppUtils.okJsonResponse());
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result deleteAccount(long accountId) {
    AppUtils.setHeaders(response());

    Account account = Account.getById(accountId);
    account.delete();

    return Results.ok(AppUtils.okJsonResponse());
  }
}
