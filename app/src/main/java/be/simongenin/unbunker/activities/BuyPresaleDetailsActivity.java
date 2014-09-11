package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import be.simongenin.unbunker.DataBase;
import be.simongenin.unbunker.R;
import be.simongenin.unbunker.classes.Presale;
import be.simongenin.unbunker.classes.User;


public class BuyPresaleDetailsActivity extends Activity {

    Presale pre;
    int error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_presale_details);

        // Get the intent
        Intent gottenIntent = getIntent();
        User seller = (User) gottenIntent.getSerializableExtra("USER");
        pre = (Presale) gottenIntent.getSerializableExtra("PRESALE");

        // Met le nom du vendeur
        TextView sellerNameText = (TextView) findViewById(R.id.seller_name);
        sellerNameText.setText(seller.getNickname() + " " + seller.getName());

        Presale presaleExistCheck = Presale.getOnePresaleByIdFromDataBase(pre.getId());
        if (presaleExistCheck == null) {
            Presale.fillPresalesListFromDataBase();
            unexistingPresaleDialog();
        } else {

            // Afin de récupéré le bon nombre de préventes restantes.
            pre = presaleExistCheck;

            // Seekbar + nombre
            final TextView numberPresalesText = (TextView) findViewById(R.id.presales_seeked_number);
            numberPresalesText.setText("1");

            final SeekBar seekBarPresale = (SeekBar) findViewById(R.id.seekBar_presales);
            seekBarPresale.setMax(pre.getPresaleLeftNumber());
            seekBarPresale.setProgress(1);
            seekBarPresale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBarPresale.getProgress() == 0) {
                        seekBarPresale.setProgress(1);
                    }

                    numberPresalesText.setText(String.valueOf(seekBarPresale.getProgress()));

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            Button buyButton = (Button) findViewById(R.id.buy_button);
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            error = DataBase.deletePresale(pre.getId(), seekBarPresale.getProgress());

                        }
                    });

                    t.start();

                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (error > 0) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(BuyPresaleDetailsActivity.this);
                            builder.setTitle("Préventes non disponnibles");
                            builder.setMessage("Il semblerait que " + seekBarPresale.getProgress() + " préventes ne soient plus disponnibles, désolé. Il en reste cependant " + error + ". Dépéchez vous de revenir avant qu'elles ne partent toutes!");
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(BuyPresaleDetailsActivity.this, MenuActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog dialog = builder.create();
                        dialog.show();


                    } else if (error == 0) {

                        if (seekBarPresale.getProgress() == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuyPresaleDetailsActivity.this);
                            builder.setTitle("Prévente non disponnible");
                            builder.setMessage("Il semblerait que la prévente ne soit plus disponnible, désolé.");
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(BuyPresaleDetailsActivity.this, MenuActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuyPresaleDetailsActivity.this);
                            builder.setTitle("Préventes non disponnibles");
                            builder.setMessage("Il semblerait que les préventes ne soient plus disponnibles, désolé.");
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(BuyPresaleDetailsActivity.this, MenuActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }

                    else {
                        // Si pas d'erreurs
                        if (seekBarPresale.getProgress() > 1)
                            Toast.makeText(BuyPresaleDetailsActivity.this, seekBarPresale.getProgress() + " préventes achetées", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(BuyPresaleDetailsActivity.this, "Prévente achetée", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(BuyPresaleDetailsActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }


                }
            });

        }

    }

    private void unexistingPresaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Prévente déjà vendue!");
        builder.setMessage("Malheureusement, il semblerait que cette prévente soit déjà vendue...");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BuyPresaleDetailsActivity.this, BuyPresaleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
