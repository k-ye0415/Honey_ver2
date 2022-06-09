package com.ioad.honey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioad.honey.R;
import com.ioad.honey.bean.BuyHistory;

import java.util.ArrayList;

public class BuyHistoryAdapter extends RecyclerView.Adapter<BuyHistoryAdapter.ViewHolder> {

    private Context mContext;
    private int layout = 0;
    private ArrayList<BuyHistory> histories = null;

    public BuyHistoryAdapter(Context mContext, int layout, ArrayList<BuyHistory> histories) {
        this.mContext = mContext;
        this.layout = layout;
        this.histories = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_history_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int count = Integer.parseInt(histories.get(position).getCount()) - 1;
        holder.tvBuyNum.setText("주문번호 : " + histories.get(position).getBuyNm());
        holder.tvBuyName.setText(histories.get(position).getiName() + histories.get(position).getiCapacity()
                + histories.get(position).getiUnit() + " 외 " + count + "개");
        holder.tvBuyPrice.setText("금액 : " + histories.get(position).getBuyDeliveryPrice());

        if (histories.get(position).getBuyCancelDay().equals("null")) {
            holder.tvBuyDay.setText("주문일자 : " + histories.get(position).getBuyDay());
            holder.tvBuyDay.setTextColor(0xFF000000);
        } else {
            holder.tvBuyDay.setText("취소일자 : " + histories.get(position).getBuyCancelDay());
            holder.tvBuyDay.setTextColor(0xFFFF0000);
        }
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBuyNum, tvBuyName, tvBuyPrice, tvBuyDay;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBuyNum = itemView.findViewById(R.id.tv_buy_num);
            tvBuyName = itemView.findViewById(R.id.tv_buy_name);
            tvBuyPrice = itemView.findViewById(R.id.tv_buy_price);
            tvBuyDay = itemView.findViewById(R.id.tv_buy_day);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    // MyPage 끝나고
                }
            });
        }
    }
}
