package be.simongenin.unbunker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.simongenin.unbunker.classes.Bunker;
import be.simongenin.unbunker.classes.DateHandler;

public class BunkerAdapter extends ArrayAdapter<Bunker> {

    protected Context mContext;
    protected List<Bunker> mBunkers;

    public BunkerAdapter(Context context, List<Bunker> bunkers) {
        super(context, R.layout.bunker_list_item, bunkers);
        mContext = context;
        mBunkers = bunkers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bunker_list_item, null);
            holder = new ViewHolder();
            holder.bunkerName = (TextView) convertView.findViewById(R.id.bunker_name);
            holder.bunkerDate = (TextView) convertView.findViewById(R.id.bunker_date);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bunker bunker = mBunkers.get(position);

        String name = "Bunker " + bunker.getName();
        String date = DateHandler.formatDateYMDToString(bunker.getDate());

        holder.bunkerName.setText(name);
        holder.bunkerDate.setText(date);

        return convertView;

    }

    private static class ViewHolder {
        TextView bunkerName;
        TextView bunkerDate;
    }
}
