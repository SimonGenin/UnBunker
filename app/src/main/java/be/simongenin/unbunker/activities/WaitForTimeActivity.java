package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.classes.DateHandler;
import be.simongenin.unbunker.classes.Network;

public class WaitForTimeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_time);

        TextView timeLeft = (TextView) findViewById(R.id.time_left);

        String time = DateHandler.getTimeLeftBeforeNextOpening();

        timeLeft.setText("Encore " + time);

        Button returnMenu = (Button) findViewById(R.id.return_menu);
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitForTimeActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

                Network.checkNetwork(WaitForTimeActivity.this);

            }

        });

    }

}
