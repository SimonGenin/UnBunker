package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.classes.Network;

public class BoughtActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought);

        Button returnMenu = (Button) findViewById(R.id.return_menu);
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoughtActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                Network.checkNetwork(BoughtActivity.this);

            }

        });
    }

}
