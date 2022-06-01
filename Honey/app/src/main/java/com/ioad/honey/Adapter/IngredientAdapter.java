package com.ioad.honey.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioad.honey.Bean.Ingredient;
import com.ioad.honey.R;
import com.ioad.honey.common.Shared;

import java.util.ArrayList;
import java.util.Arrays;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Ingredient> ingredients = null;
    private LayoutInflater inflater = null;
    private SharedPreferences preferences;
    private ArrayList<String> selectCode = new ArrayList<>();
    private ArrayList<String> selectName = new ArrayList<>();

    int checkCount = 0;
    boolean isCheck = true;

    public IngredientAdapter(Context mContext, int layout, ArrayList<Ingredient> ingredients) {
        this.mContext = mContext;
        this.layout = layout;
        this.ingredients = ingredients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_ingredient;
        public TextView tv_ingredient_price;
        public CheckBox chk_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ingredient = itemView.findViewById(R.id.tv_ingredient);
            tv_ingredient_price = itemView.findViewById(R.id.tv_ingredient_price);
            chk_select = itemView.findViewById(R.id.chk_select);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        checkCount = holder.getAdapterPosition();
        String name = ingredients.get(position).getiName();
        String capacity = ingredients.get(position).getiCapacity();
        String unit = ingredients.get(position).getiUnit();
        String price = ingredients.get(position).getiPrice();

        String fullName = name + " " + capacity + " " + unit;
        String fullPrice = price + "원";

        holder.tv_ingredient.setText(fullName);
        holder.tv_ingredient_price.setText(fullPrice);

        holder.chk_select.setChecked(ingredients.get(position).isSelected());
        holder.chk_select.setTag(ingredients.get(position));
        holder.chk_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                preferences = mContext.getSharedPreferences("checkBox", Context.MODE_PRIVATE);
                if (isChecked) {
                    String code = ingredients.get(position).getiCode();
                    String name = ingredients.get(position).getiName();

                    selectCode.add(code);
                    selectName.add(name);


//                    Shared.setStringArrayPref(mContext, "SELECT_CODE", selectCode);
//                    Shared.setStringArrayPref(mContext, "SELECT_NAME", selectName);

//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("SELECT_CODE", code);
//                    editor.putString("SELECT_NAME", name);
//                    editor.commit();
                } else {
                    String code = ingredients.get(position).getiCode();
                    String name = ingredients.get(position).getiName();

                    selectCode.remove(code);
                    selectName.remove(name);

//                    Shared.removeStringArrayPref(mContext, "SELECT_CODE");
//                    Shared.removeStringArrayPref(mContext, "SELECT_NAME");
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.remove("SELECT_CODE");
//                    editor.remove("SELECT_NAME");
//                    editor.commit();
                }
                Shared.setStringArrayPref(mContext, "SELECT_CODE", selectCode);
                Shared.setStringArrayPref(mContext, "SELECT_NAME", selectName);
                Log.e("TAG", "adapter " + String.valueOf(selectCode));
                Log.e("TAG", "adapter " + String.valueOf(selectName));
            }
        });


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

}
