package com.example.bank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private List<Cards> mCards;
    private final CardsAdapter.OnCardClickListener onClickListener;
    private boolean status; //для изменения цвета фона

    public CardsAdapter(List<Cards> cards,  CardsAdapter.OnCardClickListener onClickListener, boolean status) {
        mCards = cards;
        this.onClickListener = onClickListener;
        this.status = status;
    }

    interface OnCardClickListener{
        void onCardClick(Cards conn, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(status) {
            holder.back.setBackgroundColor(Color.parseColor("#453230"));
            holder.image.setImageResource(R.drawable.cards);
            holder.balance.setTextColor(Color.parseColor("#E5DFDD"));
            holder.type_card.setTextColor(Color.parseColor("#E5DFDD"));
            holder.card_number.setTextColor(Color.parseColor("#E5DFDD"));
        }
        Cards card = mCards.get(position);
        holder.balance.setText(card.getBalance().toString());
        holder.type_card.setText(String.valueOf(card.getCard_type()));
        holder.card_number.setText(card.getCard_number());

        // обработка нажатия
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onCardClick(card, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView balance, type_card, card_number;
        public ImageView image;
        public LinearLayout back;

        public ViewHolder(View itemView) {
            super(itemView);
            balance = (TextView) itemView.findViewById(R.id.balance);
            type_card = (TextView) itemView.findViewById(R.id.type_card);
            card_number = (TextView) itemView.findViewById(R.id.number_card);
            back = itemView.findViewById(R.id.liner_card);
            image = itemView.findViewById(R.id.imageView11);
        }
    }

}
