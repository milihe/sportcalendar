package org.sradar;

public class Team {
    private int teamId;
    private int sportId;
    private String team;

    public Team(int teamId, int sportId, String team) {
        this.teamId = teamId;
        this.sportId = sportId;
        this.team = team;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getSportId() {
        return sportId;
    }

    public String getTeam() {
        return team;
    }
}
