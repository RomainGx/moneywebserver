package controllers;

import importation.MoneyImporter;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;

public class Importation extends Controller {
  public static Result index() {
    return ok(views.html.importation.render());
  }

  public static Result upload() {
    Http.MultipartFormData body = request().body().asMultipartFormData();
    Http.MultipartFormData.FilePart moneyFile = body.getFile("moneyFile");

    if (moneyFile != null) {
      String fileName = moneyFile.getFilename();
      String contentType = moneyFile.getContentType();
      File file = moneyFile.getFile();

      MoneyImporter.importAccountData(file);

      return ok("File uploaded");
    }
    else {
      flash("error", "Missing file");
      return redirect(routes.Importation.index());
    }
  }
}
