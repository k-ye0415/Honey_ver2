package com.ioad.honey.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioad.honey.activity.SelectMenuActivity;
import com.ioad.honey.bean.Menu;
import com.ioad.honey.R;
import com.ioad.honey.task.ImageLoadTask;
import com.ioad.honey.common.Constant;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    final String TAG = getClass().getSimpleName();

    private Context mContext;
    private int layout;
    private ArrayList<Menu> data;
    private LayoutInflater inflater;
    private ImageLoadTask task;


    public MenuRecyclerAdapter(Context mContext, int layout, ArrayList<Menu> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = Constant.SERVER_URL_IMG + data.get(position).getImage1();
        task = new ImageLoadTask(url, holder.ivMenu);
        task.execute();
        holder.tvMenuName.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivMenu;
        public TextView tvMenuName;


        public ViewHolder(View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            tvMenuName = itemView.findViewById(R.id.tv_menu_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, SelectMenuActivity.class);
                    intent.putExtra("mCode", data.get(getAdapterPosition()).getCode());
                    intent.putExtra("mName", data.get(getAdapterPosition()).getName());

                    mContext.startActivity(intent);
                }
            });

        }
    }


}
