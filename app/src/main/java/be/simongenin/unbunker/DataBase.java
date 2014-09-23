package be.simongenin.unbunker;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DataBase {

    // local
    public static String BASE_URL = "http://api.unbunker.tk/android/";

    public static String SECURITY_KEY = "jxds5FK3EeNH567E";

    public static JSONArray getData(String scriptName) {

        JSONArray jsr = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(BASE_URL + scriptName + ".php?key=" + SECURITY_KEY));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                jsr = new JSONArray(responseString);

            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsr;
    }

    /*
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
    */

    public static JSONObject getOneDataByURL(String urlWithArguments) {
        JSONObject jso = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(BASE_URL + urlWithArguments + "&key=" + SECURITY_KEY));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();

                if (responseString.equals("false")) return null;

                jso = new JSONObject(responseString);

            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jso;
    }

    /*
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
    } */


    public static int deletePresale(int id, int number) {

        // Variable contenant les pres dispo si erreur
        int presStillAvailable = -1;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(BASE_URL + "deleteAPresale.php?id=" + String.valueOf(id) + "&num=" + String.valueOf(number) + "&key=" + SECURITY_KEY));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();

                try {
                    // Si il y a une erreur
                    if (responseString.substring(0, 5).equals("error")) {
                        presStillAvailable = Integer.parseInt(responseString.substring(8));
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Pas important, veut dire que pas d'erreur
                }


            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return presStillAvailable;

    }
    /*
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
    */

    // retourne true si une erreure est survenue
    public static boolean createPresale(int compte_id, int bunker_Id, int number) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(BASE_URL + "createAPresale.php?compte=" + compte_id + "&bunker=" + bunker_Id + "&num=" + number + "&key=" + SECURITY_KEY));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();

                if (!responseString.isEmpty()) {
                    return true;
                }

            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    /*
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
    */
}
