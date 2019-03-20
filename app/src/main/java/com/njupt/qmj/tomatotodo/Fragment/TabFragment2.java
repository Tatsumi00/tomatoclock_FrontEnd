package com.njupt.qmj.tomatotodo.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.njupt.qmj.tomatotodo.Activity.CalendarActivity;
import com.njupt.qmj.tomatotodo.Adapter.AchievementAdapter;
import com.njupt.qmj.tomatotodo.LitePalDataBase.Achievement;
import com.njupt.qmj.tomatotodo.LitePalDataBase.Time;
import com.njupt.qmj.tomatotodo.R;

import java.util.List;

import static org.litepal.Operator.*;

public class TabFragment2 extends Fragment {

    TextView cardView1Statics1,cardView1Statics2,cardView1Statics3;
    TextView cardView2Statics11,cardView2Statics12,cardView2Statics21,cardView2Statics22,cardView2Statics31
            ,cardView2Statics32,cardView2Statics41,cardView2Statics42,cardView2Statics51,cardView2Statics52;
    TextView cardView3Statics1,cardView3Statics2;
    TextView achievementName,achievementPointText,achievementDate;
    ImageView achievementicon ;

    int gradeAverage,tomatoCount,totalTime,achievementCount,achievementPoint;


    List<Achievement> achievementList;
    AchievementAdapter achievementAdapter;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.tab_fragment2, null);
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        iniDataBase();
        iniView(view);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.statics_toolbar_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.clear_statics:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("清空数据");
                dialog.setMessage("确定重置所有统计数据吗?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences  =  getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                        if (sharedPreferences.getInt("startFor1",0) == 1) {
                            final int tempgradeAverage =  sharedPreferences.getInt("gradeAverage",0);
                            final int temptomatoCount = sharedPreferences.getInt("tomatoCount",0);
                            final int tempachievementPoint = sharedPreferences.getInt("achievementPoint",0);
                            final int tempachievementCount = sharedPreferences.getInt("achievementCount",0);
                            final int temptotalTime = sharedPreferences.getInt("totalTIme",0);
                            final List<Time> timeList = findAll(Time.class);
                            final List<Achievement> achievements = findAll(Achievement.class);
                            achievementList.clear();
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                            editor.putInt("gradeAverage",0);
                            editor.putInt("tomatoCount",0);
                            editor.putInt("achievementCount",0);
                            editor.putInt("achievementPoint",0);
                            editor.putInt("totalTime",0);
                            editor.putInt("startFor1",0);
                            editor.apply();
                            deleteAll(Time.class);
                            deleteAll(Achievement.class);
                            gradeAverage = 0;
                            tomatoCount = 0;
                            totalTime = 0;
                            achievementCount = 0;
                            achievementPoint = 0;
                            iniCardView1();
                            iniCardView2();
                            iniCardView3();
                            Snackbar.make(getView(), "数据已删除", Snackbar.LENGTH_LONG)
                                    .setAction("撤回", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                                            editor.putInt("gradeAverage",tempgradeAverage);
                                            editor.putInt("tomatoCount",temptomatoCount);
                                            editor.putInt("achievementCount",tempachievementCount);
                                            editor.putInt("achievementPoint",tempachievementPoint);
                                            editor.putInt("totalTime",temptotalTime);
                                            editor.putInt("startFor1",1);
                                            editor.apply();
                                            int n1 = timeList.size();
                                            for (int i = 0; i < n1; i++) {
                                                Time time = timeList.get(i);
                                                int temp1 = time.getTodoTime();
                                                String temp2 = time.getTodoName();
                                                Time time1 = new Time(temp2, temp1);
                                                time1.save();
                                            }
                                            achievementList = achievements;
                                            int n2 = achievements.size();
                                            for (int i = 0; i <n2;i++){
                                                Achievement achievement = achievements.get(i);
                                                String temp1 = achievement.getAchievementName();
                                                String temp2 = achievement.getAchievementTime();
                                                int temp3 = achievement.getAchievementPoint();
                                                int temp4 = achievement.getAchievementImageid();
                                                String temp5 = achievement.getAchievementIntro();
                                                Achievement achievement1 = new Achievement(temp1,temp2,temp3,temp4,temp5);
                                                achievement1.save();
                                            }
                                            iniDataBase();
                                            iniCardView1();
                                            iniCardView2();
                                            iniCardView3();
                                            Toast.makeText(getContext(), "数据已恢复", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                        }else {
                            Toast.makeText(getContext(),"数据不存在",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                break;
            case R.id.show_calendar:
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void iniView(View view){
        CardView cardView1 = (CardView) view.findViewById(R.id.static_cardview_1);
        CardView cardView2 = (CardView) view.findViewById(R.id.static_cardview_2);
        CardView cardView3 = (CardView) view.findViewById(R.id.static_cardview_3);

        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        cardView1.setBackgroundColor(typedValue.data);
        cardView2.setBackgroundColor(typedValue.data);
        cardView3.setBackgroundColor(typedValue.data);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_achieve_list,null,false);
                final android.app.AlertDialog achieveDialog = new android.app.AlertDialog.Builder(getContext()).setView(view).create();
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.achievement_listview);
                GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),1);
                recyclerView.setLayoutManager(layoutManager);
                achievementAdapter = new AchievementAdapter(achievementList);
                recyclerView.setAdapter(achievementAdapter);
                TextView achieveSlogan = view.findViewById(R.id.achievement_slogan);
                if (findAll(Achievement.class).size() != 0) {
                    achieveSlogan.setText(R.string.get_more_achieves);
                }else {
                    achieveSlogan.setText("暂时没有成就，要加油啊");
                }
                Button okButton = (Button) view.findViewById(R.id.achievement_button);
                TypedValue typedValue = new TypedValue();
                getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
                okButton.setTextColor(typedValue.data);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        achieveDialog.dismiss();
                    }
                });
                achieveDialog.setTitle("成就列表");
                achieveDialog.setCancelable(true);
                achieveDialog.show();
            }
        });

        cardView1Statics1 = (TextView) view.findViewById(R.id.cardview1_statics1);
        cardView1Statics2 = (TextView) view.findViewById(R.id.cardview1_statics2);
        cardView1Statics3 = (TextView) view.findViewById(R.id.cardview1_statics3);
        cardView2Statics11 = (TextView) view.findViewById(R.id.cardview2_text11);
        cardView2Statics12 = (TextView) view.findViewById(R.id.cardview2_text12);
        cardView2Statics21 = (TextView) view.findViewById(R.id.cardview2_text21);
        cardView2Statics22 = (TextView) view.findViewById(R.id.cardview2_text22);
        cardView2Statics31 = (TextView) view.findViewById(R.id.cardview2_text31);
        cardView2Statics32 = (TextView) view.findViewById(R.id.cardview2_text32);
        cardView2Statics41 = (TextView) view.findViewById(R.id.cardview2_text41);
        cardView2Statics42 = (TextView) view.findViewById(R.id.cardview2_text42);
        cardView2Statics51 = (TextView) view.findViewById(R.id.cardview2_text51);
        cardView2Statics52 = (TextView) view.findViewById(R.id.cardview2_text52);
        cardView3Statics1 = (TextView) view.findViewById(R.id.cardview3_statics1);
        cardView3Statics2 = (TextView) view.findViewById(R.id.cardview3_statics2);
        achievementName = view.findViewById(R.id.achievement_name);
        achievementPointText = view.findViewById(R.id.achievement_point);
        achievementDate = view.findViewById(R.id.achievement_date);
        achievementicon = view.findViewById(R.id.achievement_icon);


        iniCardView1();
        iniCardView2();
        iniCardView3();
    }

    private void iniDataBase(){
        SharedPreferences sharedPreferences  =  getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        gradeAverage =  sharedPreferences.getInt("gradeAverage",0);
        tomatoCount = sharedPreferences.getInt("tomatoCount",0);
        achievementPoint = sharedPreferences.getInt("achievementPoint",0);
        achievementCount = sharedPreferences.getInt("achievementCount",0);
        totalTime = sharedPreferences.getInt("totalTime",0);
        achievementList = findAll(Achievement.class);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("f12","f12");
        iniDataBase();
        iniCardView1();
        iniCardView2();
        iniCardView3();
    }

    private void iniCardView1(){
        cardView1Statics1.setText(tomatoCount + "");
        int minute = totalTime / 60;
        int hour = minute / 60;
        int day = hour / 24;
        String str = "0分";
        if ( 60 <= totalTime && totalTime < 3600){
            str = minute + "<small>分</small>";
        }else if (totalTime >= 3600 && totalTime < 86400){
            minute = minute % 60;
            str = hour + "<small>时</small>" + minute + "<small>分</small>";
        }else if (totalTime >= 86400){
            str = day + "<small>天</small>" + hour + "<small>时</small>";
        }
        cardView1Statics2.setText(Html.fromHtml(str));
        cardView1Statics3.setText(gradeAverage + "");
    }

    private void iniCardView2(){
        if (findAll(Time.class).size() != 0){
            List<Time> times = order("todoTime desc").limit(5).find(Time.class);
            int n = times.size();
            for (int i = 0; i < n ; i++){
                Time time = times.get(i);
                String Name = time.getTodoName();
                int totalTimeInt = time.getTodoTime();
                int minute = totalTimeInt / 60;
                int hour = minute / 60;
                int day = hour / 24;
                String totalTimeinCard2 = "0分钟";
                if ( 60 <= totalTimeInt && totalTimeInt < 3600){
                    totalTimeinCard2 = minute + "分钟";
                }else if (totalTimeInt >= 3600 && totalTimeInt < 86400){
                    minute = minute % 60;
                    totalTimeinCard2 = hour + "小时" + minute + "分钟";
                }else if (totalTimeInt >= 86400){
                    totalTimeinCard2 = day + "天" + hour + "小时";
                }
                switch (i){
                    case 0:
                        cardView2Statics11.setText(Name);
                        cardView2Statics12.setText(totalTimeinCard2);
                        break;
                    case 1:
                        cardView2Statics21.setText(Name);
                        cardView2Statics22.setText(totalTimeinCard2);
                        break;
                    case 2:
                        cardView2Statics31.setText(Name);
                        cardView2Statics32.setText(totalTimeinCard2);
                        break;
                    case 3:
                        cardView2Statics41.setText(Name);
                        cardView2Statics42.setText(totalTimeinCard2);
                        break;
                    case 4:
                        cardView2Statics51.setText(Name);
                        cardView2Statics52.setText(totalTimeinCard2);
                        break;
                }

            }
        }else {
            cardView2Statics11.setText("暂时没有数据，番茄钟完成后会显示在此处");
            cardView2Statics12.setText(null);
            cardView2Statics21.setText(null);
            cardView2Statics22.setText(null);
            cardView2Statics31.setText(null);
            cardView2Statics32.setText(null);
            cardView2Statics41.setText(null);
            cardView2Statics42.setText(null);
            cardView2Statics51.setText(null);
            cardView2Statics52.setText(null);
        }
    }

    private void iniCardView3(){
        if (findAll(Achievement.class).size() != 0) {
            cardView3Statics1.setText(achievementCount + "");
            cardView3Statics2.setText(achievementPoint + "");
            achievementicon.setImageResource(R.drawable.ic_done_all_white_24dp);
            Achievement achievement = findLast(Achievement.class);
            achievementName.setText(achievement.getAchievementName());
            achievementDate.setText(achievement.getAchievementTime());
            achievementPointText.setText(achievement.getAchievementPoint() + "成就点数");
        }else {
            cardView3Statics1.setText(0 + "");
            cardView3Statics2.setText(0 + "");
            achievementicon.setImageResource(R.drawable.ic_cancel_black_24dp);
            achievementName.setText("最近未获得成就，请加油哦!");
            achievementDate.setText(null);
            achievementPointText.setText(null);
        }
    }





}
