package com.teamtreehouse;

import com.teamtreehouse.model.Player;
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
    private List<Player> availablePlayers;;
    public Manager(Player[] players) {

        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String, String>();
        teams = new TeamBook();


        //USE THIS BLOCK FOR TESTING//
        teams.addTeam(new Team("Coach A", "Team A"));
        teams.addTeam(new Team("Coach B", "Team B"));
        teams.addTeam(new Team("Coach C", "Team C"));
        teams.addTeam(new Team("Coach D", "Team D"));
        // END BLOCK //


        availablePlayers = new ArrayList<Player>();
        for (Player player : players) {
            //System.out.printf(i + " " + player.getFirstName());
            availablePlayers.add(player);
        }
        Collections.sort(availablePlayers);

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
        mMenu.put("add team","Add a team.");
        mMenu.put("list teams", "List Teams");
        mMenu.put("add player", "Add a player to a Team Roster");
        mMenu.put("remove player", "Remove a player from a team roster.");
        mMenu.put("assign player", "Assign player to team.");
        mMenu.put("list all players", "List available players");
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
                    case "team report":
                        teamReport();
                        break;
                    case "add player":
                        Team teamToEdit = chooseTeam();
                        addPlayersPrompt(teamToEdit);
                        break;
                    case "remove player":
                        teamToEdit = chooseTeam();
                        removePlayersPrompt(teamToEdit);
                        break;
                    case "assign player":
                        assignPlayer();
                        break;
                    case "list teams":
                        listTeams();
                        break;
                    case "list players":
                        listAvailablePlayers();
                        break;
                    case "quit":
                        break;
                }
            }catch(IOException | NumberFormatException exception){
                if(exception instanceof NumberFormatException){
                    System.out.printf("Please use only numerical digits when making selections. %n");
                }else {
                    System.out.println("Error parsing input! Let's try again! %n");
                    exception.printStackTrace();
                }
            }

        }while(!choice.equals("quit"));
    }
    //USED FOR DEBUGGING
    public void listTeams(){
        for(String team : teams.getTeams()){
            System.out.printf("%s - coached by %n", team);
        }
    }

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
    private void removePlayersPrompt(Team team) throws IOException{
        int teamPlayerIndex = 0;
        for(Player player : team.getmPlayers()){
            teamPlayerIndex++;
            System.out.printf("%d.) %s - %s - %d - %s%n", teamPlayerIndex, player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        Player selectedPlayer = team.getmPlayers().get(choice - 1 );
        team.removePlayer(selectedPlayer);
        availablePlayers.add(selectedPlayer);
        Collections.sort(availablePlayers);
        System.out.printf("are you sure that you wish to remove %s %s from %s ? %n", selectedPlayer.getFirstName(), selectedPlayer.getLastName(), team.getTeamName());

    }
    private void addPlayersPrompt(Team team) throws IOException{
        Player selectedPlayer = selectFromAvailablePlayers();

        team.addPlayer(selectedPlayer);
        availablePlayers.remove(selectedPlayer);
//        Player tempPlayer = availablePlayers.
        System.out.printf("You have added %s %sto the team %s %n", selectedPlayer.getFirstName(), selectedPlayer.getLastName(), team.getTeamName());
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
        int availablePlayersIndex = 0;
        System.out.println("availablePlayers: " + availablePlayers.size());
        for(Player player : availablePlayers){
            availablePlayersIndex++;
            System.out.printf("%d.) %s %s height:%d  has prior experience: %s%n", availablePlayersIndex, player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
    }

    private Player selectFromAvailablePlayers()throws IOException{
        listAvailablePlayers();
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        Player selectedPlayer = availablePlayers.get(choice - 1 );
        return selectedPlayer;
    }
    private void assignPlayer() throws IOException{
        Player toBeAssigned = selectFromAvailablePlayers();
        Team toBeAddedTo = chooseTeam();
        toBeAddedTo.addPlayer(toBeAssigned);
        availablePlayers.remove(toBeAssigned);
        System.out.printf("%s %s has been assigned to %s. %n", toBeAssigned.getFirstName(), toBeAssigned.getLastName(), toBeAddedTo.getTeamName() );
    }
    private void printRoster(){

    }
    private void teamReport()throws IOException{
        Team teamToReport = chooseTeam();
        List<Player> teamRoster = new ArrayList<>();
        for(Player player : teamToReport.getmPlayers()){
            teamRoster.add(player);
        }
        teamRoster.sort(new Comparator<Player>(){
            @Override
            public int compare(Player player1, Player player2){
                if (player1.equals(player2)) {
                    return 0;
                }
                if(player1.getHeightInInches() == (player2.getHeightInInches())){
                    return 0;
                }
                if(player1.getHeightInInches() > player2.getHeightInInches()){
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
        List<Player> category1 = new ArrayList<>();
        List<Player> category2 = new ArrayList<>();
        List<Player> category3 = new ArrayList<>();
        for(Player player : teamRoster){
            if(player.getHeightInInches() <= 40){
                category1.add(player);
                //teamRoster.remove(player);
            }else if(player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46){
                    category2.add(player);
                }
                else{
                category3.add(player);
            }
        }
        System.out.printf("There are %d players in category1, %d in category2, and %d in category3 %n", category1.size(), category2.size(), category3.size());


        for(Player player : teamRoster){
            System.out.printf("%s %s height:%d  has prior experience: %s%n", player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }




    }
}
