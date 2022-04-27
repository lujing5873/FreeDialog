package com.example.freedialog.adp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;
import com.example.freedialog.model.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AddressAdp extends RecyclerView.Adapter<AddressAdp.ViewHolder> {

    private List<AddressModel> list;

    public AddressAdp( ) {
        this.list=new ArrayList<>();
    }

    public void setList(List<AddressModel> list){
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_text2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(getItemData(position).getName());
        holder.itemView.setVisibility(position==0||position>=getItemCount()-2?View.INVISIBLE :View.VISIBLE) ;

    }


    public   AddressModel getItemData(int position){
        if(list.isEmpty()){
            return  new AddressModel();
        }
        if(position>0&&position<=list.size()){
            return list.get(position-1);
        }
        return list.get(0);
    }

    @Override
    public int getItemCount() {
        return list.size()+3;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder (View view)
        {
            super(view);
            textView = view.findViewById(R.id.tv_name);
        }

    }
}
