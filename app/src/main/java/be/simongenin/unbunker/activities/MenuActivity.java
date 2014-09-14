package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;
import be.simongenin.unbunker.classes.Bunker;
import be.simongenin.unbunker.classes.Network;
import be.simongenin.unbunker.classes.Presale;
import be.simongenin.unbunker.classes.User;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Network.checkNetwork(MenuActivity.this);

        // TEST
        final TextView textTest = (TextView) findViewById(R.id.textText);
        textTest.setText(UnBunkerApplication.user.getNickname() + " est connecté");

        // Question
        TextView questionMark = (TextView) findViewById(R.id.question_txtView);
        questionMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        // Si pas connecté, renvoi vers la page de connexion
        redirectToLoginIfNeeded();

        /*
            Acheter des preventes
         */
        Button buyPresaleButton = (Button) findViewById(R.id.buy_presale_button);
        buyPresaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User.fillUsersListFromDataBase();
                Presale.fillPresalesListFromDataBase();
                Intent intent = new Intent(MenuActivity.this, BuyPresaleActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                Network.checkNetwork(MenuActivity.this);
            }
        });

        /*
            Vendre une prévente
         */
        Button sellPresaleButton = (Button) findViewById(R.id.sell_presale_button);
        sellPresaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, SellPresaleActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                Network.checkNetwork(MenuActivity.this);
            }
        });

        /*
            Voir la liste des bunkers
         */
        Button bunkersButton = (Button) findViewById(R.id.bunkers_button);
        bunkersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bunker.fillBunkersListFromDataBase();
                Intent intent = new Intent(MenuActivity.this, BunkersActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                Network.checkNetwork(MenuActivity.this);
            }
        });

        /*
            Contact
         */
        Button contactButton = (Button) findViewById(R.id.contact_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, ContactTypeMessageActivity.class);
                startActivity(intent);
            }
        });
    }



    /*
        Permet de renvoyer vers la page de connection sia
        l'utilisateur n'est pas connecté
     */
    private void redirectToLoginIfNeeded() {
        if (!UnBunkerApplication.user.isConnected()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            changeSharedPrefs();
            UnBunkerApplication.user.disconnect();
            UnBunkerApplication.user = null;
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeSharedPrefs() {

        SharedPreferences autoConnection = getSharedPreferences(UnBunkerApplication.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = autoConnection.edit();

        editor.putBoolean("CONNECTED", false);

        editor.apply();

    }
}
