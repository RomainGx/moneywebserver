package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Account;
import models.BankOperation;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.AppUtils;
import views.html.index;

import java.util.List;

public class Application extends Controller {

  public static Result index() {
    return ok(index.render("Your new application is ready."));
  }

  public static Result addAccount() {
    try {
      Account account = Form.form(Account.class).bindFromRequest().get();
      account.save();

      return ok(AppUtils.okJsonResponse());
    }
    catch (Exception e) {
      e.printStackTrace();
      return internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result getAccounts() {
    List<Account> accounts = new Model.Finder<>(String.class, Account.class).all();
    return ok(Json.toJson(accounts));
  }

  public static Result getOperations(long accountId) {
    List<BankOperation> bankOperations = BankOperation.getByAccountId(accountId);
    return ok(Json.toJson(bankOperations));
  }
}
