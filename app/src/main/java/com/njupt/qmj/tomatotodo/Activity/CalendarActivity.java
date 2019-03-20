package com.njupt.qmj.tomatotodo.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.njupt.qmj.tomatotodo.Adapter.HistoryAdapter;
import com.njupt.qmj.tomatotodo.CalendarDecorator.DataBaseDecorator;
import com.njupt.qmj.tomatotodo.Class.History;
import com.njupt.qmj.tomatotodo.LitePalDataBase.DateRecorder;
import com.njupt.qmj.tomatotodo.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.litepal.Operator.*;

public class CalendarActivity extends AppCompatActivity{

    Calendar calendar;
    Toolbar toolbar;
    private HistoryAdapter historyAdapter;
    private List<History> historyList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_calendar);
        calendar = Calendar.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("时间线");
        toolbar.setSubtitle(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
        iniCalendar();
        iniData(calendar.get( Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        recyclerView = findViewById(R.id.calendar_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(historyAdapter);
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
   }

   public void iniCalendar(){
       MaterialCalendarView mcv = findViewById(R.id.maerial_cakendar);
       int temp;
       switch (calendar.get(Calendar.MONTH) + 1){
           case 2:
               temp = 28;
               break;
           case 1|3|5|6|7|8|10|12:
               temp = 31;
               break;
           default:
               temp = 30;
               break;

       }
       mcv.state().edit()
               .setMinimumDate(CalendarDay.from(calendar.get(Calendar.YEAR) - 1 ,1,1))
               .setMaximumDate(CalendarDay.from(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,temp))
               .commit();
       mcv.setSelectedDate(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH)));
       mcv.setOnDateChangedListener(new OnDateSelectedListener() {
           @Override
           public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
               toolbar.setSubtitle(date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日");
               historyAdapter.notifyDataSetChanged();
               iniData(date.getYear(), date.getMonth(), date.getDay());
           }
       });
       mcv.setWeekDayLabels(new String[] {"一","二","三","四","五","六","日"});
       mcv.setTitleFormatter(new TitleFormatter() {
           @Override
           public CharSequence format(CalendarDay day) {
               StringBuffer buffer = new StringBuffer();
               int yearOne = day.getYear();
               int monthOne = day.getMonth();
               buffer.append(yearOne).append("年").append(monthOne).append("月");
               return buffer;
           }
       });
       mcv.addDecorator(new DataBaseDecorator());

   }

   private void iniData(int year, int month , int day){
       List<DateRecorder> dateRecorders = where("year=?",year+"")
               .where("month=?",month+"")
               .where("day=?",day+"")
               .find(DateRecorder.class);
           historyList.clear();
           for (DateRecorder dateRecorder: dateRecorders){
               historyList.add(new History(dateRecorder.getName(),dateRecorder.getOnceTime() / 60 + "分钟"));
       }
   }
}
