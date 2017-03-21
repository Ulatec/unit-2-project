package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

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
    private Set<Team> teams;
    private List<Player> availablePlayers;
    private int mTotalPlayers;
    public Manager(Player[] players) {

        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new LinkedHashMap<String, String>();
        //teams = new TeamBook();
        teams = new TreeSet<Team>();
        mTotalPlayers = players.length;

        //USE THIS BLOCK FOR TESTING//
//        teams.add(new Team("Coach A", "Team A"));
//        teams.add(new Team("Coach B", "Team B"));
//        teams.add(new Team("Coach C", "Team C"));
//        teams.add(new Team("Coach D", "Team D"));
        // END BLOCK //

        availablePlayers = new ArrayList<Player>();
        for (Player player : players) {
            availablePlayers.add(player);
        }
        Collections.sort(availablePlayers);
        buildMenu();
    }
    private String promptAction()throws IOException{
        System.out.printf("%n Main Menu: %n");
        for(Map.Entry<String,String> option : mMenu.entrySet()){
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }
        System.out.println("What do you want to do: ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }
    private void buildMenu(){
        //LinkedHashMap to maintain menu order.
        mMenu.put("create team","Create a team.");
        mMenu.put("list teams", "List Teams");
        mMenu.put("add player", "Add a player to a Team Roster");
        mMenu.put("remove player", "Remove a player from a team roster.");
        mMenu.put("experience report", "Print high-level experience report.");
        mMenu.put("list all players", "List available players");
        mMenu.put("quit","Exit the application.");
    }
    public void run(){
        String choice ="";
        do{
            try{
                choice = promptAction();
                switch (choice) {
                    case "create team":
                        addTeam();
                        break;
                    case "team report":
                        heightReport();
                        break;
                    case "add player":
                        if(teams.isEmpty()){
                            System.out.printf("Sorry, there are no teams to edit. %n");
                            break;
                        }else {
                            Team teamToEdit = chooseTeam();
                            addPlayersPrompt(teamToEdit);
                            break;
                        }
                    case "remove player":
                        if(teams.isEmpty()){
                            System.out.printf("Sorry, there are no teams to edit. %n");
                            break;
                        }else {
                            Team teamToEdit = chooseTeam();
                            removePlayersPrompt(teamToEdit);
                            break;
                        }
                    case "list teams":
                        listTeams();
                        break;
                    case "list players":
                        listAvailablePlayers();
                        break;
                    case "experience report":
                        experienceReport();
                        break;
                    case "quit":
                        break;
                }
            }catch(IOException | NumberFormatException | IndexOutOfBoundsException exception){
                if(exception instanceof IndexOutOfBoundsException){
                    System.out.printf("Your input appears to not match any of the available options. %n");
                }else if(exception instanceof NumberFormatException){
                    System.out.printf("Please use only numerical digits when making selections. %n");
                }else {
                    System.out.println("Error parsing input! Let's try again! %n");
                }
            }

        }while(!choice.equals("quit"));
    }
    //USED FOR DEBUGGING
    public void listTeams(){
        for(Team team : teams){
            System.out.printf("%s - coached by %n", team.getTeamName(), team.getCoachName());
        }
    }

    private Team chooseTeam() throws IOException{
        List<Team> teamList = new ArrayList<Team>();
        int index = 0;
        if(teams.size() == 0){
            System.out.printf("Sorry, there are no teams to edit. %n");
        }else {
            System.out.printf("Available teams to edit: %n");
            for (Team team : teams) {
                teamList.add(team);
                index++;
                System.out.printf("%d. ) %s - coached by - %s %n", index, team.getTeamName(), team.getCoachName());
            }
        }
        int selection = promptForIndex();
        //System.out.print(teamList);
        Team selectedTeam = teamList.get(selection);
        System.out.printf("You selected %s %n", selectedTeam);
        return selectedTeam;
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
        System.out.printf("You have added %s %s to the team: %s %n", selectedPlayer.getFirstName(), selectedPlayer.getLastName(), team.getTeamName());
    }
    private void addTeam()throws IOException{
        if(teams.size() >= mTotalPlayers){
            System.out.printf("Sorry, you are unable to create anymore teams. There are no longer enough players to fill the teams. %n");
        }else {
            System.out.println("What will the name of the team be?");
            String teamName = mReader.readLine();
            System.out.println("Who will the coach be?");
            String coachName = mReader.readLine();
            Team newTeam = new Team(coachName, teamName);
            teams.add(newTeam);
            System.out.printf("New team %s coached by %s has been created. %n", teamName, coachName);
        }
    }
    private void listAvailablePlayers(){
        int availablePlayersIndex = 0;
        System.out.println("Number of players available for assignment: " + availablePlayers.size());
        for(Player player : availablePlayers){
            availablePlayersIndex++;
            System.out.printf("%d.) %s %s | Height:%d | Prior experience: %s%n", availablePlayersIndex, player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
    }

    private Player selectFromAvailablePlayers()throws IndexOutOfBoundsException , IOException{
        listAvailablePlayers();
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        return availablePlayers.get(choice - 1);
    }
    private void experienceReport(){
        for(Team team : teams){
            int numberOfExperiencedPlayers = 0;
            for(Player player : team.getmPlayers()){
                if(player.isPreviousExperience()){
                    numberOfExperiencedPlayers++;
                }
            }
            float percentage = ((float) numberOfExperiencedPlayers / team.getmPlayers().size())*100;
            System.out.printf("%s | # of experienced players: %d | %% of players that have prior experience: %.3f%% %n", team.getTeamName(), numberOfExperiencedPlayers, percentage );
        }


    }
    private void heightReport()throws IOException{
        Team teamToReport = chooseTeam();
        Comparator<Player> heightComparator = new Comparator<Player>(){
            @Override
            public int compare(Player player1, Player player2) {
                if (player1.equals(player2)) {
                    return 0;
                }
                if (player1.getHeightInInches() == (player2.getHeightInInches())) {
                    return 0;
                }
                if (player1.getHeightInInches() > player2.getHeightInInches()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Map<String, List<Player>> allTeamPlayers = new TreeMap<String, List<Player>>();
        List<Player> shortCategory = new ArrayList<Player>();
        List<Player> mediumCategory = new ArrayList<Player>();
        List<Player> tallCategory = new ArrayList<Player>();
        for(Player player : teamToReport.getmPlayers()){
            if(player.getHeightInInches() <= 40){
                shortCategory.add(player);
            }else if(player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46){
                    mediumCategory.add(player);
                }
                else{
                tallCategory.add(player);
            }
           
        }
		allTeamPlayers.put("40 and below", shortCategory);
		allTeamPlayers.put("41-46", mediumCategory);
		allTeamPlayers.put("47 and above", tallCategory);
		for(Map.Entry<String,List<Player>> entry : allTeamPlayers.entrySet()){
		    entry.getValue().sort(heightComparator);
			System.out.printf("%s has %d player(s), they are:  %n", entry.getKey(), entry.getValue().size());
			for(Player player : entry.getValue()){
                System.out.printf("%s %s | Height: %d inches %n", player.getFirstName(), player.getLastName(), player.getHeightInInches());
            }
		}
    }
}
