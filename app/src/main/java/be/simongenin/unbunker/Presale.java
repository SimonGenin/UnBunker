package be.simongenin.unbunker;


import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Presale {

    static JSONObject jsr;

    /*
        Get all the presales and store them in jsr (JSONOBJECT)
     */
    public static void getAllPresales() {

         new Thread(new Runnable() {
             @Override
             public void run() {

                 int responseCode = -1;

                 try {
                     URL feedURL = new URL("http://10.0.3.2/unbunkerandroid/getAllPresales.php");
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

                         jsr = new JSONObject(responseData);

                     }

                 } catch(Exception e) {
                     Log.e("Presale activity", "Error : " + e.getMessage());
                 }
             }
         }).start();

    }

}
