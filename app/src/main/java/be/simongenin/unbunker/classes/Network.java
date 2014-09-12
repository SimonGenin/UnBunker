package be.simongenin.unbunker.classes;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.activities.NoNetworkAvailableActivity;

public class Network {

    public static void checkNetwork(Context context) {

        if (!isNetworkAvailable(context)) {

            Intent intent = new Intent(context, NoNetworkAvailableActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

}
