package be.simongenin.unbunker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.simongenin.unbunker.R;
import be.simongenin.unbunker.classes.DateHandler;
import be.simongenin.unbunker.classes.History;
import be.simongenin.unbunker.classes.User;

public class HistoryAdapter extends ArrayAdapter<History> {

    protected Context mContext;
    protected List<History> mHistories;

    public HistoryAdapter(Context context, List<History> histories) {
        super(context, R.layout.history_list_item, histories);
        mContext = context;
        mHistories = histories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_list_item, null);
            holder = new ViewHolder();
            holder.interactorName = (TextView) convertView.findViewById(R.id.name);
            holder.interactorNumber = (TextView) convertView.findViewById(R.id.gsm_number);
            holder.interactionDate = (TextView) convertView.findViewById(R.id.times_ago);
            holder.transactionNumber = (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        History history = mHistories.get(position);
        User interactor = history.getInteractor();

        String name = interactor.getNickname() + " " + interactor.getName();
        String number = interactor.getGsm();
        String date = DateHandler.formatDateYMDToString(history.getDate());
        String presalesNumber = String.valueOf(history.getNumber());

        holder.interactorName.setText(name);
        holder.interactorNumber.setText(number);
        holder.interactionDate.setText(date);
        holder.transactionNumber.setText(presalesNumber);

        return convertView;
    }

    private static class ViewHolder {
        TextView interactorName;
        TextView interactorNumber;
        TextView interactionDate;
        TextView transactionNumber;
    }

}
