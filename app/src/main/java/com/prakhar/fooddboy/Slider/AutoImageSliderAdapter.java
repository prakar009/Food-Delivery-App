package com.prakhar.fooddboy.Slider;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class AutoImageSliderAdapter extends FragmentStateAdapter {
    private List<Integer> imageList;

    public AutoImageSliderAdapter(Context context, List<Integer> imageList) {
        super((FragmentActivity) context);
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ImageFragment.newInstance(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
