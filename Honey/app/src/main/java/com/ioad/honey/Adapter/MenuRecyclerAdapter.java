package com.ioad.honey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioad.honey.Activity.SelectMenuActivity;
import com.ioad.honey.Bean.Menu;
import com.ioad.honey.R;
import com.ioad.honey.Task.ImageLoadTask;
import com.ioad.honey.common.Constant;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    final String TAG = getClass().getSimpleName();

    private Context mContext;
    private int layout;
    private ArrayList<Menu> data;
    private LayoutInflater inflater;
    ImageLoadTask task;


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
        task = new ImageLoadTask(url, holder.iv_menu);
        task.execute();
        holder.tv_menu_name.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_menu;
        public TextView tv_menu_name;


        public ViewHolder(View itemView) {
            super(itemView);
            iv_menu = itemView.findViewById(R.id.iv_menu);
            tv_menu_name = itemView.findViewById(R.id.tv_menu_name);

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
