package org.sradar;

import java.time.Instant;

public class Event {
    private int eventId;
    private Instant start;
    private String sport;
    private String team1;
    private String team2;

    public Event(int eventId, Instant start, String sport, String team1, String team2) {
        this.eventId = eventId;
        this.start = start;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
    }

    public int getEventId() {
        return eventId;
    }

    public Instant getStart() {
        return start;
    }

    public String getSport() {
        return sport;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }
}