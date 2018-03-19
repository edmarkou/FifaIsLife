package com.edmarkou.fifaislife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListAdapter extends BaseAdapter{

    private final Context mContext;
    private Player[] mPlayers;

    public ListAdapter(Context context, Player[] players) {
        mContext = context;
        mPlayers = players;
    }

    @Override
    public int getCount() {
        return mPlayers.length;
    }

    @Override
    public Object getItem(int i) {
        return mPlayers[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.playerName = view.findViewById(R.id.playerName);
            holder.wins = view.findViewById(R.id.wins);
            holder.loses = view.findViewById(R.id.loses);

            view.setTag(holder);
        } else{
            holder = (ViewHolder) view.getTag();
        }

        holder.playerName.setText(mPlayers[i].getPlayerName());
        holder.wins.setText(mPlayers[i].getWins()+"");
        holder.loses.setText(mPlayers[i].getLoses()+"");

        return view;
    }

    public static class ViewHolder{
        TextView playerName;
        TextView wins;
        TextView loses;
    }
}
