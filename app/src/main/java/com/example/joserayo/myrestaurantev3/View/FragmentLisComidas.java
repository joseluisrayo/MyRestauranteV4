package com.example.joserayo.myrestaurantev3.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.R;

public class FragmentLisComidas  extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comidas, container, false);

        return rootView;
    }
}
