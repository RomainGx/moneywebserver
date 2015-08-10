package controllers;

import models.ThirdParty;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import utils.AppUtils;

import java.util.List;

public class ThirdParties extends Controller {
  public static Result getThirdParties() {
    AppUtils.setHeaders(response());

    List<ThirdParty> thirdParties = ThirdParty.getAll();
    return Results.ok(Json.toJson(thirdParties));
  }

  public static Result getThirdParty(long thirdPartyId) {
    AppUtils.setHeaders(response());

    ThirdParty thirdParty = ThirdParty.getById(thirdPartyId);
    return Results.ok(Json.toJson(thirdParty));
  }

  public static Result editThirdParty(long thirdPartyId) {
    AppUtils.setHeaders(response());

    if (thirdPartyId == 0) {
      try {
        ThirdParty thirdParty = Form.form(ThirdParty.class).bindFromRequest().get();
        thirdParty.save();

        return Results.ok(AppUtils.okJsonResponse());
      }
      catch (Exception e) {
        e.printStackTrace();
        return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
      }
    }

    return Results.ok(AppUtils.okJsonResponse());
  }

  public static Result addThirdParty() {
    AppUtils.setHeaders(response());

    try {
      ThirdParty thirdParty = Form.form(ThirdParty.class).bindFromRequest().get();
      thirdParty.save();

      return Results.ok(Json.toJson(thirdParty));
    }
    catch (Exception e) {
      e.printStackTrace();
      return Results.internalServerError(AppUtils.errorJsonResponse(e.getMessage()));
    }
  }

  public static Result deleteThirdParty(long thirdPartyId) {
    AppUtils.setHeaders(response());

    ThirdParty thirdParty = ThirdParty.getById(thirdPartyId);
    thirdParty.delete();

    return Results.ok(AppUtils.okJsonResponse());
  }
}
