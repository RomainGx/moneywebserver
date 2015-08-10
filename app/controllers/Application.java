package controllers;

import models.Account;
import models.BankOperation;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.AppUtils;
import views.html.index;

import java.util.List;

public class Application extends Controller {

  public static Result index() {
    AppUtils.setHeaders(response());
    return ok(index.render("Your new application is ready."));
  }

  public static Result onOptionsPreflight() {
    AppUtils.setHeaders(response());
    return ok();
  }

  public static Result onOptionsPreflightWithObjects(Long o) {
    AppUtils.setHeaders(response());
    return ok();
  }
}
