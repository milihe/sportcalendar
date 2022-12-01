package org.sradar;

public class Sport {
    private int sportId;
    private String sport;

    public Sport(int sportId, String sport) {
        this.sportId = sportId;
        this.sport = sport;
    }

    public int getSportId() {
        return sportId;
    }

    public String getSport() {
        return sport;
    }
}
