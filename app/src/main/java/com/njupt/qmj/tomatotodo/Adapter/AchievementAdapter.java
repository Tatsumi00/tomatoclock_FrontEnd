package com.njupt.qmj.tomatotodo.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.njupt.qmj.tomatotodo.LitePalDataBase.Achievement;
import com.njupt.qmj.tomatotodo.R;

import org.w3c.dom.Text;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    private Context mContext;

    private List<Achievement> machievementList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView achievementImage;
        TextView achievementName,achievementDate,achievementPoint,achievementIntro;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            achievementImage = (ImageView) view.findViewById(R.id.item_achievement_icon);
            achievementDate = (TextView) view.findViewById(R.id.item_achievement_date);
            achievementName = (TextView) view.findViewById(R.id.item_achievement_name);
            achievementPoint = (TextView) view.findViewById(R.id.item_achievement_point);
            achievementIntro = (TextView) view.findViewById(R.id.item_achievement_intro);
        }

    }

    public AchievementAdapter(List<Achievement> achievementList){
        machievementList = achievementList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_achievement_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Achievement achievement = machievementList.get(position);
        holder.achievementName.setText(achievement.getAchievementName());
        holder.achievementDate.setText(achievement.getAchievementTime());
        holder.achievementPoint.setText(achievement.getAchievementPoint() + "成就点数");
        holder.achievementImage.setImageResource(achievement.getAchievementImageid());
        holder.achievementIntro.setText(achievement.getAchievementIntro());
    }

    @Override
    public int getItemCount(){
        return machievementList.size();
    }
}
