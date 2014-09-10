package be.simongenin.unbunker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.simongenin.unbunker.classes.DateHandler;
import be.simongenin.unbunker.classes.Presale;
import be.simongenin.unbunker.classes.User;

public class PresaleAdapter extends ArrayAdapter<Presale> {

    protected Context mContext;
    protected List<Presale>  mPresales;

    public PresaleAdapter(Context context, List<Presale> presales) {
        super(context, R.layout.buy_presale_item, presales);
        mContext = context;
        mPresales = presales;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.buy_presale_item, null);
            holder = new ViewHolder();
            holder.nametxt = (TextView) convertView.findViewById(R.id.name);
            holder.timesAgo = (TextView) convertView.findViewById(R.id.times_ago);
            holder.numberPresales = (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);

        }

        else {

            holder = (ViewHolder) convertView.getTag();

        }

        Presale pre = mPresales.get(position);
        User user = User.getUserById(pre.getCompte_id());

        String name = User.getUserNameById(pre.getCompte_id());
        String date = DateHandler.formatDateTimeAgo(pre.getDate_post());
        String number = String.valueOf(pre.getPresaleLeftNumber());

        holder.nametxt.setText(name);
        holder.timesAgo.setText(date);
        holder.numberPresales.setText(number);

        TextView roleAdmin = (TextView) convertView.findViewById(R.id.role_admin);
        TextView roleDev = (TextView) convertView.findViewById(R.id.role_dev);

        if (user.getRole() == User.ROLE_ADMIN) {
            roleAdmin.setVisibility(View.VISIBLE);
            roleDev.setVisibility(View.GONE);
        }
        else if (user.getRole() == User.ROLE_DEV) {
            roleAdmin.setVisibility(View.GONE);
            roleDev.setVisibility(View.VISIBLE);
        }
        else if (user.getRole() == User.ROLE_ADMIN_AND_DEV) {
            roleAdmin.setVisibility(View.VISIBLE);
            roleDev.setVisibility(View.VISIBLE);
        }
        else {
            roleAdmin.setVisibility(View.GONE);
            roleDev.setVisibility(View.GONE);
        }

        return convertView;

    }


    private static class ViewHolder {
        TextView nametxt;
        TextView timesAgo;
        TextView numberPresales;
    }
}
