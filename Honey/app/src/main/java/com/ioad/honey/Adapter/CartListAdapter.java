package com.ioad.honey.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ioad.honey.Bean.Cart;
import com.ioad.honey.R;
import com.ioad.honey.Task.UpdateNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Listener.CartClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {

    private Context mContext;
    private int layout = 0;
    private ArrayList<Cart> carts;
    private LayoutInflater inflater;
    private CartClickListener cartClickListener;
    DecimalFormat priceFormat = new DecimalFormat("###,###");
    int count;
    String url;

    public CartListAdapter(Context mContext, int layout, ArrayList<Cart> carts, CartClickListener cartClickListener) {
        this.mContext = mContext;
        this.layout = layout;
        this.carts = carts;
        this.cartClickListener = cartClickListener;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int i) {
        return carts.get(i).getCartCode();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflater.inflate(this.layout, viewGroup, false);

        TextView cartListTitle = view.findViewById(R.id.tv_cart_list_title); //tv_mName
        ImageView cartListImage = view.findViewById(R.id.iv_cart_list_image); //image
        TextView ingredientName = view.findViewById(R.id.tv_cart_ingredient_name); //tv_iFullName
        TextView ingredientPrice = view.findViewById(R.id.tv_cart_ingredient_price); //tv_iPrice
        ImageButton ingredientMinus = view.findViewById(R.id.ib_cart_ingredient_minus); //cart_ingredientMinus_btn
        EditText ingredientCount = view.findViewById(R.id.et_cart_ingredient_count); //et_cartEA
        ImageButton ingredientAdd = view.findViewById(R.id.ib_cart_ingredient_add);//cart_ingredientPlus_btn
        TextView ingredientTotPrice = view.findViewById(R.id.tv_cart_ingredient_total_price); //tv_iTotalPrice

        cartListTitle.setText(carts.get(position).getmName());
        ingredientName.setText(carts.get(position).getiName() + carts.get(position).getiCapacity() + carts.get(position).getiUnit());
        ingredientPrice.setText(carts.get(position).getiPrice() + "원");
        ingredientTotPrice.setText(priceFormat.format(carts.get(position).getiPrice() * carts.get(position).getCartEA()) + "원");
        ingredientCount.setText(Integer.toString(carts.get(position).getCartEA()));


        ingredientMinus.setTag(carts.get(position).getCartCode());
        ingredientAdd.setTag(carts.get(position).getCartCode());

        ingredientMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(String.valueOf(ingredientCount));
                if (count == 1) {
                    Toast.makeText(mContext, "최소 구매 수량입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    count -= 1;
                    ingredientCount.setText(String.valueOf(count));
                    updateIngredientCount((Integer) ingredientMinus.getTag(), count);
                    cartClickListener.onCartClickAction(true);
                }
            }
        });

        ingredientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(String.valueOf(ingredientCount));
                if (count == 10) {
                    Toast.makeText(mContext, "최대 구매 수량입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    count += 1;
                    ingredientCount.setText(String.valueOf(count));
                    updateIngredientCount((Integer) ingredientAdd.getTag(), count);
                    cartClickListener.onCartClickAction(true);
                }
            }
        });

        return null;
    }


    private String updateIngredientCount(int position, int count) {
        url = Constant.SERVER_IP + "Cart_ingredientEA_Update_Return.jsp?cartCode=" + position + "&cartEA=" + count;
        String result = null;
        try {
            UpdateNetworkTask task = new UpdateNetworkTask(mContext, url, "update");
            Object obj = task.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}