package com.teamtreehouse.model;

import java.util.List;

/**
 * Created by mark on 3/16/2017.
 */
public class Team {
    private List<Player> mPlayers;
    private String mCoach;
    private String mTeamName;
    public Team(String coach, String teamName){
        //mPlayers = players;
        mCoach = coach;
        mTeamName = teamName;
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
