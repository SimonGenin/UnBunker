package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import be.simongenin.unbunker.R;

public class HelpActivity extends ListActivity {

    private final static int OPTION_WORKING = 0;
    private final static int OPTION_BUG = 1;
    private final static int OPTION_REPORT = 2;
    private final static int OPTION_CREDITS = 3;

    private static String[] options = {
            "Fonctionnement",
            "Comment reporter un bug",
            "Comment reporter une personne",
            "Crédits"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        getListView().setAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        switch (position) {

            case OPTION_WORKING :
                Intent intentWorking = new Intent(this, HelpWorkingActivity.class);
                startActivity(intentWorking);
                break;
            case OPTION_BUG :
                Intent intentBug = new Intent(this, HelpBugReportActivity.class);
                startActivity(intentBug);
                break;
            case OPTION_REPORT :
                Intent intentReport = new Intent(this, HelpPersonReportActivity.class);
                startActivity(intentReport);
                break;
            case OPTION_CREDITS :
                Intent intentCredits = new Intent(this, HelpCreditsActivity.class);
                startActivity(intentCredits);
                break;

        }

    }


}
