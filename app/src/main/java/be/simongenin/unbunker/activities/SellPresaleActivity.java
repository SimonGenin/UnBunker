package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import be.simongenin.unbunker.R;

public class SellPresaleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_presale);

        final TextView seekedPresalesNumber = (TextView) findViewById(R.id.presales_seeked_number);
        seekedPresalesNumber.setText("1");

        final SeekBar seekBarPresales = (SeekBar) findViewById(R.id.seekBar_presales);
        seekBarPresales.setMax(5);
        seekBarPresales.setProgress(1);
        seekBarPresales.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    seekBarPresales.setProgress(1);
                }
                seekedPresalesNumber.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final TextView sellMoreText = (TextView) findViewById(R.id.sell_more_text);
        sellMoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (seekBarPresales.getMax()) {
                    case 5 :
                        seekBarPresales.setMax(10);
                        break;
                    case 10 :
                        seekBarPresales.setMax(20);
                        break;
                    case 20 :
                        seekBarPresales.setMax(100);
                        sellMoreText.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sell_presale, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
