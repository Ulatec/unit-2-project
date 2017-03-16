package com.teamtreehouse;

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
    public Manager(){

        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String,String>();
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
        mMenu.put("quit","Exit the application.");
    }

    public void run(){
        String choice ="";
        do{
            try{
                choice = promptAction();
                switch (choice) {
                    case "add team":
                        //TODO ADD NEW TEAM
                        break;
                    case "quit":
                        break;
                }
            }catch(IOException ioe){
                ioe.printStackTrace();
            }

        }while(!choice.equals("quit"));
    }
}
