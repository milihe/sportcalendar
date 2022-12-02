package org.sradar;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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

    public List<Sport> getSports() throws SQLException {
        String sql = "SELECT * FROM sports";
        List<Sport> sports = new ArrayList<>();
        try (var statement = conn.createStatement()) {
            try (var result = statement.executeQuery(sql)) {
                while (result.next()) {
                    var sport = new Sport(
                        result.getInt("sportId"),
                        result.getString("sport")
                    );
                    sports.add(sport);
                }
            }
        }
        return sports;
    }

    public List<Team> getTeams(int sportId) throws SQLException {
        String sql = "SELECT * FROM teams WHERE sportId = ? ORDER BY team";
        List<Team> teams = new ArrayList<>();
        try (var statement = conn.prepareStatement(sql)) {
            statement.setInt(1, sportId);
            try (var result = statement.executeQuery()) {
                while (result.next()) {
                    var team = new Team(
                        result.getInt("teamId"),
                        result.getInt("sportId"),
                        result.getString("team")
                    );
                    teams.add(team);
                }
            }
        }
        return teams;
    }

    public void addEvent(Instant start, int sportId, int teamId1, int teamId2) throws SQLException {
        String sql = "INSERT INTO events (start, sportId, teamId1, teamId2) VALUES (?,?,?,?)";
        try (var statement = conn.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.from(start));
            statement.setInt(2, sportId);
            statement.setInt(3, teamId1);
            statement.setInt(4, teamId2);
            statement.executeUpdate();
        }
    }
}