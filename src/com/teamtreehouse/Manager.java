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


        teams.addTeam(new Team("mark", "the fgts"));
        teams.addTeam(new Team("bitch", "the ballsacks"));

        // END BLOCK //
        int i = 0;
        System.out.println(players.length);

        //availablePlayers.addAll(players);
        availablePlayers = new ArrayList<Player>();

        for (Player player : players) {
            System.out.printf(i + " " + player.getFirstName());
            availablePlayers.add(player);
            i++;
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
        mMenu.put("add","Add a team.");
        mMenu.put("list", "List Teams");
        mMenu.put("add player", "Add a player to a Team Roster");
        mMenu.put("remove player", "Remove a player from a team roster.");
        mMenu.put("assign player", "Assign player to team.");
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
                    case "assign player":
                        assignPlayer();
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
        System.out.printf("are you sure that you wish to remove dickface from %s ? %n", team.getTeamName());

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
}
