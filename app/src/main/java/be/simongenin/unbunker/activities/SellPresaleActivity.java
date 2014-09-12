package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import be.simongenin.unbunker.DataBase;
import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;

public class SellPresaleActivity extends Activity {

    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_presale);

        final TextView seekedPresalesNumber = (TextView) findViewById(R.id.presales_seeked_number);
        seekedPresalesNumber.setText("1");

        final SeekBar seekBarPresale = (SeekBar) findViewById(R.id.seekBar_presales);
        seekBarPresale.setMax(5);
        seekBarPresale.setProgress(1);
        seekBarPresale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBarPresale.getProgress() == 0) {
                    seekBarPresale.setProgress(1);
                }

                seekedPresalesNumber.setText(String.valueOf(seekBarPresale.getProgress()));
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

                switch (seekBarPresale.getMax()) {
                    case 5 :
                        seekBarPresale.setMax(10);
                        break;
                    case 10 :
                        seekBarPresale.setMax(20);
                        break;
                    case 20 :
                        seekBarPresale.setMax(100);
                        sellMoreText.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        Button sellPresale = (Button) findViewById(R.id.sell_button);
        sellPresale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        error = DataBase.createPresale(UnBunkerApplication.user.getId(), getBunkerId(), seekBarPresale.getProgress());
                    }
                });

                t.start();

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if (error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellPresaleActivity.this);
                    builder.setTitle("Une erreur est survenue.");
                    builder.setMessage("Il semblerait que votre prévente n'ai pas été mise en vente. Vérifier cela dans la liste d'achat.");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SellPresaleActivity.this, MenuActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {

                    if (seekBarPresale.getProgress() == 1)
                        Toast.makeText(SellPresaleActivity.this, "Prévente vendue", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(SellPresaleActivity.this, seekBarPresale.getProgress()  + " préventes vendues", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(SellPresaleActivity.this, SoldActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

                }

            }
        });

    }

    private int getBunkerId() {

        return 3;

    }


}
