package com.njupt.qmj.tomatotodo.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.njupt.qmj.tomatotodo.Activity.CalendarActivity;
import com.njupt.qmj.tomatotodo.Activity.EditActivity;
import com.njupt.qmj.tomatotodo.Adapter.TodoAdapter;
import com.njupt.qmj.tomatotodo.Class.Todo;
import com.njupt.qmj.tomatotodo.LitePalDataBase.TodoDataBase;
import com.njupt.qmj.tomatotodo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.litepal.Operator.*;

public class TabFragment1 extends Fragment implements View.OnClickListener{

    int time;
    RecyclerView mRecyclerView;

    private List<Todo> TodosList = new ArrayList<>();
    


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, null);
        setHasOptionsMenu(true);
        iniView(view);
        return view;
    }

    public void iniView(View view){
        FloatingActionButton fab = view.findViewById(R.id.fab_recycler_view);
        fab.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.Todo_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(layoutManager);
        iniData();
        TodoAdapter todoAdapter = new TodoAdapter(TodosList);
        mRecyclerView.setAdapter(todoAdapter);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.fab_recycler_view:
              iniDialog();
              break;
        }

    }

public void iniDialog(){
    View view = LayoutInflater.from(getContext()).inflate(R.layout.new_todo_dialog,null,false);
    final AlertDialog createDialog = new AlertDialog.Builder(getContext()).setView(view).create();
    Button positive = view.findViewById(R.id.create_to_do_positive);
    Button negative = view.findViewById(R.id.create_to_do_negative);
    TypedValue typedValue = new TypedValue();
    getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
    positive.setTextColor(typedValue.data);
    negative.setTextColor(typedValue.data);
    final EditText timeinputfield = view.findViewById(R.id.create_todo_time);
    final EditText nameinputfield = view.findViewById(R.id.create_todo_name);
    positive.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = nameinputfield.getText().toString();
            try {
                time = Integer.parseInt(timeinputfield.getText().toString());
            }catch (NumberFormatException e){
                time = 0;
            }
            List< TodoDataBase > todoDataBases = where("name=?",name)
                    .where("time=?",time+"分钟")
                    .find(TodoDataBase.class);
            boolean ifExists = todoDataBases.size() != 0;
            if (name.equals("")) {
                nameinputfield.setError("番茄钟名字不能为空");
            }
            else if (time > 180){
                timeinputfield.setError("番茄钟时间不能大于180分钟");
            }
            else if (time < 1){
                timeinputfield.setError("番茄钟时间不能小于1分钟");
            } else if (ifExists){
                nameinputfield.setError("已有相同番茄钟");
                timeinputfield.setError("已有相同番茄钟");
            }
            else{
                TodoDataBase todoDataBase = new TodoDataBase();
                todoDataBase.setName(name);
                todoDataBase.setTime(time + "分钟");
                todoDataBase.setBackgorund(RandomArtCard());
                todoDataBase.save();
                createDialog.dismiss();
                iniData();
            }
        }


    });
    negative.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(),"番茄钟未保存",Toast.LENGTH_SHORT).show();
            createDialog.dismiss();
        }
    });
    createDialog.setTitle("创建番茄钟");
    createDialog.setCancelable(true);
    timeinputfield.setInputType(InputType.TYPE_CLASS_NUMBER);
    createDialog.show();
}

public void iniData() {
        List<TodoDataBase> dataBases = findAll(TodoDataBase.class);
        TodosList.clear();
        for (TodoDataBase todoDataBase: dataBases){
            TodosList.add(new Todo(todoDataBase.getName(),todoDataBase.getTime(),todoDataBase.getBackgorund()));
        }

}

public int RandomArtCard(){
        int array[] = {R.drawable.art_card1,R.drawable.art_card2,R.drawable.art_card3,R.drawable.art_card4,
                R.drawable.art_card5,R.drawable.art_card6,R.drawable.art_card7,R.drawable.art_card8};
         Random random = new Random();
        return array[random.nextInt(8)];
}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.todo_toolbar_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sort_todo_item:
                Intent intent = new Intent(getContext(), EditActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
        return true;
    }





}
