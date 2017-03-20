package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mark on 3/16/2017.
 */
public class Team {
    private List<Player> mPlayers;
    private String mCoach;
    private String mTeamName;
    private static final int MAX_PLAYERS = 11;
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
}
