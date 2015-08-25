package controllers;

import models.Account;
import models.BankOperation;
import models.CategoryType;
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

      BankOperation bankOperation = Form.form(BankOperation.class).bindFromRequest().get();
      account.finalBalance += bankOperation.getAmount();
      bankOperation.account = account;
      bankOperation.save();
      account.update(accountId);

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
      BankOperation oldBankOperation = BankOperation.getById(operationId);
      BankOperation bankOperation = Form.form(BankOperation.class).bindFromRequest().get();

      if (account.id == bankOperation.account.id) {
        account.finalBalance += bankOperation.getAmount() - oldBankOperation.getAmount();
        // Modification du solde dans l'objet car il est retourne au client
        bankOperation.account.finalBalance = account.finalBalance;
        bankOperation.update(operationId);
        account.update(accountId);

        return Results.ok(Json.toJson(bankOperation));
      }
      else {
        return Results.badRequest("Bank operation does not match with account");
      }
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
