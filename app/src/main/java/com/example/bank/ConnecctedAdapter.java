package com.example.bank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConnecctedAdapter extends RecyclerView.Adapter<ConnecctedAdapter.ViewHolder> {
    private List<ConnecctedItem> mItems;
    private final OnConnClickListener onClickListener;
    private boolean status; //для изменения цвета фона

    public ConnecctedAdapter(List<ConnecctedItem> items,  OnConnClickListener onClickListener, boolean status) {
        mItems = items;
        this.onClickListener = onClickListener;
        this.status=status;
    }

    interface OnConnClickListener{
        void onConnClick(ConnecctedItem conn, int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.connected_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ConnecctedItem conn = mItems.get(position);
        if(position%2==0){

            holder.LL.setBackgroundColor(Color.parseColor("#453230"));
        }
        else{
            holder.LL.setBackgroundColor(Color.parseColor("#59413E"));
        }
        ConnecctedItem item = mItems.get(position);
        holder.name.setText(item.getNameOfType());
        holder.iconImage.setImageResource(item.getIconImg());

        // обработка нажатия
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onConnClick(conn, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
       public CardView cv;
       public Button btn;
       public RecyclerView RV;
       public LinearLayout LL;
       public ImageView iconImage;

        public ViewHolder(View itemView) {
            super(itemView);
            RV = itemView.findViewById(R.id.rv_connected_item);
            name = (TextView) itemView.findViewById(R.id.conn_item_name);
            cv = (CardView) itemView.findViewById(R.id.cv_coneccted);
            LL = itemView.findViewById(R.id.llconn);
            iconImage = itemView.findViewById(R.id.conn_item_img);
        }
    }
}
