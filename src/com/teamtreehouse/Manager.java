package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.TeamBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by mark on 3/16/2017.
 */
public class Manager {
    private BufferedReader mReader;
    private HashMap<String, String> mMenu;
    private TeamBook teams;
    private Set<Player> availablePlayers;
    public Manager(Player[] players){

        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String,String>();
        teams = new TeamBook();
        //USE THIS BLOCK FOR TESTING//


        teams.addTeam(new Team("mark", "the fgts" ));
        teams.addTeam(new Team("bitch", "the ballsacks"));

        // END BLOCK //
        int i = 0;
        System.out.println(players.length);
        availablePlayers = new TreeSet<>();
        for(Player player : players){

            System.out.printf(i + " " + player.getFirstName());
            availablePlayers.add(player);
            i++;
        }

        buildMenu();


    }
    private String promptAction()throws IOException{
        System.out.printf("Main Menu: %n");
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
        mMenu.put("add player", "Add a player to a Team Roster");
        mMenu.put("remove player", "Remove a player from a team roster.");
        mMenu.put("list players", "List available players");
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

                    case "add player":
                        Team teamToEdit = chooseTeam();
                        addPlayersPrompt(teamToEdit);
                        break;
                    case "remove player":
                        teamToEdit = chooseTeam();
                        removePlayersPrompt(teamToEdit);
                        break;
                    case "list teams":
                        //String team = promptForTeamIndex();
                        listTeams();
                        break;
                    case "list players":
                        listAvailablePlayers();
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
    private Team chooseTeam() throws IOException{
        List<String> teamList = new ArrayList<String>();
        int index = 0;
        if(teams.getTeams().size() == 0){
            System.out.printf("Sorry, there are no teams to edit. %n");
        }else {
            System.out.printf("Available teams to edit: %n");
            for (String team : teams.getTeams()) {
                teamList.add(team);
                index++;
                System.out.printf("%d. ) %s - coached by - %s %n", index, team, teams.getTeamCoach(team));
            }
        }
        int selection = promptForIndex();
        //System.out.print(teamList);
        String selectedTeam = teamList.get(selection);
        System.out.printf("You selected %s %n", selectedTeam);
        return teams.getTeamFromName(selectedTeam);
    }
    private int promptForIndex() throws IOException {
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        return choice - 1;
    }
    private void removePlayersPrompt(Team team){
        System.out.printf("are you sure that you wish to remove dickface from %s ? %n", team.getTeamName());

    }
    private void addPlayersPrompt(Team team){
        listAvailablePlayers();
        System.out.printf("are you sure that you wish to add dickface from %s ? %n", team.getTeamName());
    }
    private void addTeam()throws IOException{
        System.out.println("What will the name of the team be?");
        String teamName = mReader.readLine();
        System.out.println("Who will the coach be?");
        String coachName = mReader.readLine();
        Team newTeam = new Team(coachName, teamName);
        teams.addTeam(newTeam);
        System.out.printf("New team %s coached by %s has been created. %n", teamName, coachName);
    }

    private void listAvailablePlayers(){
        System.out.println("availablePlayers: " + availablePlayers.size());
        for(Player player : availablePlayers){
            System.out.printf("%s - %s - %d - %s%n", player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
    }
}
