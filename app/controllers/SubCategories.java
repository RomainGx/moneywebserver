package controllers;

import com.avaje.ebean.Ebean;
import models.Account;
import models.BankOperation;
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
  public static Result getSubCategory(long categoryId, long subCategoryId) {
    AppUtils.setHeaders(response());

    SubCategory subCategory = SubCategory.getById(subCategoryId);
    return Results.ok(Json.toJson(subCategory));
  }

  public static Result getBankOperations(long categoryId, long subCategoryId) {
    AppUtils.setHeaders(response());

    List<BankOperation> bankOperations = BankOperation.getBySubCategoryId(categoryId, subCategoryId);
    return Results.ok(Json.toJson(bankOperations));
  }

  public static Result editSubCategory(long categoryId, long subCategoryId) {
    AppUtils.setHeaders(response());
    boolean isMoving = request().queryString().containsKey("move");

    Ebean.beginTransaction();

    try {
      SubCategory subCategory = Form.form(SubCategory.class).bindFromRequest().get();
      subCategory.update();

      // Si la categorie a ete deplacee, on met a jour toutes les operations concernees
      if (isMoving) {
        List<BankOperation> bankOperations = BankOperation.getBySubCategoryId(categoryId, subCategoryId);
        for (BankOperation bankOperation : bankOperations) {
          bankOperation.category = subCategory.category;
          bankOperation.update();
        }
      }

      Ebean.commitTransaction();

      return Results.ok(Json.toJson(subCategory));
    }
    catch (Exception e) {
      Ebean.rollbackTransaction();
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
    finally {
      Ebean.endTransaction();
    }
  }

  public static Result addSubCategory(long categoryId) {
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

  public static Result deleteSubCategory(long categoryId, long accountId) {
    AppUtils.setHeaders(response());

    SubCategory subCategory = SubCategory.getById(accountId);
    subCategory.delete();

    return Results.ok(AppUtils.okJsonResponse());
  }
}
