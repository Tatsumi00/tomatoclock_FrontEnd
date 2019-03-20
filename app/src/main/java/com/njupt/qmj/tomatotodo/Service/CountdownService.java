package com.njupt.qmj.tomatotodo.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.njupt.qmj.tomatotodo.Activity.TodoActivity;
import com.njupt.qmj.tomatotodo.LitePalDataBase.Achievement;
import com.njupt.qmj.tomatotodo.LitePalDataBase.DateRecorder;
import com.njupt.qmj.tomatotodo.LitePalDataBase.Time;
import com.njupt.qmj.tomatotodo.R;



import static org.litepal.Operator.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class CountdownService extends Service {
    public static final String TO_DO_NAME = "to_do_name";
    public static final String TO_DO_TIME = "to_do_time";
    String TodoName, TodoTime;
    int durationTime,durationTimeRec,count,repeatDetector,repeatCount,Grade = 100;
    long timerecorder;
    Calendar calendar;

    final String Channel_todocountdown_start_id = "1";
    final String Channel_todocountdown_start_name = "番茄钟倒计时开始";
    final String Channel_todocountdown_end_id = "2";
    final String Channel_todocountdown_end_name = "番茄钟倒计时结束";
    NotificationManager managerstart,managerdone;
    Notification notification;

    public CountDownTimer countDownTimer;

    private LocalBroadcastManager localBroadcastManager;

    private MediaPlayer player;

    private CountdownBinder countdownBinder = new CountdownBinder();

    public class CountdownBinder extends Binder{

        public void onStart(long millisInFuture, long countDownInterval){
              timerecorder = millisInFuture;
              countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
                  @Override
                  public void onTick(long l) {
                      notification = new Notification.Builder(getApplication())
                              .setContentTitle(TodoName + "正在进行中")
                              .setContentText((durationTime/ 60 < 10 ? "0" + durationTime / 60 : durationTime / 60) + ":" + (durationTime % 60 < 10 ? "0" + durationTime % 60 : durationTime % 60))
                              .setChannelId(Channel_todocountdown_start_id)
                              .setContentIntent(clickToApp())
                              .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
                              .setOnlyAlertOnce(true)
                              .setOngoing(true)
                              .build();
                      startForeground(1,notification);
                      count++;
                      Intent intent = new Intent("CountDowning");
                      intent.putExtra("duration",durationTime);
                      intent.putExtra("count",count);
                      localBroadcastManager.sendBroadcast(intent);
                      durationTime -= 1;
                  }

                  @Override
                  public void onFinish() {
                          stopForeground(true);
                          Intent intent = new Intent("CountDowning");
                          localBroadcastManager.sendBroadcast(intent);
                          if (repeatDetector == 0) {
                              Notification notification = new Notification.Builder(getApplication())
                                      .setSmallIcon(R.drawable.ic_check_black_24dp)
                                      .setContentTitle("番茄钟完成")
                                      .setContentText(TodoName + "已完成，用时" + TodoTime + "，相关数据已更新至数据统计")
                                      .setChannelId(Channel_todocountdown_end_id)
                                      .setContentIntent(clickToApp())
                                      .setAutoCancel(true)
                                      .build();
                              managerdone.notify(2, notification);
                              DateRecorder recorder = new DateRecorder(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH),durationTimeRec,repeatCount + 1,TodoName);
                              Log.d("123456",(calendar.get(Calendar.MONTH) + 1)  + "");
                              recorder.save();
                          }else if (repeatDetector == 1){
                              durationTime = durationTimeRec;
                              repeatCount++;
                              count = 0;
                              Notification notification1 = new Notification.Builder(getApplication())
                                      .setSmallIcon(R.drawable.ic_done_all_black_24dp)
                                      .setContentTitle("番茄钟完成")
                                      .setContentText(TodoName + "已完成循环" + repeatCount + "次，相关数据已更新至数据统计")
                                      .setChannelId(Channel_todocountdown_end_id)
                                      .setContentIntent(clickToApp())
                                      .setAutoCancel(true)
                                      .build();
                              managerdone.notify(2, notification1);
                              Intent clearintent = new Intent("ClearView");
                              clearintent.putExtra("repeatCount",repeatCount);
                              localBroadcastManager.sendBroadcast(clearintent);
                      }
                      if (where("todoName = ?",TodoName).find(Time.class).size() == 0){
                          Time time = new Time(TodoName,durationTimeRec);
                          time.save();
                      }else {
                          List<Time> time = where("todoName = ?",TodoName).find(Time.class);
                          Time time1 = time.get(0);
                          int durationOld = time1.getTodoTime();
                          time1.setTodoTime(durationOld + durationTimeRec);
                          time1.save();
                      }
                          SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                          int achievementCount =  sharedPreferences.getInt("achievementCount",0);
                          int achievementPoint = sharedPreferences.getInt("achievementPoint",0);
                          int gradeAverage = sharedPreferences.getInt("gradeAverage",100);
                          int tomatoCount = sharedPreferences.getInt("tomatoCount",1);
                          int totalTime = sharedPreferences.getInt("totalTime",0) + durationTimeRec;
                          LocalDate localDate = LocalDate.now();
                          String temp = localDate.getYear() + "." + localDate.getMonthValue() + "." + localDate.getDayOfMonth();
                          SharedPreferences.Editor editor = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                          editor.putInt("gradeAverage",(gradeAverage + Grade)/2);
                          editor.putInt("tomatoCount",tomatoCount + 1);
                          editor.putInt("totalTime",totalTime);
                          editor.putInt("startFor1",1);
                          switch (tomatoCount){
                              case 10:
                                  achievementCount ++;
                                  Achievement achievement1 = new Achievement("水滴石穿I",temp,10,R.drawable.ic_assignment_turned_in_brozne_24dp,"完成番茄钟10次");
                                  achievementPoint += 10;
                                  achievement1.save();
                                  break;
                              case 50:
                                  achievementCount ++;
                                  Achievement achievement2 = new Achievement("水滴石穿II",temp,50,R.drawable.ic_assignment_turned_in_silver_24dp,"完成番茄钟50次");
                                  achievementPoint += 50;
                                  achievement2.save();
                                  break;
                              case 100:
                                  achievementCount ++;
                                  Achievement achievement3 = new Achievement("水滴石穿III",temp,100,R.drawable.ic_assignment_turned_in_gold_24dp,"完成番茄钟100次");
                                  achievementPoint += 100;
                                  achievement3.save();
                                  break;
                              default:
                                  break;
                          }
                          editor.putInt("achievementCount",achievementCount);
                          editor.putInt("achievementPoint",achievementPoint);
                          editor.apply();
                      }
              };
              countDownTimer.start();
        }

        public void onClockPause(){
            countDownTimer.cancel();
        }


        public void musicOnPause(){
              player.stop();
              player.release();
              player = null;
        }

        public void setRepeat(int repeatId){
            repeatDetector = repeatId;
        }


        public void PlayMusic(int musicid){
            player = MediaPlayer.create(getApplication(),musicid);
            player.setLooping(true);
            player.start();
        }

        public void setGrade(int grade){
            Grade = grade;
        }

    }

    @Override
    public void onCreate(){
        calendar = Calendar.getInstance();
        managerdone = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel1 = new NotificationChannel(Channel_todocountdown_end_id, Channel_todocountdown_end_name, NotificationManager.IMPORTANCE_HIGH);
        managerdone.createNotificationChannel(channel1);
        managerstart = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel2 = new NotificationChannel(Channel_todocountdown_start_id, Channel_todocountdown_start_name, NotificationManager.IMPORTANCE_HIGH);
        managerstart.createNotificationChannel(channel2);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        countDownTimer.cancel();
        if (player != null) {
            player.release();
            player = null;
        }
        managerdone.cancel(2);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    public CountdownService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        TodoName = intent.getStringExtra(TO_DO_NAME);
        TodoTime = intent.getStringExtra(TO_DO_TIME);
        int stringLength = TodoTime.length();
        String temp = TodoTime.substring(0, stringLength - 2);
        durationTime = (Integer.valueOf(temp).intValue() * 1000 * 60 )/1000;
        durationTimeRec = durationTime;
        iniStartNotification();
        startForeground(1, notification);
        return countdownBinder;
    }



    public void iniStartNotification(){
       notification = new Notification.Builder(this)
                .setContentTitle(TodoName + "正在进行中")
                .setContentText((durationTime/ 60 < 10 ? "0" + durationTime / 60 : durationTime / 60) + ":" + (durationTime % 60 < 10 ? "0" + durationTime % 60 : durationTime % 60))
                .setChannelId(Channel_todocountdown_start_id)
                .setContentIntent(clickToApp())
                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
                .setOngoing(true)
                .build();
    }

    private PendingIntent clickToApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, TodoActivity.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        return pi;
    }




}
