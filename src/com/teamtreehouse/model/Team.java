package com.teamtreehouse.model;

import java.util.*;

/**
 * Created by mark on 3/16/2017.
 */
public class Team implements Comparable<Team>{
    private List<Player> mPlayers;
    private String mCoach;
    private String mTeamName;
    public Team(String coach, String teamName){
        //mPlayers = players;
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
    public List<Player> getmPlayers(){
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
        int teamNameCmp = mTeamName.compareTo(other.mTeamName);
        if (teamNameCmp == 0) {
            return mCoach.compareTo(other.mCoach);
        }
        return teamNameCmp;
    }
}
