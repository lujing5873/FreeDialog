package com.example.freedialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

public class TestAdapter2 extends ListAdapter<SelectModel, TestAdapter2.ViewHolder> {


    private onClickListener listener;

    static DiffUtil.ItemCallback<SelectModel> diffCallback=new DiffUtil.ItemCallback<SelectModel>(){

        @Override
        public boolean areItemsTheSame(@NonNull SelectModel oldItem, @NonNull SelectModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull SelectModel oldItem, @NonNull SelectModel newItem) {
            return oldItem.equals(newItem);
        }
    };
    public TestAdapter2() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_text2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(getItem(position).getText());

        holder.itemView.setOnClickListener(v -> {
            if(listener!=null){
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }


     class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder (View view)
        {
            super(view);
            textView = view.findViewById(R.id.tv_name);
        }

    }


    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener{
        void onItemClick(int position);
    }

}
