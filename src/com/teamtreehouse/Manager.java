package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Manager {
    private BufferedReader mReader;
    private HashMap<String, String> mMenu;
    private Set<Team> teams;
    private List<Player> availablePlayers;
    private int mTotalPlayers;
    public Manager(Player[] players) {

        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new LinkedHashMap<String, String>();
        teams = new TreeSet<Team>();
        mTotalPlayers = players.length;
        availablePlayers = new ArrayList<Player>();
        for (Player player : players) {
            availablePlayers.add(player);
        }
        Collections.sort(availablePlayers);
        buildMenu();
    }
    private String promptAction()throws IOException{
        System.out.printf("%nMain Menu: %n");
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
        mMenu.put("height report", "Print height report for a specified team.");
        mMenu.put("balance report", "Print high-level balance report.");
        mMenu.put("print roster", "Print a player roster for a specified team.");
        mMenu.put("available players", "List available players");
        mMenu.put("quit","Exit the application.");
    }
    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "create team":
                        addTeam();
                        break;
                    case "list teams":
                        listTeams();
                        break;

                    case "add player":
                        if (teams.isEmpty()) {
                            System.out.printf("Sorry, there are no teams to edit. %n");
                            break;
                        } else {
                            Team teamToEdit = chooseTeam();
                            addPlayersPrompt(teamToEdit);
                            break;
                        }
                    case "remove player":
                        if (teams.isEmpty()) {
                            System.out.printf("Sorry, there are no teams to edit. %n");
                            break;
                        } else {
                            Team teamToEdit = chooseTeam();
                            removePlayersPrompt(teamToEdit);
                            break;
                        }
                    case "available players":
                        listAvailablePlayers();
                        break;
                    case "height report":
                        if (teams.isEmpty()) {
                            System.out.printf("Sorry, there are no active teams to obtain a height report for. Try creating a new team first! %n");
                            break;
                        } else {
                            Team teamToGetRoster = chooseTeam();
                            heightReport(teamToGetRoster);
                            break;
                        }
                    case "balance report":
                        if (teams.isEmpty()) {
                            System.out.printf("Sorry, there are no active teams to obtain an experience report for. Try creating a new team first! %n");
                            break;
                        } else {
                            balanceReport();
                            break;
                        }
                    case "print roster":
                        if (teams.isEmpty()) {
                            System.out.printf("Sorry, there are no active teams to print a roster for. Try creating a new team first! %n");
                        } else {
                            Team teamToGetRoster = chooseTeam();
                            printRoster(teamToGetRoster);
                            break;
                        }
                    case "quit":
                        break;

                }
                }catch(IOException | NumberFormatException | IndexOutOfBoundsException exception){
                    //There must be a better/more efficient way of catching all possible exceptions. I attempted to at least keep them in one place.
                    if (exception instanceof IndexOutOfBoundsException) {
                        System.out.printf("Your input appears to not match any of the available options. %n");
                    } else if (exception instanceof NumberFormatException) {
                        System.out.printf("Please use only numerical digits when making selections. %n");
                    } else {
                        System.out.printf("Error parsing input! Let's try again! %n");
                    }
                }

            } while (!choice.equals("quit")) ;
        System.out.print("Goodbye!");
        }
    public void listTeams(){
        for(Team team : teams){
            System.out.printf("%s - coached by %s %n", team.getTeamName(), team.getCoachName());
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
                System.out.printf("%d.) %s - coached by - %s %n", index, team.getTeamName(), team.getCoachName());
            }
        }
        int selection = promptForIndex();
        //System.out.print(teamList);
        Team selectedTeam = teamList.get(selection);
        System.out.printf("You selected %s. %n %n", selectedTeam);
        return selectedTeam;
    }
    private int promptForIndex() throws IOException {
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        return choice - 1;
    }
    private void removePlayersPrompt(Team team) throws IOException{
        int teamPlayerIndex = 0;
        for(Player player : team.getPlayers()){
            teamPlayerIndex++;
            System.out.printf("%d.) %s - %s - %d - %s%n", teamPlayerIndex, player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        Player selectedPlayer = team.getPlayers().get(choice - 1 );
        team.removePlayer(selectedPlayer);
        availablePlayers.add(selectedPlayer);
        Collections.sort(availablePlayers);
        System.out.printf("Are you sure that you wish to remove %s %s from %s ? %n", selectedPlayer.getFirstName(), selectedPlayer.getLastName(), team.getTeamName());

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
            System.out.printf("What will the name of the team be? %n");
            String teamName = mReader.readLine();
            System.out.printf("Who will the coach be? %n");
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
            System.out.printf("%d.) %s %s | Height:%d inches | Prior experience: %s%n", availablePlayersIndex, player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
    }

    private Player selectFromAvailablePlayers()throws IndexOutOfBoundsException , IOException{
        listAvailablePlayers();
        String selection = mReader.readLine();
        int choice = Integer.parseInt(selection.trim());
        return availablePlayers.get(choice - 1);
    }
    private void balanceReport(){
        Map<Team, Set<Player>> experiencedPlayers = new TreeMap<Team,Set<Player>>();
            for (Team team : teams) {
                Set<Player> playerSet = new TreeSet<Player>();
                for (Player player : team.getPlayers()) {
                    if (player.isPreviousExperience()) {
                        playerSet.add(player);
                    }
                }
                experiencedPlayers.put(team, playerSet);
            }

        for(Map.Entry<Team, Set<Player>> entry : experiencedPlayers.entrySet()){
            float percentage = ((float) entry.getValue().size() / entry.getKey().getPlayers().size()) * 100;
            System.out.printf("%s | # of experienced players: %d/%d | %% of players that have prior experience: %.3f%% %n", entry.getKey().getTeamName() , entry.getValue().size(), entry.getKey().getPlayers().size(), percentage);
            System.out.printf("Experienced players on %s are: %n", entry.getKey().getTeamName());
            for(Player player : entry.getValue()){
                System.out.printf("%s %s | Height:%d inches | Prior experience: %s%n%n", player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
            }

        }


        }

    private void heightReport(Team team){
        Map<Team, Map<String, List<Player>>> mapOfAllTeamsByCategory = new TreeMap<Team, Map<String, List<Player>>>();
        for (Team eachTeam : teams) {
            Map<String, List<Player>> allTeamPlayers = new TreeMap<String, List<Player>>();
            List<Player> shortCategory = new ArrayList<Player>();
            List<Player> mediumCategory = new ArrayList<Player>();
            List<Player> tallCategory = new ArrayList<Player>();
            for (Player player : eachTeam.getPlayers()) {
                if (player.getHeightInInches() <= 40) {
                    shortCategory.add(player);
                } else if (player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46) {
                    mediumCategory.add(player);
                } else {
                    tallCategory.add(player);
                }

            }
            allTeamPlayers.put("35-40", shortCategory);
            allTeamPlayers.put("41-46", mediumCategory);
            allTeamPlayers.put("47-50", tallCategory);
            mapOfAllTeamsByCategory.put(eachTeam, allTeamPlayers);
        }
        Map<String, List<Player>> requestedMap = mapOfAllTeamsByCategory.get(team);
        for (Map.Entry<String, List<Player>> requestedMapEntry : requestedMap.entrySet()) {
            System.out.printf("%s Category has %d players. %n", requestedMapEntry.getKey(), requestedMapEntry.getValue().size());

        }
        //Nested map used to print results for the rest of each of the non-selected teams. This helps to give reference when determining of team heights are balanced or not.
        // Using this as an attempt at exceeding expectations (Shows a count of how many players are that height on each team.)
        mapOfAllTeamsByCategory.remove(team);
        System.out.printf("Height results for other teams: %n");
        for (Map.Entry<Team, Map<String, List<Player>>> teamEntry : mapOfAllTeamsByCategory.entrySet()) {
            for (Map.Entry<String, List<Player>> categoryEntry : teamEntry.getValue().entrySet()) {
                System.out.printf("%s : %s category has %d players. %n", teamEntry.getKey(), categoryEntry.getKey(), categoryEntry.getValue().size());
            }
        }
    }
    private void printRoster(Team team){
        System.out.printf("Team: %s %nCoached By: %s %nFull Roster: %n", team.getTeamName(), team.getCoachName());
        for(Player player : team.getPlayers()){
            System.out.printf("%s %s | Height:%d inches | Prior experience: %s%n", player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
        }
    }
}
