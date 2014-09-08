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

    public static JSONArray getData(String scriptName)  {

        int responseCode = -1;
        JSONArray jsr = null;

        try {
            URL feedURL = new URL("http://10.0.3.2/unbunkerandroid/" + scriptName + ".php");
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
            URL feedURL = new URL("http://10.0.3.2/unbunkerandroid/" + url);
            HttpURLConnection connection = (HttpURLConnection) feedURL.openConnection();
            connection.connect();
            responseCode = connection.getResponseCode();
            int i = 23;

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

}
