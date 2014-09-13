package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help, menu);
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
