package com.ioad.honey.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioad.honey.Bean.Tip;
import com.ioad.honey.R;

import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Tip> tips;
    private LayoutInflater inflater;

    String tipCode;


    public TipAdapter(Context mContext, int layout, ArrayList<Tip> tips) {
        this.mContext = mContext;
        this.layout = layout;
        this.tips = tips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = tips.get(position).getcId().substring(0,2) + "***";
        String name = tips.get(position).getcName().substring(0,1) + "***";

        holder.tv_tip_id.setText(id);
        holder.tv_tip_name.setText(name);
        holder.tv_tip_title.setText(tips.get(position).getTipContent());
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tip_title;
        public TextView tv_tip_id;
        public TextView tv_tip_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_tip_title = itemView.findViewById(R.id.tv_tip_title);
            tv_tip_id = itemView.findViewById(R.id.tv_tip_id);
            tv_tip_name = itemView.findViewById(R.id.tv_tip_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = tips.get(getAdapterPosition()).getcId();
                    tipCode = tips.get(getAdapterPosition()).getTipCode();



                }
            });

        }

    }

}
