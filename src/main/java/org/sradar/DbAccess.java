package org.sradar;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbAccess {
    private Connection conn;

    public DbAccess(Connection connection) {
        this.conn = connection;
    }

    public List<Event> getEvents() throws SQLException {
        String sql = """
            SELECT eventId, start, s.sport, t1.team team1, t2.team team2
            FROM events e
            JOIN sports s ON s.sportId = e.sportId
            JOIN teams t1 ON t1.teamId = e.teamId1
            JOIN teams t2 ON t2.teamId = e.teamId2
            """;
        List<Event> events = new ArrayList<>();
        try (var statement = conn.createStatement()) {
            try (var result = statement.executeQuery(sql)) {
                while (result.next()) {
                    var event = new Event(
                        result.getInt("eventId"),
                        result.getTimestamp("start").toInstant(),
                        result.getString("sport"),
                        result.getString("team1"),
                        result.getString("team2")
                    );
                    events.add(event);
                }
            }
        }
        return events;
    }
}