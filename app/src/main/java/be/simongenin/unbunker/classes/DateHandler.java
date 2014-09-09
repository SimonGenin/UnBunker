package be.simongenin.unbunker.classes;


import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler {

    /*
	 * Coverti de yyyy-MM-dd vers EEEE d MMMM yyyy (exemple)
	 * Ajoute une majuscule
	 */
    public static String formatDateYMDToString(String ds) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date stringDate = sdf.parse(ds);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE d MMMM yyyy");
            String date = formatter.format(stringDate);
            date = date.substring(0, 1).toUpperCase() + date.substring(1);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Convertu yyyy-MM-dd HH:mm:ss en beau format (il y 7 minutes)
    public static String formatDateTimeAgo(String ds) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date stringDate = sdf.parse(ds);
            PrettyTime p = new PrettyTime();
            String date =  p.format(stringDate);
            date = date.substring(0, 1).toUpperCase() + date.substring(1);
            return date;

        } catch(ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
