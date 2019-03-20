package com.njupt.qmj.tomatotodo.CalendarDecorator;

import android.util.Log;

import com.njupt.qmj.tomatotodo.LitePalDataBase.DateRecorder;
import com.njupt.qmj.tomatotodo.view.CircleBackGroundSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.List;
import static org.litepal.Operator.*;

public class DataBaseDecorator implements DayViewDecorator {

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay){

        List<DateRecorder> dateRecorders = where("year=?",calendarDay.getYear()+"")
                .where("month=?",calendarDay.getMonth()+"")
                .where("day=?",calendarDay.getDay()+"")
                .find(DateRecorder.class);

       return (dateRecorders.size() != 0);
    }

    @Override
    public void decorate(DayViewFacade view){
        view.addSpan(new CircleBackGroundSpan());
    }
}
