package controllers;

import models.Account;
import models.BankOperation;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import utils.AppUtils;

import java.util.List;
import java.util.Map;

public class BankOperations extends Controller {
  public static Result getBankOperations(long accountId) {
    AppUtils.setHeaders(response());

    List<BankOperation> bankOperations = BankOperation.getByAccountId(accountId);
    return Results.ok(Json.toJson(bankOperations));
  }

  public static Result addBankOperation(long accountId) {
    AppUtils.setHeaders(response());

    try {
      Account account = Account.getById(accountId);
      Http.RequestBody b = request().body();

      Map<String, String[]> m = request().body().asFormUrlEncoded();

      Form<BankOperation> f = Form.form(BankOperation.class).bindFromRequest();
      BankOperation bankOperation = f.get();
      bankOperation.account = account;
      bankOperation.save();

      return Results.ok(Json.toJson(bankOperation));
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result editBankOperation(long accountId, long operationId) {
    AppUtils.setHeaders(response());

    try {
      Account account = Account.getById(accountId);
      BankOperation bankOperation = BankOperation.getById(operationId);

      BankOperation bankOperationForm = Form.form(BankOperation.class).bindFromRequest().get();
      // TODO Modifier bankOperation avec bankOperationForm
      bankOperation.account = account;
      bankOperation.update();

      return Results.ok(AppUtils.okJsonResponse());
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result deleteBankOperation(long accountId, long operationId) {
    AppUtils.setHeaders(response());

    BankOperation bankOperation = BankOperation.getById(operationId);
    if (bankOperation.account.id == accountId) {
      bankOperation.delete();
    }

    return Results.ok(AppUtils.okJsonResponse());
  }
}
