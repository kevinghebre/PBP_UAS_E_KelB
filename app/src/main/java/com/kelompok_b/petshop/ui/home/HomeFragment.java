package com.kelompok_b.petshop.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.findUs;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button btn_findUs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        btn_findUs = root.findViewById(R.id.btn_findUs);
        btn_findUs.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

                view.getContext().startActivity(new Intent(view.getContext(), findUs.class));

            }

        });

        ImageSlider imageSlider = root.findViewById(R.id.image_slider); // init imageSlider

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://s4.bukalapak.com/uploads/content_attachment/46f38cf330e8d762bb8767c5/w-744/02_Jenis_Ras_Anjing.jpg","baby Owl",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://dogtime.com/assets/uploads/2017/12/shih-tzu-dog-names-1.jpg","Baby El",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://www.petfoodindonesia.com/wp-content/uploads/2018/11/telingalipatscottishfold.jpg","Baby Yeb",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://static.nationalgeographic.co.uk/files/styles/image_3200/public/cat-collars-28306_cbm9631.jpg?w=1600&h=900","Baby Bre",ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        return root;
    }
}