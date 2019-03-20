package com.njupt.qmj.tomatotodo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.njupt.qmj.tomatotodo.Activity.TodoActivity;
import com.njupt.qmj.tomatotodo.Class.Todo;
import com.njupt.qmj.tomatotodo.R;


import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context mContext;
    private List<Todo> mTodoList;



    public TodoAdapter(List<Todo> TodoList) {
        mTodoList = TodoList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        CardView cardView;
        TextView TodoName;
        TextView TodoIntro;
        ImageView TodoBackgound;


        public ViewHolder(View view){
            super(view);
            mView = view;
            cardView = (CardView) view;
            TodoName = (TextView) view.findViewById(R.id.todo_item_name);
            TodoIntro = (TextView) view.findViewById(R.id.todo_item_intro);
            TodoBackgound = (ImageView)view.findViewById(R.id.todo_item_background);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentView, int viewType) {
        if (mContext == null) {
            mContext = parentView.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_item_layout, parentView, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Todo todo = mTodoList.get(position);
                Intent intent = new Intent(mContext, TodoActivity.class);
                intent.putExtra(TodoActivity.TO_DO_TIME, todo.getTodoIntro());
                intent.putExtra(TodoActivity.TO_DO_NAME, todo.getTodoName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final ViewHolder recyclerViewHolder = (ViewHolder) holder;
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        recyclerViewHolder.mView.setAnimation(animation);
        Todo todo = mTodoList.get(position);
        holder.TodoName.setText(todo.getTodoName());
        holder.TodoIntro.setText(todo.getTodoIntro());
        Glide.with(mContext).load(todo.getImageId()).centerCrop().into(holder.TodoBackgound);
    }

    @Override
    public int getItemCount(){
        return mTodoList.size();
    }







}

