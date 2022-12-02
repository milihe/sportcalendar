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

        String error = null;
        if (req.queryParams("submit") != null) {
            try {
                if (req.queryParams("start").isEmpty()) {
                    throw new IllegalArgumentException("Start is not selected");
                }
                var localStart = LocalDateTime.parse(req.queryParams("start"));
                var start = localStart.atZone(ZoneId.of("Europe/Vienna")).toInstant();
                int teamId1 = Integer.parseInt(req.queryParams("teamId1"));
                int teamId2 = Integer.parseInt(req.queryParams("teamId2"));
                if (teamId1 == teamId2) {
                    throw new IllegalArgumentException("Same team cannot be selected twice");
                }
                dbAccess.addEvent(start, sportId, teamId1, teamId2);
                res.redirect("/");
                return null;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
        }

        var model = new HashMap<>();
        model.put("sports", sports);
        model.put("selectedSportId", sportId);
        model.put("teams", teams);
        model.put("error", error);
        return new ModelAndView(model, "templates/add-event.html");
    }
}
