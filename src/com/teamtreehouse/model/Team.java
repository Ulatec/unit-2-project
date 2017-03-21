package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable<Team>{
    private List<Player> mPlayers;
    private String mCoach;
    private String mTeamName;
    public Team(String coach, String teamName){
        mCoach = coach;
        mTeamName = teamName;
        mPlayers = new ArrayList<Player>();
    }
    public void removePlayer(Player player){
        mPlayers.remove(player);
    }
    public void addPlayer(Player player){
        mPlayers.add(player);
    }
    public List<Player> getPlayers(){
        return mPlayers;
    }
    public String getTeamName(){
        return mTeamName;
    }
    public String getCoachName(){
        return mCoach;
    }
    @Override
    public int compareTo(Team other) {
        if (equals(other)) {
            return 0;
        }
        //Use toLowerCase to ensure that Set order does not become case sensitive.
        int teamNameCmp = mTeamName.toLowerCase().compareTo(other.mTeamName.toLowerCase());
        if (teamNameCmp == 0) {
            return mCoach.compareTo(other.mCoach.toLowerCase());
        }
        return teamNameCmp;
    }
    @Override
    public String toString(){
        return mTeamName;
    }
}
