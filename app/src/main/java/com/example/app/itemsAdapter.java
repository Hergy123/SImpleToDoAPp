package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class itemsAdapter extends  RecyclerView.Adapter<itemsAdapter.ViewHolder>{
  public interface OnclickListerner {
      void onitemClicked(int position);
  }

    public interface OnLongClickListener{
        void onItemLongCliked(int position);

    }


    List<String> Add;
    OnLongClickListener longClickListener;
    OnclickListerner ClickListener;

    public itemsAdapter( List<String> Add ,  OnLongClickListener longClickListener , OnclickListerner ClickListener) {

        this.Add = Add;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View toolview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent ,false);

        return new ViewHolder(toolview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String Ad = Add.get(position);
        holder.blind(Ad);

    }

    @Override
    public int getItemCount() {

        return Add.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        public void blind(String ad) {

           textView.setText(ad);

           textView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   ClickListener.onitemClicked(getAdapterPosition());
               }
           });

           textView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   longClickListener.onItemLongCliked(getAdapterPosition());
                   return true;
               }
           });
        }
    }
}
