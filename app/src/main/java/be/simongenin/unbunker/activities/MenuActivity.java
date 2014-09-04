package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;
import be.simongenin.unbunker.classes.Bunker;
import be.simongenin.unbunker.classes.Presale;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // TEST
        final TextView textTest = (TextView) findViewById(R.id.textText);

        // Si pas connecté, renvoi vers la page de connexion
        redirectToLoginIfNeeded();

        // Deconnection
        // TODO mettre dans un menu
        Button disconnectButton = (Button) findViewById(R.id.disconnect_button);
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnBunkerApplication.user.disconnect();
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        /*
            Acheter des preventes
         */
        Button buyPresaleButton = (Button) findViewById(R.id.buy_presale_button);
        buyPresaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presale.fillPresalesListFromDataBase();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
