package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;


public class LoginActivity extends Activity {

    private EditText nicknameEDT;
    private EditText nameEDT;
    private  EditText passwordEDT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init
        nicknameEDT = (EditText) findViewById(R.id.nickname_edt);
        nameEDT = (EditText) findViewById(R.id.name_edt);
        passwordEDT = (EditText) findViewById(R.id.mdp_edt);
        Button loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Recupere les données
                String nickname = nicknameEDT.getText().toString().trim();
                String name = nameEDT.getText().toString().trim();
                String password = passwordEDT.getText().toString().trim();

                // check si les champs ne sont pas vides
                if (checkFields(nickname, name, password)) {
                    // check la bd si il existe
                    if (checkConnection(nickname, name, password)) {

                        // TODO change data given to user be the ones into the DB
                        UnBunkerApplication.user.setName(name);
                        UnBunkerApplication.user.setNickname(nickname);

                        UnBunkerApplication.user.connect();

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }

            }
        });

    }

    private boolean checkConnection(String nkn, String n, String p) {
        // TODO check connection + error dialog
        return true;

    }

    /*
        Check si des champs sont vides
     */
    private boolean checkFields(String nkn, String n, String p) {

        if (nkn.isEmpty() || n.isEmpty() || p.isEmpty()) {
            showDialogEmptyFields();
            return false;
        }

        return true;
    }

    private void showDialogEmptyFields() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Impossible de se connecter");
        builder.setMessage("Tous les champs doivent être remplis.");
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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