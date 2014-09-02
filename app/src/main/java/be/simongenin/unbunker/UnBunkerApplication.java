package be.simongenin.unbunker;


import android.app.Application;

public class UnBunkerApplication extends Application {

    public static User user;

    public UnBunkerApplication() {

        user = new User();

    }

}
