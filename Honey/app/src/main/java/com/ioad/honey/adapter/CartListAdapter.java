package com.ioad.honey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ioad.honey.bean.Cart;
import com.ioad.honey.R;
import com.ioad.honey.task.ImageLoadTask;
import com.ioad.honey.task.UpdateNetworkTask;
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
    private ImageLoadTask task;

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
        Button ingredientMinus = view.findViewById(R.id.btn_cart_ingredient_minus); //cart_ingredientMinus_btn
        EditText ingredientCount = view.findViewById(R.id.et_cart_ingredient_count); //et_cartEA
        Button ingredientAdd = view.findViewById(R.id.btn_cart_ingredient_add);//cart_ingredientPlus_btn
        TextView ingredientTotPrice = view.findViewById(R.id.tv_cart_ingredient_total_price); //tv_iTotalPrice
//        Button btnCartAllDel = view.findViewById(R.id.btn_cart_all_del);
//        Button btnCartDel = view.findViewById(R.id.btn_cart_del);

//        getSetImage(carts.get(position).getmImagePath(), cartListImage);
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
                count = Integer.parseInt(String.valueOf(ingredientCount.getText()));
                if (count == 1) {
                    Toast.makeText(mContext, R.string.cart_min, Toast.LENGTH_SHORT).show();
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
                count = Integer.parseInt(String.valueOf(ingredientCount.getText()));
                if (count == 10) {
                    Toast.makeText(mContext, R.string.cart_max, Toast.LENGTH_SHORT).show();
                } else {
                    count += 1;
                    ingredientCount.setText(String.valueOf(count));
                    updateIngredientCount((Integer) ingredientAdd.getTag(), count);
                    cartClickListener.onCartClickAction(true);
                }
            }
        });

        return view;
    }


    private String updateIngredientCount(int position, int count) {
        url = Constant.SERVER_IP + "honey/Cart_ingredientEA_Update_Return.jsp?cartCode=" + position + "&cartEA=" + count;
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

    private void getSetImage(String imageCode, ImageView imageView) {
        url = Constant.SERVER_URL_IMG + imageCode;
        Log.e("TAG", url);
        task = new ImageLoadTask(url, imageView);
        task.execute();
    }

}
