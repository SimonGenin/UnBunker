package be.simongenin.unbunker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataBase {

    // local
    public static String BASE_URL = "http://10.0.3.2/unbunkerandroid/";

    public static JSONArray getData(String scriptName)  {

        int responseCode = -1;
        JSONArray jsr = null;


        try {
            URL feedURL = new URL(BASE_URL + scriptName + ".php");
            HttpURLConnection connection = (HttpURLConnection) feedURL.openConnection();
            connection.connect();

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = connection.getInputStream();
                Reader reader = new InputStreamReader(inputStream);
                int contentLength = connection.getContentLength();
                char[] charArray = new char[contentLength];
                reader.read(charArray);
                String responseData = new String(charArray);

                jsr = new JSONArray(responseData);

            }

        } catch (Exception e) {
            Log.e("HTTP", "HTTP Error : " + e.getMessage());
        }

        return jsr;
    }

    public static JSONObject getOneDataByURL(String url) {

        int responseCode = -1;
        JSONObject jso = null;

        try {
            URL feedURL = new URL(BASE_URL + url);
            HttpURLConnection connection = (HttpURLConnection) feedURL.openConnection();
            connection.connect();
            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = connection.getInputStream();
                Reader reader = new InputStreamReader(inputStream);
                int contentLength = connection.getContentLength();
                char[] charArray = new char[contentLength];
                reader.read(charArray);
                String responseData = new String(charArray);

                if (responseData.equals("false")) return null;

                jso = new JSONObject(responseData);


            }

        } catch (Exception e) {
            Log.e("HTTP", "HTTP Error : " + e.getMessage());
        }

        return jso;
    }

    public static int deletePresale(int id, int number) {

        // Variable contenant les pres dispo si erreur
        int presStillAvailable = -1;

        // connection
        int responseCode = -1;

        try {
            URL feedURL = new URL(BASE_URL + "deleteAPresale.php?id=" + String.valueOf(id) + "&num=" + String.valueOf(number));
            HttpURLConnection connection = (HttpURLConnection) feedURL.openConnection();
            connection.connect();
            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = connection.getInputStream();
                Reader reader = new InputStreamReader(inputStream);
                int contentLength = connection.getContentLength();
                char[] charArray = new char[contentLength];
                reader.read(charArray);
                String responseData = new String(charArray);

                // Si il y a une erreur
                if (responseData.substring(0, 5).equals("error")) {

                    presStillAvailable = Integer.parseInt(responseData.substring(8));

                }

            }



        } catch (Exception e) {
            Log.e(UnBunkerApplication.DEBUG_TAG, "HTTP error : " + e.getMessage());
        }


        return presStillAvailable;

    }

    // retourne true si une erreure est survenue
    public static boolean createPresale(int compte_id, int bunker_Id, int number) {

        // connection
        int responseCode = -1;

        try {
            URL feedURL = new URL("http://10.0.3.2/unbunkerandroid/createAPresale.php?compte=" + compte_id + "&bunker=" + bunker_Id + "&num=" + number);
            HttpURLConnection connection = (HttpURLConnection) feedURL.openConnection();
            connection.connect();
            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = connection.getInputStream();
                Reader reader = new InputStreamReader(inputStream);
                int contentLength = connection.getContentLength();
                char[] charArray = new char[contentLength];
                reader.read(charArray);
                String responseData = new String(charArray);

                if (!responseData.isEmpty()) {
                    return true;
                }


            }


        } catch (Exception e) {
            Log.e(UnBunkerApplication.DEBUG_TAG, "HTTP error : " + e.getMessage());
        }

        return false;

    }
}
