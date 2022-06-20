package com.example.bank.history;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryItem> mHistoryItems;
    private final OnHisClickListener onClickListener;

    public HistoryAdapter(List<HistoryItem> his, OnHisClickListener onClickListener) {
        mHistoryItems = his;
        this.onClickListener = onClickListener;
    }

    interface OnHisClickListener{
        void onHisClick(HistoryItem historyItem, int position);
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.history_item, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryItem historyItem = mHistoryItems.get(position);
        holder.diff.setText(String.valueOf(historyItem.getDiff()));
        holder.date.setText(String.valueOf(historyItem.getDate_of_change()));
        holder.remainder.setText("Остаток: "+String.valueOf(historyItem.getBalance_after()));
        if(historyItem.getDiff()>0){
            holder.diff.setTextColor(Color.parseColor("#6bdb6b"));
        }
        else{
            holder.diff.setTextColor(Color.parseColor("#fc6f6f"));
        }
        if(historyItem.getTrans_type()==2){
            holder.type.setImageResource(R.drawable.oplata);
        }
        // обработка нажатия
        holder.itemView.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onHisClick(historyItem, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mHistoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView diff, date, remainder;
        public ImageView type;
        public ViewHolder(View itemView) {
            super(itemView);
            diff = (TextView) itemView.findViewById(R.id.trans_amount);
            date = (TextView) itemView.findViewById(R.id.trans_date);
            remainder = (TextView) itemView.findViewById(R.id.trans_mod);
            type = (ImageView) itemView.findViewById(R.id.iv_type);
        }
    }

}
