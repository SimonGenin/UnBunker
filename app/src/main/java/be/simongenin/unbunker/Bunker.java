package be.simongenin.unbunker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bunker {

    public static ArrayList<Bunker> bunkers = new ArrayList<Bunker>();

    private int id;
    private String name;
    // format date : YYYY-MM-DD
    private String date;

    public Bunker(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public static void fillBunkersListFromDataBase() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray dataJSON = DataBase.getData("getAllBunkers");
                Bunker.fillBunkers(dataJSON);
            }

        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void fillBunkers(JSONArray jsr) {

        if (jsr == null) {
            Log.e("HTTP", "Pas de données!");
        } else {

            try {

                // on vide les bunkers existants
                bunkers.clear();

                for (int i = 0 ; i < jsr.length() ; i++) {

                    JSONObject bunker = jsr.getJSONObject(i);

                    bunkers.add(new Bunker(bunker.getInt("Id"), bunker.getString("Nom"), bunker.getString("Date_Bunker")));

                }

            } catch (Exception e) {
                Log.e("Bunkers", "Error : " + e.getMessage());
            }

        }

    }

    @Override
    public String toString() {
        return "Bunker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
