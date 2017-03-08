package com.bk120.cinematicket.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk120.cinematicket.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class YingYuanFragment extends Fragment {


    public YingYuanFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_ying_yuan, container, false);
        ButterKnife.bind(rootView);
        return rootView;
    }

}
