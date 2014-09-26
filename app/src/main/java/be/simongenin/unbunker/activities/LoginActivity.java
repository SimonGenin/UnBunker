package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;
import be.simongenin.unbunker.classes.Encryption;
import be.simongenin.unbunker.classes.Network;
import be.simongenin.unbunker.classes.User;


public class LoginActivity extends Activity {

    private EditText nicknameEDT;
    private EditText nameEDT;
    private EditText passwordEDT;
    private EditText gsmEDT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        if (checkSharedPrefs()) {
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        TextView signUpText = (TextView) findViewById(R.id.signup_text);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpOnInternetActivity.class);
                startActivity(intent);
            }
        });

        // init
        nicknameEDT = (EditText) findViewById(R.id.nickname_edt);
        nameEDT = (EditText) findViewById(R.id.name_edt);
        passwordEDT = (EditText) findViewById(R.id.mdp_edt);
        gsmEDT = (EditText) findViewById(R.id.gsm_edt);
        Button loginButton = (Button) findViewById(R.id.login_button);


        passwordEDT.setTypeface(Typeface.DEFAULT);
        passwordEDT.setTransformationMethod(new PasswordTransformationMethod());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Network.checkNetwork(LoginActivity.this);


                // Recupere les données
                String nickname = nicknameEDT.getText().toString().trim();
                String name = nameEDT.getText().toString().trim();
                String password = passwordEDT.getText().toString().trim();
                String gsm = gsmEDT.getText().toString().trim();

                // check si les champs ne sont pas vides
                if (checkFields(nickname, name, password, gsm)) {
                    // check la bd si il existe
                    if (checkConnection(nickname, name, password, gsm)) {

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
        editor.putInt("ROLE", user.getRole());

        editor.apply();

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
            int role = autoConnection.getInt("ROLE", 0);

            UnBunkerApplication.user = new User(id, name, nickname, null, email, gsm, null, etat, role);
            UnBunkerApplication.user.connect();

            return true;

        }

        UnBunkerApplication.user = null;

        return false;

    }

    private boolean checkConnection(String nkn, String n, String p, String gsm) {

        setProgressBarIndeterminateVisibility(true);

        User.fillUsersListFromDataBase();
        p = Encryption.encryptPassword(p);

        setProgressBarIndeterminateVisibility(false);

        // Si erreur pendant le cryptage
        if (p == null) {
            encryptionError();
            return false;
        }

        nkn = nkn.toLowerCase();
        n = n.toLowerCase();

        for (User user : User.users) {

            String user_nkn = user.getNickname().toLowerCase();
            String user_n = user.getName().toLowerCase();

            // On check avec le nom et prenom (inssanssible a la casse)
            if (user_nkn.equals(nkn) && user_n.equals(n) && user.getPassword().equals(p)) {
                if (user.getEtat() == 1) {
                    UnBunkerApplication.user = user;
                    UnBunkerApplication.user.connect();
                    return true;
                } else {
                    notActiveAccount();
                    return false;
                }
            }

            // On check avec le num de gsm
            String user_gsm = user.getGsm();
            if (user_gsm.equals(gsm) && user.getPassword().equals(p)) {
                if (user.getEtat() == 1) {
                    UnBunkerApplication.user = user;
                    UnBunkerApplication.user.connect();
                    return true;
                } else {
                    notActiveAccount();
                    return false;
                }
            }

        }
        return false;

    }

    private void notActiveAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Compte innactif");
        builder.setMessage("Vous n'avez pas encore activé votre compte a l'aide de la vérification sms. Veuillez vous rendre sur le site afin de procéder à cette dernière étape.");
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void encryptionError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur");
        builder.setMessage("Il semblerait que votre connection soit impossible dû à une erreur de sécuritée.");
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
        Check si des champs sont vides
     */
    private boolean checkFields(String nkn, String n, String p, String gsm) {

        if (p.isEmpty()) {
            showDialogEmptyFields();
            return false;
        }

        if (nkn.isEmpty() && n.isEmpty()) {
            if (gsm.isEmpty()) {
                showDialogEmptyFields();
                return false;
            }
        }

        if (gsm.isEmpty()) {
            if (nkn.isEmpty() || n.isEmpty()) {
                showDialogEmptyFields();
                return false;
            }
        }

        return true;
    }



    private void showDialogEmptyFields() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Champs manquant !");
        builder.setMessage("Le prénom, nom et mot de passe ou le gsm et mot de passe doivent au moins être rentré");
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
        getMenuInflater().inflate(R.menu.sold, menu);
        return true;
    }

}
