package be.simongenin.unbunker.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.adapters.PresaleAdapter;
import be.simongenin.unbunker.classes.Network;
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

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        Network.checkNetwork(this);

    }

}
