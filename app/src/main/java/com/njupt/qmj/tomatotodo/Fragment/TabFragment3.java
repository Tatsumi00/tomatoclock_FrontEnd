package com.njupt.qmj.tomatotodo.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.njupt.qmj.tomatotodo.R;



import de.hdodenhof.circleimageview.CircleImageView;

public class TabFragment3 extends Fragment implements View.OnClickListener{

    int themeId;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.tab_fragment3, null);
        Toolbar toolbar = view.findViewById(R.id.mine_toolbar);
        toolbar.setTitle("我的");
        iniView(view);
        return view;
    }


    private void iniView(View view){
        RelativeLayout relativeLayout = view.findViewById(R.id.mine_header_background);
        relativeLayout.setBackgroundResource(R.drawable.mine_background);
        CircleImageView circleImageView = view.findViewById(R.id.mine_header_image);
        circleImageView.setImageResource(R.drawable.mine_image);
        RelativeLayout settings1 = view.findViewById(R.id.btn_more_theme);
        settings1.setOnClickListener(this);
        RelativeLayout settings2 = view.findViewById(R.id.btn_more_settings);
        settings2.setOnClickListener(this);
        RelativeLayout settings3 = view.findViewById(R.id.btn_about);
        settings3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_more_theme:
                View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_theme, null, false);
                final AlertDialog selectThemeDialog = new AlertDialog.Builder(getContext()).setView(v).create();
                final RadioGroup themeRadioGroup = (RadioGroup) v.findViewById(R.id.theme_radio_group);
                TypedValue typedValue = new TypedValue();
                getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
                Button positive = v.findViewById(R.id.theme_positive);
                Button negative = v.findViewById(R.id.theme_negative);
                positive.setTextColor(typedValue.data);
                negative.setTextColor(typedValue.data);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioButtonId = themeRadioGroup.getCheckedRadioButtonId();
                        switch (radioButtonId){
                            case R.id.orange_radio_button:
                                themeId = 0;
                                break;
                            case R.id.blue_radio_button:
                                themeId = 1;
                                break;
                                default:
                                    break;
                        }
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.putInt("ThemeNumber",themeId);
                        editor.apply();
                        Toast.makeText(getContext(),"主题已应用，重启生效",Toast.LENGTH_SHORT).show();
                        selectThemeDialog.dismiss();
                    }
                });
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectThemeDialog.dismiss();
                    }
                });
                selectThemeDialog.setTitle("选择背景颜色");
                selectThemeDialog.show();
                break;
            case R.id.btn_more_settings:
                break;
            case R.id.btn_about:
                break;
                default:
                    break;
        }

    }


}
