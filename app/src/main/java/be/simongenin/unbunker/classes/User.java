package be.simongenin.unbunker.classes;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import be.simongenin.unbunker.DataBase;

public class User {

    public static ArrayList<User> users = new ArrayList<User>();

    private boolean connected;

    private int id;
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String gsm;
    // Format date : YYYY-MM-DD HH-MM-SS
    private String date;
    private int etat;

    public User() {
        connected = false;
    }

    public User(int id, String name, String nickname, String password, String email, String gsm, String date, int etat) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.gsm = gsm;
        this.date = date;
        this.etat = etat;
    }

    public static String getUserNameByd(int id) {

        for (User user : User.users) {

            if (id == user.getId()) {
                StringBuilder sb = new StringBuilder();

                if (user.getNickname() != null && !user.getNickname().isEmpty() && !user.getNickname().equals("null")) {
                    sb.append(user.getNickname() + " ");
                }

                if (user.getName() != null && !user.getName().isEmpty() && !user.getName().equals("null")) {
                    sb.append(user.getName());
                }

                return new String(sb);
            }

        }

        return null;
    }

    public static void fillUsersListFromDataBase() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray dataJSON = DataBase.getData("getAllUsers");
                User.fillUsers(dataJSON);
            }

        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void fillUsers(JSONArray jsr) {

        if (jsr == null) {
            Log.e("HTTP", "Pas de données!");
        } else {

            try {

                // on vide les users existants
                users.clear();

                for (int i = 0 ; i < jsr.length() ; i++) {

                    JSONObject user = jsr.getJSONObject(i);

                    users.add(new User(user.getInt("Id"), user.getString("Nom"), user.getString("Prenom"),user.getString("Pass"), user.getString("Email"), user.getString("Gsm"), user.getString("Date_Inscription"), user.getInt("Etat")));

                }

            } catch (Exception e) {
                Log.e("Users", "Error : " + e.getMessage());
            }

        }

    }

    public void connect() {
        connected = true;
    }

    public void disconnect() {
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}
