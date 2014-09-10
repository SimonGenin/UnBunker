package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import be.simongenin.unbunker.adapters.PresaleAdapter;
import be.simongenin.unbunker.R;
import be.simongenin.unbunker.classes.Presale;
import be.simongenin.unbunker.classes.User;

public class BuyPresaleActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_presale);

        PresaleAdapter adapter = new PresaleAdapter(this, Presale.getNotSoldPresales());
        getListView().setAdapter(adapter);

    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(this, BuyPresaleDetailsActivity.class);
        intent.putExtra("USER", User.getUserById(Presale.getNotSoldPresales().get(position).getCompte_id()));
        intent.putExtra("PRESALE", Presale.getNotSoldPresales().get(position));
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buy_presale, menu);
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
