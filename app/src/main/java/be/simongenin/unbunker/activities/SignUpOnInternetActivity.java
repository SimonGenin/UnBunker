package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import be.simongenin.unbunker.R;

public class SignUpOnInternetActivity extends Activity {

    // TODO bonne url d'inscription
    private static String URL_SIGN_UP = "https://www.facebook.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_on_internet);

        Button signUpLink = (Button) findViewById(R.id.online_sign_up_link);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URL_SIGN_UP));
                startActivity(intent);

            }
        });

    }

}
