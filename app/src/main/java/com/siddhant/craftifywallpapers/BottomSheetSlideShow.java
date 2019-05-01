package com.siddhant.craftifywallpapers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;




public class BottomSheetSlideShow extends BottomSheetDialogFragment {
   private RadioButton radioButton5hr, radioButton3hr, radioButton2hr, radioButton1hr;
   private RadioGroup radioGroup;
    int durationInHour;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_show_bottom_sheet, container, false);
        radioButton1hr = view.findViewById(R.id.radioButton1hr);
        radioButton2hr = view.findViewById(R.id.radioButton2hr);
        radioButton3hr = view.findViewById(R.id.radioButton3hr);
        radioButton5hr = view.findViewById(R.id.radioButton5hr);
        radioGroup = view.findViewById(R.id.radioGroupDuration);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Intent i = new Intent(getActivity().getApplicationContext(),WallpaperChangerService.class);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == radioButton1hr.getId()) {
                    durationInHour = 1;
                    startSlideShowService(i,durationInHour);
                    dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Wallpaper will change after an interval of " + durationInHour + " hour(s)", Toast.LENGTH_LONG).show();

                } else if (checkedId == radioButton2hr.getId()) {
                    durationInHour = 2;
                    startSlideShowService(i,durationInHour);
                    dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Wallpaper will change after an interval of " + durationInHour + " hour(s)", Toast.LENGTH_LONG).show();

                } else if (checkedId == radioButton3hr.getId()) {
                    durationInHour = 3;
                    startSlideShowService(i,durationInHour);
                    dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Wallpaper will change after an interval of " + durationInHour + " hour(s)", Toast.LENGTH_LONG).show();

                } else if (checkedId == radioButton5hr.getId()) {
                    durationInHour = 5;
                    startSlideShowService(i,durationInHour);
                    dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Wallpaper will change after an interval of " + durationInHour + " hour(s)", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void startSlideShowService(Intent i,int interval) {
        i.putExtra("interval",interval);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(i);
            Toast.makeText(getActivity().getApplicationContext(),"service started",Toast.LENGTH_SHORT).show();
        } else {
            getActivity().startService(i);

            Toast.makeText(getActivity().getApplicationContext(),"service started",Toast.LENGTH_SHORT).show();
        }
    }
}

