package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;
import be.simongenin.unbunker.classes.User;


public class LoginActivity extends Activity {

    private EditText nicknameEDT;
    private EditText nameEDT;
    private  EditText passwordEDT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (checkSharedPrefs()) {
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

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

                        storeSharedPrefs(UnBunkerApplication.user);

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        showDialogWrongData();
                    }

                }

            }
        });

    }

    private void storeSharedPrefs(User user) {

        SharedPreferences autoConnection = getSharedPreferences(UnBunkerApplication.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = autoConnection.edit();

        editor.putBoolean("CONNECTED", true);

        editor.putInt("ID", user.getId());
        editor.putString("NICKNAME", user.getNickname());
        editor.putString("NAME", user.getName());
        editor.putString("EMAIL", user.getEmail());
        editor.putString("GSM", user.getGsm());
        editor.putInt("ETAT", user.getEtat());

        editor.commit();

    }

    private boolean checkSharedPrefs() {

        SharedPreferences autoConnection = getSharedPreferences(UnBunkerApplication.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);

        boolean connected = autoConnection.getBoolean("CONNECTED", false);

        if (connected) {

            int id = autoConnection.getInt("ID", -1);
            String nickname = autoConnection.getString("NICKNAME", null);
            String name = autoConnection.getString("NAME", null);
            String email = autoConnection.getString("EMAIL", null);
            String gsm = autoConnection.getString("GSM", null);
            int etat = autoConnection.getInt("ETAT", 0);

            UnBunkerApplication.user = new User(id, name, nickname, null, email, gsm, null, etat);
            UnBunkerApplication.user.connect();

            return true;

        }

        UnBunkerApplication.user = null;

        return false;

    }

    private boolean checkConnection(String nkn, String n, String p) {

        User.fillUsersListFromDataBase();

        for (User user : User.users) {
            if (user.getNickname().equals(nkn) && user.getName().equals(n) && user.getPassword().equals(p)) {

                UnBunkerApplication.user = user;
                UnBunkerApplication.user.connect();
                return true;
            }
        }

        return false;

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

    private void showDialogWrongData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Impossible de se connecter");
        builder.setMessage("Vos informations sont incorrectes.");
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
