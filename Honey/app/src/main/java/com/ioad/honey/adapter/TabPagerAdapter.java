package com.ioad.honey.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ioad.honey.fragment.DashboardFragment;
import com.ioad.honey.fragment.MenuFragment;

public class TabPagerAdapter extends FragmentStateAdapter {
    private Fragment[] fragments = new Fragment[] {
            new DashboardFragment(),
            new MenuFragment("korean.png", "menu_select_koreanfood.jsp"),
            new MenuFragment("schoolfood.png", "menu_select_schoolfood.jsp"),
            new MenuFragment("soup.png","menu_select_soup.jsp"),
            new MenuFragment("instant.png","menu_select_instant.jsp"),
            new MenuFragment("dessert.png","menu_select_dessert.jsp"),
            new MenuFragment("vegan.png","menu_select_vegan.jsp")
    };

    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}
