package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import play.libs.Json;
import play.mvc.Result;

public final class AppUtils {
  public static ObjectNode okJsonResponse() {
    ObjectNode result = Json.newObject();
    result.put("status", "ok");

    return result;
  }

  public static ObjectNode errorJsonResponse() {
    return errorJsonResponse(null);
  }

  public static ObjectNode errorJsonResponse(String message) {
    ObjectNode result = Json.newObject();
    result.put("status", "error");

    if (!Strings.isNullOrEmpty(message)) {
      result.put("message", message);
    }

    return result;
  }
}
