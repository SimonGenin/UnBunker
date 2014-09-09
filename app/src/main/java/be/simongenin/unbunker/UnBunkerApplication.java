package be.simongenin.unbunker;


import android.app.Application;

import be.simongenin.unbunker.classes.User;

public class UnBunkerApplication extends Application {

    public static User user;

    public static String DEBUG_TAG = "UnBunkerDebug";

    public static String SHARED_PREFS_FILE_NAME = "SettingsUnBunker";

    public UnBunkerApplication() {

        user = new User();

    }

}
