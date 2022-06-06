package com.ioad.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ioad.honey.Adapter.CartListAdapter;
import com.ioad.honey.Bean.Cart;
import com.ioad.honey.R;
import com.ioad.honey.Task.DeleteNetworkTask;
import com.ioad.honey.Task.SelectNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Listener.CartClickListener;
import com.ioad.honey.common.Shared;
import com.ioad.honey.common.Swife.SwipeDismissListViewTouchListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartClickListener {

    LinearLayout layoutCartList, layoutCartEmpty;
    ProgressBar pbCart;
    TextView tvCartDelivery, tvPriceTot, tvDeliveryTip;
    ImageButton ibDeliveryTip;
    Button btnCartAllDel, btnDelivery, btnGoBack;
    ListView cartList;

    String deleteResult;
    String url;
    String userId;
    ArrayList<Cart> carts;
    CartListAdapter adapter;
    DecimalFormat priceFormat = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        userId = Shared.getStringPref(CartActivity.this, "USER_ID");

        layoutCartList = findViewById(R.id.ll_cart_list);
        layoutCartEmpty = findViewById(R.id.ll_cart_empty);
        pbCart = findViewById(R.id.pb_cart);
        tvCartDelivery = findViewById(R.id.tv_cart_delivery);
        tvPriceTot = findViewById(R.id.tv_price_total);
        tvDeliveryTip = findViewById(R.id.tv_delivery_tip);
        ibDeliveryTip = findViewById(R.id.ib_cart_delivery_tip);
        btnCartAllDel = findViewById(R.id.btn_cart_all_del);
        btnDelivery = findViewById(R.id.btn_delivery);
        btnGoBack = findViewById(R.id.btn_go_back);
        cartList = findViewById(R.id.lv_cart_list);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(cartList,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    deleteResult = connectDeleteData(carts.get(position).getCartCode());
                                    carts.remove(carts.get(position));
                                    if(deleteResult.equals("1")){
                                        Snackbar.make(listView, "장바구니에서 삭제했습니다.", Snackbar.LENGTH_SHORT).show();
                                    }else {
                                        Snackbar.make(listView, "장바구니에서 삭제 실패했습니다.", Snackbar.LENGTH_SHORT).show();
                                    }
                                    connectGetData();
                                }
                                adapter.notifyDataSetChanged();
                            }

                        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private String connectDeleteData(int cartCode){
        url = Constant.SERVER_IP + "Cart_Deletedate_Update_Return.jsp?" + "cartCode=" + cartCode;
        String result = null;
        try {
            DeleteNetworkTask networkTask = new DeleteNetworkTask(CartActivity.this, url, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //잘끝났으면 1 아니면 에러
    }



    private void connectGetData(){

        url = Constant.SERVER_IP + "Cart_All_List.jsp?cId=" + userId;

        try{
            SelectNetworkTask networkTask = new SelectNetworkTask(CartActivity.this, url, "select", "carts_info");
            Object obj = networkTask.execute().get();
            carts = (ArrayList<Cart>) obj;

            if (carts.isEmpty() == true){
                layoutCartEmpty.setVisibility(View.VISIBLE);
                layoutCartList.setVisibility(View.INVISIBLE);
            }else {
                layoutCartEmpty.setVisibility(View.INVISIBLE);
                layoutCartList.setVisibility(View.VISIBLE);
                adapter = new CartListAdapter(CartActivity.this, R.layout.cart_list_layout, carts, this);
                cartList.setAdapter(adapter);

                tvPriceTot.setText(priceFormat.format(totalPrice()) + "원");
                pbCart.setProgress(totalPrice());

                if (totalPrice() < 10000){
                    tvCartDelivery.setText(priceFormat.format(10000-totalPrice())+"원 더 담아주세용");
                    tvCartDelivery.setTextColor(0xFFFF0000);
                    btnDelivery.setEnabled(false);
                    btnDelivery.setText("최소 주문 금액을 채워주세요");
                    tvDeliveryTip.setText("3,000원");
                }else if(totalPrice() >= 10000 && totalPrice() < 30000){
                    tvCartDelivery.setText(priceFormat.format(30000 - totalPrice()) + "원 더 담으면 무료배송");
                    tvCartDelivery.setTextColor(0xFF0000FF);
                    btnDelivery.setEnabled(true);
                    btnDelivery.setText(totalCount() + "개 " + priceFormat.format(totalPrice() + 3000) + "원 주문하러 가기");
                    tvDeliveryTip.setText("3,000원");
                }else {
                    tvCartDelivery.setText("무료배송");
                    tvCartDelivery.setTextColor(0xFF000000);
                    btnDelivery.setEnabled(true);
                    btnDelivery.setText(totalCount() + "개 " + priceFormat.format(totalPrice()) + "원 주문하러 가기");
                    tvDeliveryTip.setText("무료배송");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCartClickAction(boolean isSelected) {
        if (isSelected) {
            connectGetData();
        }
    }

    private int totalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < carts.size(); i++) {
            totalPrice += carts.get(i).getiPrice() * carts.get(i).getCartEA();
        }
        return totalPrice;
    }

    private int totalCount() {
        int totalCount = 0;
        for (int i = 0; i < carts.size(); i++) {
            totalCount += carts.get(i).getCartEA();
        }
        return totalCount;
    }
}