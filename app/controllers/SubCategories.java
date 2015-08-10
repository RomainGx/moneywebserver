package controllers;

import models.Account;
import models.Category;
import models.CategoryType;
import models.SubCategory;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.AppUtils;

import java.util.List;

public class SubCategories extends Controller {
  public static Result getSubCategory(long subCategoryId) {
    AppUtils.setHeaders(response());

    SubCategory subCategory = SubCategory.getById(subCategoryId);
    return Results.ok(Json.toJson(subCategory));
  }

  public static Result editSubCategory(long subCategoryId) {
    AppUtils.setHeaders(response());

    if (subCategoryId == 0) {
      try {
        SubCategory subCategory = Form.form(SubCategory.class).bindFromRequest().get();
        subCategory.save();

        return Results.ok(AppUtils.okJsonResponse());
      }
      catch (Exception e) {
        e.printStackTrace();
        return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
      }
    }

    return Results.ok(AppUtils.okJsonResponse());
  }

  public static Result addSubCategory() {
    AppUtils.setHeaders(response());

    try {
      SubCategory subCategory = Form.form(SubCategory.class).bindFromRequest().get();
      subCategory.save();

      return Results.ok(Json.toJson(subCategory));
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result deleteSubCategory(long accountId) {
    AppUtils.setHeaders(response());

    SubCategory subCategory = SubCategory.getById(accountId);
    subCategory.delete();

    return Results.ok(AppUtils.okJsonResponse());
  }
}
