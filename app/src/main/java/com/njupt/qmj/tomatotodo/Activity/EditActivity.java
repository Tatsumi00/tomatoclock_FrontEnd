package com.njupt.qmj.tomatotodo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.njupt.qmj.tomatotodo.Adapter.EditAdapter;
import com.njupt.qmj.tomatotodo.Class.Todo;
import com.njupt.qmj.tomatotodo.LitePalDataBase.TodoDataBase;
import com.njupt.qmj.tomatotodo.R;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.Operator.findAll;

public class EditActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    private List<Todo> TodosList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("编辑");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.edit_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        iniData();
        EditAdapter editAdapter = new EditAdapter(TodosList);
        recyclerView.setAdapter(editAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void iniData(){
        List<TodoDataBase> dataBases = findAll(TodoDataBase.class);
        TodosList.clear();
        for (TodoDataBase todoDataBase: dataBases){
            TodosList.add(new Todo(todoDataBase.getName(),todoDataBase.getTime(),todoDataBase.getBackgorund()));
        }
    }
}
