package com.example.freedialog.adp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;
import com.example.freedialog.model.AddressModel;
import com.example.freedialog.model.SelectModel;

public class TestAdapter2 extends ListAdapter<AddressModel, TestAdapter2.ViewHolder> {


    private onClickListener listener;


    public TestAdapter2() {
        super(new DiffUtil.ItemCallback<AddressModel>(){

            @Override
            public boolean areItemsTheSame(@NonNull AddressModel oldItem, @NonNull AddressModel newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull AddressModel oldItem, @NonNull AddressModel newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_text2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(getItem(position).getName());
        holder.itemView.setVisibility(position==0||position>=getItemCount()-2?View.INVISIBLE :View.VISIBLE) ;
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


    @Override
    public int getItemCount() {
        if(getCurrentList().isEmpty()){
            return 0;
        }
        return super.getItemCount()+3;
    }

    @Override
    protected AddressModel getItem(int position) {
        if(getCurrentList().isEmpty()){
            return  new AddressModel();
        }
        if(position>0&&position<=getCurrentList().size()){
            return getCurrentList().get(position-1);
        }
        return getCurrentList().get(0);
    }
}
