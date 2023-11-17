package com.prakhar.fooddboy.Slider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prakhar.fooddboy.R;

public class ImageFragment extends Fragment {
    private static final String ARG_IMAGE_RESOURCE = "image _resource";

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(int imageResource) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RESOURCE, imageResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.imageView);
        Bundle args = getArguments();
        if (args != null) {
            int imageResource = args.getInt(ARG_IMAGE_RESOURCE);
            imageView.setImageResource(imageResource);
        }
    }
}
