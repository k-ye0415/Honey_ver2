package com.ioad.honey.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioad.honey.bean.Tip;
import com.ioad.honey.R;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.DeleteNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Tip> tips;
    private LayoutInflater inflater;

    private String tipCode;
    private String userId, userName;
    private Dialog deleteDialog;
    private TextView tvDeleteContent;
    private Button btnDelete, btnCancel;
    private String url;


    public TipAdapter(Context mContext, int layout, ArrayList<Tip> tips) {
        this.mContext = mContext;
        this.layout = layout;
        this.tips = tips;
        this.userId = Shared.getStringPref(mContext, "USER_ID");
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

        userName = tips.get(position).getcName();
        holder.tvTipId.setText(id);
        holder.tvTipName.setText(name);
        holder.tvTipTitle.setText(tips.get(position).getTipContent());
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTipTitle;
        public TextView tvTipId;
        public TextView tvTipName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTipTitle = itemView.findViewById(R.id.tv_tip_title);
            tvTipId = itemView.findViewById(R.id.tv_tip_id);
            tvTipName = itemView.findViewById(R.id.tv_tip_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = tips.get(getAdapterPosition()).getcId();
                    tipCode = tips.get(getAdapterPosition()).getTipCode();

                    if (id.equals(userId)) {
                        deleteDialog = new Dialog(view.getContext());
                        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        deleteDialog.setContentView(R.layout.tip_delete_dialog);

                        WindowManager.LayoutParams lbDown = new WindowManager.LayoutParams();
                        lbDown.copyFrom(deleteDialog.getWindow().getAttributes());
                        lbDown.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lbDown.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Window window = deleteDialog.getWindow();
                        window.setAttributes(lbDown);

                        deleteDialog.show();
                        tvDeleteContent = deleteDialog.findViewById(R.id.tv_dialog_delete_content);
                        btnCancel = deleteDialog.findViewById(R.id.btn_dialog_cancel);
                        btnDelete = deleteDialog.findViewById(R.id.btn_dialog_delete);

                        tvDeleteContent.setText("?????? ?????? ???????????????..? \n?????? ????????? " + userName + "?????? ?????? ??????????????????..?");

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        });

                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = tipDeleted();
                                if (result.equals("1")) {
//                                    deleteOnClickListener.onDeleteClickListener(true);
//                                    Toast.makeText(mContext, R.string.tip_remove, Toast.LENGTH_SHORT).show();
                                    Util.showToast(mContext, "?????? ?????? ??????");
                                }
                                deleteDialog.dismiss();
                            }
                        });
                    } else {
//                        Toast.makeText(mContext, R.string.self_tip_remove, Toast.LENGTH_SHORT).show();
                        Util.showToast(mContext, "?????? ??? ????????? ?????? ????????????");
                    }

                }
            });

        }

    }

    private String tipDeleted() {
        url = Constant.SERVER_URL_JSP + "tip_delete.jsp?code=" + tipCode;
        String result = null;
        try {
            DeleteNetworkTask networkTask = new DeleteNetworkTask(mContext, url, "delete");
            Object obj = networkTask.execute().get();
            result = (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("result", result);
        return result;
    }

}
