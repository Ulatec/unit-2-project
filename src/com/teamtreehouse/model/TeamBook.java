package com.teamtreehouse.model;

import java.util.*;

/**
 * Created by mark on 3/16/2017.
 */
public class TeamBook {
    private Map<String, Team> mTeams;
    public TeamBook(){
        mTeams = new HashMap<String, Team>();
    }
    public void addTeam(Team team){
        mTeams.put(team.getTeamName(), team);
    }
    public Set<String> getTeams(){
        Set<String> teamNames = new TreeSet<String>();
        for(String team : mTeams.keySet()){
            teamNames.add(team);
        }
        return teamNames;
    }
    public String getTeamCoach(String teamName){
        return mTeams.get(teamName).getCoachName();
    }
    public Team getTeamFromName(String teamName){
        return mTeams.get(teamName);
    }
}
