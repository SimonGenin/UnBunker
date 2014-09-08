package be.simongenin.unbunker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import be.simongenin.unbunker.activities.BuyPresaleActivity;
import be.simongenin.unbunker.classes.Presale;
import be.simongenin.unbunker.classes.User;


public class BuyPresaleDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_presale_details);

        // Get the intent
        Intent gottenIntent = getIntent();
        User seller = (User) gottenIntent.getSerializableExtra("USER");
        Presale pre = (Presale) gottenIntent.getSerializableExtra("PRESALE");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buy_presale_details, menu);
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
