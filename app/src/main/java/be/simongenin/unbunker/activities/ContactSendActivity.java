package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import be.simongenin.unbunker.R;

public class ContactSendActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_send);

        Intent gottenIntent = getIntent();
        final int type = gottenIntent.getIntExtra("TYPE", 0);

        TextView sms = (TextView) findViewById(R.id.contact_sms_send);
        TextView email = (TextView) findViewById(R.id.contact_email_send);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String typeStr = null;
                switch (type) {

                    case ContactTypeMessageActivity.CONTACT_TYPE_PROBLEM :
                        typeStr = "[UNBUNKER:PROBLEME]\n";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_THOUGHT :
                        typeStr = "[UNBUNKER:AVIS]\n";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_PROPOSAL :
                        typeStr = "[UNBUNKER:PROPOSITION]\n";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_BUG :
                        typeStr = "[UNBUNKER:BUG]\n";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_REPORT :
                        typeStr = "[UNBUNKER:REPORT]\n";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_QUESTION :
                        typeStr = "[UNBUNKER:QUESTION]\n";
                        break;

                }

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:" + "0474130371"));
                intent.putExtra("sms_body", typeStr);
                startActivityForResult(intent, 100);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String typeStr = null;
                switch (type) {

                    case ContactTypeMessageActivity.CONTACT_TYPE_PROBLEM :
                        typeStr = "UnBunker : Probleme";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_THOUGHT :
                        typeStr = "UnBunker : Avis";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_PROPOSAL :
                        typeStr = "UnBunker : Proposition";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_BUG :
                        typeStr = "UnBunker : Report de bug";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_REPORT :
                        typeStr = "UnBunker : Report de personne";
                        break;
                    case ContactTypeMessageActivity.CONTACT_TYPE_QUESTION :
                        typeStr = "UnBunker : Question";
                        break;

                }

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"simongenin@hotmail.fr"});
                intent.putExtra(Intent.EXTRA_SUBJECT, typeStr);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Envoyer E-Mail"), 100);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactSendActivity.this, "Il n'y a pas de client email installé sur votre smartphone", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 100) {
            Intent intent = new Intent(this, SentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

        }

    }

}
