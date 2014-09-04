package be.simongenin.unbunker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Presale {

    static ArrayList<Presale> presales = new ArrayList<Presale>();

    private int id;
    private int compte_id;
    private int bunker_id;
    private int nb_total;
    private int nb_vendu;
    private String date_post;

    public Presale(int id, int compte_id, int bunker_id, int nb_total, int nb_vendu, String date_post) {
        this.id = id;
        this.compte_id = compte_id;
        this.bunker_id = bunker_id;
        this.nb_total = nb_total;
        this.nb_vendu = nb_vendu;
        this.date_post = date_post;    }


    public static void fillPresales(JSONArray jsr) {

        if (jsr == null) {
           Log.e("HTTP", "Pas de données!");
        } else {

            try {

                // on vide les preventes existantes
                presales.clear();

                for (int i = 0 ; i < jsr.length() ; i++) {

                    // On met un prevente par tableau sous forme :
                    // [ IdPre, Id_Compte, Id_Bunker, Nb_Total, Nb_Vendu, Date_Post ]
                    // Format date : YYYY-MM-DD HH:MM:SS
                    JSONObject pre = jsr.getJSONObject(i);

                    presales.add(
                            new Presale(pre.getInt("IdPre"), pre.getInt("Id_Compte"), pre.getInt("Id_Bunker"),
                                    pre.getInt("Nb_Total"), pre.getInt("Nb_Vendu"), pre.getString("Date_Post")));


                }

            } catch (Exception e) {
                Log.e("Presales", "Error : " + e.getMessage());
            }

        }

    }

    @Override
    public String toString() {
        return "Pre id : " + this.id + "\n" +
               "Compte id : " + this.compte_id + "\n" +
               "Bunker id : " + this.bunker_id + "\n" +
               "Nb total : " + this.nb_total + "\n" +
               "Nb venu : " + this.nb_vendu + "\n" +
               "date : " + this.date_post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompte_id() {
        return compte_id;
    }

    public void setCompte_id(int compte_id) {
        this.compte_id = compte_id;
    }

    public int getBunker_id() {
        return bunker_id;
    }

    public void setBunker_id(int bunker_id) {
        this.bunker_id = bunker_id;
    }

    public int getNb_total() {
        return nb_total;
    }

    public void setNb_total(int nb_total) {
        this.nb_total = nb_total;
    }

    public int getNb_vendu() {
        return nb_vendu;
    }

    public void setNb_vendu(int nb_vendu) {
        this.nb_vendu = nb_vendu;
    }

    public String getDate_post() {
        return date_post;
    }

    public void setDate_post(String date_post) {
        this.date_post = date_post;
    }
}
