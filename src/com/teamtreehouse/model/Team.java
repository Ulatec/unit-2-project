package com.teamtreehouse.model;

import java.util.List;

/**
 * Created by mark on 3/16/2017.
 */
public class Team {
    private List<Player> mPlayers;
    private String mCoach;
    public Team(String coach, List<Player> players){
        mPlayers = players;
        mCoach = coach;
    }
    public List<Player> getmPlayers(){
        return mPlayers;
    }
}
