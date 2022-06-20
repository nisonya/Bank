package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DepositsAdapter extends RecyclerView.Adapter<DepositsAdapter.ViewHolder> {
    private List<Deposits> mDeposits;
    private final DepositsAdapter.OnDepClickListener onClickListener;

    public DepositsAdapter(List<Deposits> dep,  DepositsAdapter.OnDepClickListener onClickListener) {
        mDeposits = dep;
        this.onClickListener = onClickListener;
    }

    interface OnDepClickListener{
        void onDepClick(Deposits dep, int position);
    }

    @NonNull
    @Override
    public DepositsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.deposit_item, parent, false);
        DepositsAdapter.ViewHolder viewHolder = new DepositsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DepositsAdapter.ViewHolder holder, int position) {
        Deposits dep = mDeposits.get(position);
         holder.balance_dep.setText(String.valueOf(dep.getTotal()));
         holder.repl_dep.setText(String.valueOf(dep.getReplenishment()));
         holder.term_date_dep.setText(dep.getTerm_date());
        // обработка нажатия
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onDepClick(dep, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeposits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView balance_dep, term_date_dep, repl_dep;
        public ViewHolder(View itemView) {
            super(itemView);
            balance_dep = (TextView) itemView.findViewById(R.id.balance_dep);
            term_date_dep = (TextView) itemView.findViewById(R.id.term_date_deposit);
            repl_dep = (TextView) itemView.findViewById(R.id.replen);
        }
    }

}
