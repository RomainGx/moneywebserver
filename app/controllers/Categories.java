package controllers;

import models.Account;
import models.Category;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.AppUtils;

import java.util.List;

public class Categories extends Controller {
  public static Result getChargeCategories() {
    return getCategories(Category.Type.CHARGE);
  }

  public static Result getCreditCategories() {
    return getCategories(Category.Type.CREDIT);
  }

  private static Result getCategories(Category.Type categoryType) {
    AppUtils.setHeaders(response());

    List<Category> categories = Category.getAll(categoryType);
    return Results.ok(Json.toJson(categories));
  }

  public static Result getCategory(long categoryId) {
    AppUtils.setHeaders(response());

    Category category = Category.getById(categoryId);
    return Results.ok(Json.toJson(category));
  }

  public static Result editCategory(long categoryId) {
    AppUtils.setHeaders(response());

    if (categoryId == 0) {
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

  public static Result addCategory() {
    AppUtils.setHeaders(response());

    try {
      Category category = Form.form(Category.class).bindFromRequest().get();
      category.save();

      return Results.ok(AppUtils.okJsonResponse());
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result deleteCategory(long accountId) {
    AppUtils.setHeaders(response());

    Account account = Account.getById(accountId);
    account.delete();

    return Results.ok(AppUtils.okJsonResponse());
  }
}
