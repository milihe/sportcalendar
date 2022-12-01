package org.sradar;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;

public class Controller {
    public static ModelAndView getEvents(Request req, Response res, DbAccess dbAccess) throws SQLException {
        var events = dbAccess.getEvents();
        var model = new HashMap<>();
        model.put("events", events);
        return new ModelAndView(model, "templates/get-events.html");
    }
}
