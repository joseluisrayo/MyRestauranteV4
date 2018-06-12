package com.example.joserayo.myrestaurantev3.View;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;

import com.example.joserayo.myrestaurantev3.R;

public class Extras extends  Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.extras, container, false);

        return rootView;
    }
}
