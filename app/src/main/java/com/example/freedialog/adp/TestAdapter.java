package com.example.freedialog.adp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;

public class TestAdapter extends ListAdapter<String, TestAdapter.ViewHolder> {


    private onClickListener listener;

    LinearLayoutManager linearLayoutManager;

    static DiffUtil.ItemCallback<String> diffCallback=new DiffUtil.ItemCallback<String>(){

        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };
    public TestAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_text,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(getItem(position));
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
