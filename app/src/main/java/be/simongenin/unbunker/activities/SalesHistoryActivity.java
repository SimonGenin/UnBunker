package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.os.Bundle;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.UnBunkerApplication;
import be.simongenin.unbunker.adapters.HistoryAdapter;
import be.simongenin.unbunker.classes.History;

public class SalesHistoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);

        HistoryAdapter adapter = new HistoryAdapter(this, History.getSalesHistoryList(UnBunkerApplication.user.getHistories()));
        getListView().setAdapter(adapter);

    }

}
