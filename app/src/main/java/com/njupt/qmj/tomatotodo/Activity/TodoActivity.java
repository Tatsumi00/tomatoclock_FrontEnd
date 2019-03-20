package com.njupt.qmj.tomatotodo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.njupt.qmj.tomatotodo.R;
import com.njupt.qmj.tomatotodo.Service.CountdownService;
import com.njupt.qmj.tomatotodo.view.CountdownView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class TodoActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TO_DO_NAME = "to_do_name";
    public static final String TO_DO_TIME = "to_do_time";
    TextView starttodo_slogan, starttodo_name;
    String TodoName, TodoTime,pauseTimeString;
    int durationtime,pauseRecordTime,musicid,repeatCount,grade = 100;

    int pauseTime = 180000;
    int durationtimeInsec;

    String tag = "TodoActivity";
    ImageButton alwaysOn,playMusic,repeat;

    private CountdownService.CountdownBinder countdownBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                 countdownBinder = (CountdownService.CountdownBinder) iBinder;
                 int stringLength = TodoTime.length();
                 String temp = TodoTime.substring(0, stringLength - 2);
                 durationtime = (Integer.valueOf(temp).intValue() * 1000 * 60);
                 pauseRecordTime = durationtime/1000;
                 countdownBinder.onStart(durationtime, 1000);
                 iniView();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    static int AlwaysOnDetector = 0;
    static int CompleteDector = 0;
    static int musicOnDetector = 0;
    static int pauseDetector = 0;
    static int repeatDetector = 0;

    private LocalReceiver1 localReceiver1;
    private LocalReceiver2 localReceiver2;
    private IntentFilter intentFilter1,intentFilter2;
    private LocalBroadcastManager localBroadcastManager1,localBroadcastManager2;

    private CountdownView countdownView;




    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Intent intent = getIntent();
        TodoName = intent.getStringExtra(TO_DO_NAME);
        TodoTime = intent.getStringExtra(TO_DO_TIME);
        Intent bindServiceIntent = new Intent(this,CountdownService.class);
        bindServiceIntent.putExtra(CountdownService.TO_DO_NAME,TodoName);
        bindServiceIntent.putExtra(CountdownService.TO_DO_TIME,TodoTime);
        bindService(bindServiceIntent,connection,BIND_AUTO_CREATE);
        intentFilter1 = new IntentFilter();
        intentFilter1.addAction("CountDowning");
        localReceiver1 = new LocalReceiver1();
        localBroadcastManager1 = LocalBroadcastManager.getInstance(this);
        localBroadcastManager1.registerReceiver(localReceiver1,intentFilter1);
        intentFilter2 = new IntentFilter();
        intentFilter2.addAction("ClearView");
        localReceiver2 = new LocalReceiver2();
        localBroadcastManager2 = LocalBroadcastManager.getInstance(this);
        localBroadcastManager2.registerReceiver(localReceiver2,intentFilter2);
    }


    public void iniView() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_starttodo);
        alwaysOn = (ImageButton) findViewById(R.id.always_on_button);
        alwaysOn.setOnClickListener(this);
        playMusic = (ImageButton) findViewById(R.id.play_music_button);
        playMusic.setOnClickListener(this);
        repeat = (ImageButton) findViewById(R.id.repeat_button);
        repeat.setOnClickListener(this);
        ImageButton stopAndExit = (ImageButton) findViewById(R.id.stop_button);
        stopAndExit.setOnClickListener(this);
        ImageButton pauseClock = (ImageButton) findViewById(R.id.pause_button);
        pauseClock.setOnClickListener(this);
        countdownView = (CountdownView) findViewById(R.id.count_down_view);
        countdownView.setListener(new CountdownView.onFinishListener() {
            @Override
            public void onFinish() {
                if (repeatDetector == 0) {
                    starttodo_name.setText(TodoName + "\n已完成");
                    CompleteDector = 1;
                }else if (repeatDetector == 1){
                    starttodo_name.setText(TodoName + "\n循环已进行" + (repeatCount + 1) + "次");
                }
            }
        });
        starttodo_name = findViewById(R.id.starttodo_name);
        starttodo_name.setText(TodoName + "\n正在进行中");
        starttodo_slogan = findViewById(R.id.starttodo_slogan);
        starttodo_slogan.setText(RandomSlogan());
    }

    public String RandomSlogan() {
        String array[] = {"人生最大的冒险莫过于，按照自己所梦想的轨迹而活。", "开始就是停止空谈，毫不犹豫，马上行动。", "行动是治愈恐惧的良药，而犹豫拖延将不断滋养恐惧。",
                "Whaterver is worth doing is worth doing well.", "Do one thing at a time, and do well.", "有些事不是看到了希望才去坚持，而是坚持了才看到希望。"};
        Random random = new Random();
        return array[random.nextInt(6)];
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        localBroadcastManager1.unregisterReceiver(localReceiver1);
        localBroadcastManager2.unregisterReceiver(localReceiver2);
        if (AlwaysOnDetector == 1){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            AlwaysOnDetector = 0;
        }
        musicOnDetector = 0;
        CompleteDector = 0;
        pauseDetector = 0;
        repeatDetector = 0;
    }

    @Override
    public void onPause(){
        grade -= 5;
        countdownBinder.setGrade(grade);
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (AlwaysOnDetector == 1){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onBackPressed() {
        if (CompleteDector == 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(TodoActivity.this);
            dialog.setTitle("退出计时");
            dialog.setMessage("确定要退出计时吗，未完成计时将不会获得番茄");
            dialog.setCancelable(true);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            dialog.show();
        }else{
            super.onBackPressed();
        }
    }

    class LocalReceiver1 extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            durationtimeInsec = intent.getIntExtra("duration",0);
            pauseRecordTime = durationtimeInsec;
            int count = intent.getIntExtra("count",durationtime/1000 + 1);
            countdownView.onUpdateTime(durationtimeInsec,count);
        }
    }

    class LocalReceiver2 extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            repeatCount = intent.getIntExtra("repeatCount",0);
            countdownView.clearView(repeatCount);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Looper.prepare();
                    countdownView.clearView(repeatCount);
                    countdownBinder.onStart(durationtime, 1000);
                    Looper.loop();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task,3000);
        }
    }



    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.always_on_button:
                if (AlwaysOnDetector == 0) {
                    alwaysOn.setImageResource(R.drawable.ic_wb_sunny_white_24dp);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    Toast.makeText(TodoActivity.this, "已开启屏幕常亮", Toast.LENGTH_SHORT).show();
                    AlwaysOnDetector = 1;
                } else if (AlwaysOnDetector == 1) {
                    alwaysOn.setImageResource(R.drawable.ic_wb_sunny_black_24dp);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    Toast.makeText(TodoActivity.this, "已关闭屏幕常亮", Toast.LENGTH_SHORT).show();
                    AlwaysOnDetector = 0;
                }
                break;
            case R.id.stop_button:
                onBackPressed();
                break;
            case R.id.play_music_button:
                if (musicOnDetector == 0) {
                    View view = LayoutInflater.from(this).inflate(R.layout.select_music_to_play_layout, null, false);
                    final AlertDialog selectMusicDialog = new AlertDialog.Builder(this).setView(view).create();
                    final RadioGroup musicRadioGroup = (RadioGroup) view.findViewById(R.id.music_radio_group);
                    TypedValue typedValue = new TypedValue();
                    getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
                    Button positive = view.findViewById(R.id.select_music_positive);
                    Button negative = view.findViewById(R.id.select_music_negative);
                    positive.setTextColor(typedValue.data);
                    negative.setTextColor(typedValue.data);
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int radioButtonId = musicRadioGroup.getCheckedRadioButtonId();
                            switch (radioButtonId) {
                                case R.id.rain_radio_button:
                                    musicid = R.raw.whitenoise_rain;
                                    break;
                                case R.id.nature_radio_button:
                                    musicid = R.raw.whitenoise_nature;
                                    break;
                                case R.id.stars_radio_button:
                                    musicid = R.raw.whitenoise_stars;
                                    break;
                            }
                            musicOnDetector = 1;
                            countdownBinder.PlayMusic(musicid);
                            playMusic.setImageResource(R.drawable.ic_audiotrack_white_24dp);
                            selectMusicDialog.dismiss();
                        }
                    });

                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectMusicDialog.dismiss();
                        }
                    });
                    selectMusicDialog.setTitle("选择番茄钟的背景音乐");
                    selectMusicDialog.show();
                } else if (musicOnDetector == 1) {
                    countdownBinder.musicOnPause();
                    playMusic.setImageResource(R.drawable.ic_audiotrack_black_24dp);
                    musicOnDetector = 0;
                }
                break;
            case R.id.pause_button:
                if (pauseDetector == 0) {
                    final ProgressDialog pauseDialog = new ProgressDialog(TodoActivity.this);
                    pauseDialog.setTitle("暂停计时");
                    pauseDialog.setMessage("番茄钟能进行3分钟的暂停，现在剩余3:00（点击弹窗外继续计时）");
                    pauseDialog.setCancelable(true);
                    pauseDialog.show();
                    final CountDownTimer countDownTimer = new CountDownTimer(pauseTime, 1000) {
                        @Override
                        public void onTick(long l) {
                            int pauseTime1 = pauseTime / 1000;
                            pauseTimeString = (pauseTime1 / 60 < 10 ? "0" + pauseTime1 / 60 : pauseTime1 / 60) + ":" + (pauseTime1 % 60 < 10 ? "0" + pauseTime1 % 60 : pauseTime1 % 60);
                            pauseTime -= 1000;
                            pauseDialog.setMessage("番茄钟能进行3分钟的暂停，现在剩余" + pauseTimeString + "（点击弹窗外继续计时）");
                        }

                        @Override
                        public void onFinish() {
                            grade -= 10;
                            countdownBinder.setGrade(grade);
                            pauseDetector = 1;
                            pauseDialog.setMessage("暂停时间结束（点击弹窗外退出暂停）");
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    pauseDialog.dismiss();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 3000);
                        }
                    };
                    countDownTimer.start();
                    countdownBinder.onClockPause();
                    pauseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            countdownBinder.onStart(pauseRecordTime * 1000, 1000);
                            countDownTimer.cancel();
                        }
                    });
                }else if (pauseDetector == 1){
                    Toast.makeText(this,"暂停时间已用尽",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.repeat_button:
                if (repeatDetector == 0){
                    countdownBinder.setRepeat(1);
                    Toast.makeText(this,"循环计时已开启",Toast.LENGTH_SHORT).show();
                    repeat.setImageResource(R.drawable.ic_repeat_white_24dp);
                    repeatDetector = 1;
                }else if (repeatDetector == 1){
                    countdownBinder.setRepeat(0);
                    repeat.setImageResource(R.drawable.ic_repeat_black_24dp);
                    Toast.makeText(this,"循环计时已关闭",Toast.LENGTH_SHORT).show();
                    repeatDetector = 0;
                }
                break;

        };
    }









    }
