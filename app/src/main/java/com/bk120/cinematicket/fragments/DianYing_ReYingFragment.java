package com.bk120.cinematicket.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk120.cinematicket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DianYing_ReYingFragment extends Fragment {


    public DianYing_ReYingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("DianYing_ReYingFragment","onCreateView");
        return inflater.inflate(R.layout.fragment_dian_ying__re_ying, container, false);
    }

}
