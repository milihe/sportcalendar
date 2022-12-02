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

    public static ModelAndView addEvent(Request req, Response res, DbAccess dbAccess) throws SQLException {
        var sports = dbAccess.getSports();

        int sportId;
        if (req.queryParams("sportId") != null) {
            sportId = Integer.parseInt(req.queryParams("sportId"));
        } else {
            sportId = sports.get(0).getSportId();
        }

        var teams = dbAccess.getTeams(sportId);
        var model = new HashMap<>();
        model.put("sports", sports);
        model.put("selectedSportId", sportId);
        model.put("teams", teams);
        return new ModelAndView(model, "templates/add-event.html");
    }
}
