package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.classes.Network;

public class NoNetworkAvailableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network_available);

        Button returnMenu = (Button) findViewById(R.id.return_menu);
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isConnected = Network.isNetworkAvailable(NoNetworkAvailableActivity.this);

                if (!isConnected) {
                    Toast.makeText(NoNetworkAvailableActivity.this, "Toujours pas de connexion", Toast.LENGTH_SHORT).show();
                }

                Network.checkNetwork(NoNetworkAvailableActivity.this);

                if (isConnected) {
                    Intent intent = new Intent(NoNetworkAvailableActivity.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                }



            }
        });

    }

}
