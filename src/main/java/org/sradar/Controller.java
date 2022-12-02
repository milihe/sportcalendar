package org.sradar;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        if (req.queryParams("submit") != null) {
            var localStart = LocalDateTime.parse(req.queryParams("start"));
            var instant = localStart.atZone(ZoneId.of("Europe/Vienna")).toInstant();
            var teamId1 = Integer.parseInt(req.queryParams("teamId1"));
            var teamId2 = Integer.parseInt(req.queryParams("teamId2"));
            dbAccess.addEvent(instant, sportId, teamId1, teamId2);
            res.redirect("/");
            return null;
        }

        var model = new HashMap<>();
        model.put("sports", sports);
        model.put("selectedSportId", sportId);
        model.put("teams", teams);
        return new ModelAndView(model, "templates/add-event.html");
    }
}
