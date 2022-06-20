package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    private List<Credit> mCredits;
    private final CreditAdapter.OnCredClickListener onClickListener;

    public CreditAdapter(List<Credit> cre,  CreditAdapter.OnCredClickListener onClickListener) {
        mCredits = cre;
        this.onClickListener = onClickListener;
    }

    interface OnCredClickListener{
        void onCredClick(Credit cred, int position);
    }

    @NonNull
    @Override
    public CreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.credit_item, parent, false);
        CreditAdapter.ViewHolder viewHolder = new CreditAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.ViewHolder holder, int position) {
        Credit cred = mCredits.get(position);
        holder.remainder_cred.setText(String.valueOf(cred.getTotal()));
        String date =cred.getTerm_date().substring(0,10);
        holder.term_date_cred.setText(date);
        holder.payment_cred.setText(String.valueOf(cred.getMonthly_payment()));
        // обработка нажатия
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onCredClick(cred, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCredits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView remainder_cred, term_date_cred, payment_cred;
        public ViewHolder(View itemView) {
            super(itemView);
            remainder_cred = (TextView) itemView.findViewById(R.id.remainder);
            term_date_cred = (TextView) itemView.findViewById(R.id.term_date_credit);
            payment_cred = (TextView) itemView.findViewById(R.id.payment);
        }
    }

}
