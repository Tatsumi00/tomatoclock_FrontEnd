package com.njupt.qmj.tomatotodo.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.njupt.qmj.tomatotodo.Class.History;
import com.njupt.qmj.tomatotodo.R;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;

    private List<History> mHistoryList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        CardView cardView;
        TextView historyName;
        TextView historyTime;

        public ViewHolder(View view){
            super(view);
            mView = view;
            cardView = (CardView) view;
            historyName = (TextView) view.findViewById(R.id.history_intro_name);
            historyTime = (TextView) view.findViewById(R.id.history_intro_time);
        }
    }

    public HistoryAdapter(List<History> historyList){
        mHistoryList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final HistoryAdapter.ViewHolder recyclerViewHolder = (HistoryAdapter.ViewHolder) holder;
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        recyclerViewHolder.mView.setAnimation(animation);
        History history = mHistoryList.get(position);
        holder.historyName.setText(history.getName());
        holder.historyTime.setText(history.getIntro());
    }

    @Override
    public int getItemCount(){
        return mHistoryList.size();
    }

}
