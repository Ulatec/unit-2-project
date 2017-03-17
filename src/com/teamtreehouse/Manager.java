package com.teamtreehouse;

import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.TeamBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mark on 3/16/2017.
 */
public class Manager {
    private BufferedReader mReader;
    private HashMap<String, String> mMenu;
    private TeamBook teams;
    public Manager(){

        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String,String>();
        teams = new TeamBook();
        buildMenu();
    }
    private String promptAction()throws IOException{
        for(Map.Entry<String,String> option : mMenu.entrySet()){
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }
        System.out.println("What do you want to do: ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }
    private void buildMenu(){
        mMenu.put("add","Add a team.");
        mMenu.put("list", "List Teams");
        mMenu.put("quit","Exit the application.");

    }

    public void run(){
        String choice ="";
        do{
            try{
                choice = promptAction();
                switch (choice) {
                    case "add team":
                        addTeam();
                        //TODO ADD NEW TEAM
                        break;
                    case "list teams":
                        //String team = promptForTeamIndex();
                        listTeams();
                        break;
                    case "quit":
                        break;
                }
            }catch(IOException ioe){
                ioe.printStackTrace();
            }

        }while(!choice.equals("quit"));
    }
    //USED FOR DEBUGGING
    public void listTeams(){
        for(String team : teams.getTeams()){
            System.out.printf("%s - coached by - %s %n", team);
        }
    }

//    private int promptForTeamIndex(){
//        teams.getTeams();
//    }
    private void addTeam()throws IOException{
        System.out.println("What will the name of the team be?");
        String teamName = mReader.readLine();
        System.out.println("Who will the coach be?");
        String coachName = mReader.readLine();
        Team newTeam = new Team(coachName, teamName);
        teams.addTeam(newTeam);
        System.out.printf("New team %s coached by %s has been created. %n", teamName, coachName);
    }
}
