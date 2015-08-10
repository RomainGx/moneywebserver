package controllers;

import models.Account;
import models.Category;
import models.CategoryType;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.AppUtils;

import java.util.List;

public class Categories extends Controller {
  public static Result getChargeCategories() {
    return getCategories(CategoryType.CHARGE);
  }

  public static Result getCreditCategories() {
    return getCategories(CategoryType.CREDIT);
  }

  private static Result getCategories(CategoryType categoryType) {
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
        Category category = Form.form(Category.class).bindFromRequest().get();
        category.save();

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

      return Results.ok(Json.toJson(category));
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result deleteCategory(long categoryId) {
    AppUtils.setHeaders(response());

    Category category = Category.getById(categoryId);
    category.delete();

    return Results.ok(AppUtils.okJsonResponse());
  }
}
