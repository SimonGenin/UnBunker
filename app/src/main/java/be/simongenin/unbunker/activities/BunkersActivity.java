package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.os.Bundle;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.adapters.BunkerAdapter;
import be.simongenin.unbunker.classes.Bunker;


public class BunkersActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunkers);

        BunkerAdapter adapter = new BunkerAdapter(this, Bunker.bunkers);
        getListView().setAdapter(adapter);

    }

}
