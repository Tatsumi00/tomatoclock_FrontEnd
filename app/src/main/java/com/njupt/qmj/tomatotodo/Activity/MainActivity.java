package com.njupt.qmj.tomatotodo.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.njupt.qmj.tomatotodo.Adapter.BottomNavigationAdapter;
import com.njupt.qmj.tomatotodo.Fragment.TabFragment3;
import com.njupt.qmj.tomatotodo.ViewPager.ViewPagerSlide;
import com.njupt.qmj.tomatotodo.Fragment.TabFragment1;
import com.njupt.qmj.tomatotodo.Fragment.TabFragment2;
import com.njupt.qmj.tomatotodo.R;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPagerSlide viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("待办");
        iniBottomNavigation();
    }


    private void iniBottomNavigation(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPagerSlide) findViewById(R.id.main_viewPager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_Todo:
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.bottom_navigation_statics:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.bottom_navigation_settings:
                        viewPager.setCurrentItem(2);
                        break;

                }
                return false;
            }
        });
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                switch (position){
                    case 0:
                        getSupportActionBar().show();
                        toolbar.setTitle("待办");
                        break;
                    case 1:
                        getSupportActionBar().show();
                        toolbar.setTitle("统计数据");
                        break;
                    case 2:
                        getSupportActionBar().hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
        BottomNavigationAdapter adapter = new BottomNavigationAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragment1());
        adapter.addFragment(new TabFragment2());
        adapter.addFragment(new TabFragment3());
        viewPager.setAdapter(adapter);
    }



}
