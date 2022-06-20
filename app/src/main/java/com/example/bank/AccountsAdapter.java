package com.example.bank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder>{
    private List<Accounts> mAccounts;
    private final AccountsAdapter.OnAcClickListener onClickListener;

    public AccountsAdapter(List<Accounts> acc,  AccountsAdapter.OnAcClickListener onClickListener) {
        mAccounts = acc;
        this.onClickListener = onClickListener;
    }

    interface OnAcClickListener{
        void onAccClick(Accounts acc, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.account_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsAdapter.ViewHolder holder, int position) {
        Accounts acc = mAccounts.get(position);
        holder.acc_number.setText(acc.getFrst_order_balance()+" "+acc.getSecond_order_balance()+" "+acc.getCurrency_cade()+" "+acc.getControl_digit()+" "+acc.getBank_division_code()+" "+acc.getBank_account_number());
        holder.balance_acc.setText(String.valueOf(acc.getAccount_balance()));
        // обработка нажатия
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onAccClick(acc, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView balance_acc, acc_number;
        public ViewHolder(View itemView) {
            super(itemView);
            balance_acc = (TextView) itemView.findViewById(R.id.balance_acc);
            acc_number = (TextView) itemView.findViewById(R.id.number_acc);
        }
    }

}

