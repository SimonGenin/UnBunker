package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.os.Bundle;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;
import be.simongenin.unbunker.adapters.HistoryAdapter;
import be.simongenin.unbunker.classes.History;

public class BuyingHistoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_history);

        HistoryAdapter adapter = new HistoryAdapter(this, History.getBuyingHistoryList(UnBunkerApplication.user.getHistories()));
        getListView().setAdapter(adapter);

    }



}
