package be.simongenin.unbunker.classes;


import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    // Ajoute une majuscule
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

    public static Date fromSringDateYMDToDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // PRE : Il y a un prochain bunker
    public static boolean isTimeToSellAndBuyYet() {
        Date now = new Date();
        Bunker nextBunker = Bunker.getNextBunker();
        Date dateBunker = DateHandler.fromSringDateYMDToDate(nextBunker.getDate());

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateBunker);
        cal.add(Calendar.HOUR_OF_DAY, -12);
        Date goodDate = cal.getTime();

        if (goodDate.before(now)) {
            return true;
        } else {
            return false;
        }

    }

    public static String getTimeLeftBeforeNextOpening() {

        Bunker nextBunker = Bunker.getNextBunker();
        Date dateBunker = DateHandler.fromSringDateYMDToDate(nextBunker.getDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateBunker);
        cal.add(Calendar.HOUR_OF_DAY, -12);
        Date dateOpen = cal.getTime();

        Date now = new Date();

        float diff = (dateOpen.getTime() - now.getTime()) / 3600000;
        if (diff < 1) {
            diff *= 60;
            if (diff < 1)
                return "quelques secondes";
             else
                return (int)diff + " minute(s)";
        } else {
            return (int)diff + " heure(s)";
        }

    }
}
