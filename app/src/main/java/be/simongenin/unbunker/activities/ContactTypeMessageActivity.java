package be.simongenin.unbunker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import be.simongenin.unbunker.R;

public class ContactTypeMessageActivity extends Activity {

    // Index
    public final static int CONTACT_TYPE_PROBLEM = 0;
    public final static int CONTACT_TYPE_THOUGHT = 1;
    public final static int CONTACT_TYPE_PROPOSAL = 2;
    public final static int CONTACT_TYPE_BUG = 3;
    public final static int CONTACT_TYPE_REPORT = 4;
    public final static int CONTACT_TYPE_QUESTION = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_type_message);

        final RadioGroup rg = (RadioGroup) findViewById(R.id.contact_radio_group);
        Button continueButton = (Button) findViewById(R.id.contact_continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioButtonSelectedId = rg.getCheckedRadioButtonId();

                if (radioButtonSelectedId == -1) {
                    Toast.makeText(ContactTypeMessageActivity.this, "Sélectionnez au moins une option", Toast.LENGTH_LONG).show();
                } else {
                    View radioButton = rg.findViewById(radioButtonSelectedId);
                    int index = rg.indexOfChild(radioButton);

                    Intent intent = new Intent(ContactTypeMessageActivity.this, ContactSendActivity.class);
                    intent.putExtra("TYPE", index);
                    startActivity(intent);

                }



            }
        });


    }


}
