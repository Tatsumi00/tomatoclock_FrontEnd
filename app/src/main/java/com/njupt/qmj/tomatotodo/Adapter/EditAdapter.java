package com.njupt.qmj.tomatotodo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.njupt.qmj.tomatotodo.Class.Todo;
import com.njupt.qmj.tomatotodo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.ViewHolder>{

    private Context mContext;
    private List<Todo> mTodoList;
    private List<String> mItems;

    public EditAdapter(List<Todo> TodoList) {
        mTodoList = TodoList;
        mItems =  new ArrayList<>();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        CardView cardView;
        TextView TodoName;
        TextView TodoIntro;


        public ViewHolder(View view){
            super(view);
            mView = view;
            cardView = (CardView) view;
            TodoName = (TextView) view.findViewById(R.id.edit_item_name);
            TodoIntro = (TextView) view.findViewById(R.id.edit_item_intro);
        }
    }

    @Override
    public EditAdapter.ViewHolder onCreateViewHolder(ViewGroup parentView, int viewType) {
        if (mContext == null) {
            mContext = parentView.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_item_layout, parentView, false);
        final EditAdapter.ViewHolder holder = new EditAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"12345",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(EditAdapter.ViewHolder holder, int position){
        final EditAdapter.ViewHolder recyclerViewHolder = (EditAdapter.ViewHolder) holder;
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        recyclerViewHolder.mView.setAnimation(animation);
        Todo todo = mTodoList.get(position);
        holder.TodoName.setText(todo.getTodoName());
        holder.TodoIntro.setText(todo.getTodoIntro());
    }

    @Override
    public int getItemCount(){
        return mTodoList.size();
    }


}
