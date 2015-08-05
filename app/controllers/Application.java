package controllers;

import models.Account;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

public class Application extends Controller {

  public static Result index() {
    return ok(index.render("Your new application is ready."));
  }

  public static Result addAccount() {
    Account account = Form.form(Account.class).bindFromRequest().get();
    account.save();

    return redirect(routes.Application.index());
  }

  public static Result getAccounts() {
    List<Account> accounts = new Model.Finder(String.class, Account.class).all();
    return ok(Json.toJson(accounts));
  }
}
