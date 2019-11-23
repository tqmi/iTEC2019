package com.tamas.szasz.zapp.main.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.SettingsActivity;
import com.tamas.szasz.zapp.adapter.CarsAdapter;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.main.fragments.model.CustomPopupWindow;

import java.util.ArrayList;

public class CarsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Car> mDataSet;
    private PopupWindow mPopWindow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataSet = User.getInstance().getCars();
        ((SettingsActivity) getActivity()).setActionBarTitle("My cars");
        return inflater.inflate(R.layout.fragment_cars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.frag_cars_RV_cars);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CarsAdapter(mDataSet, getContext());
        recyclerView.setAdapter(mAdapter);

        setUpButtonAddListener(view);
    }

    private void setUpButtonAddListener(View view) {
        Button buttonAdd = view.findViewById(R.id.frag_cars_BTN_add_car);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindows(view);
            }
        });
    }

    private void showPopupWindows(View view) {
        final View _inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.popup_add_car, null, false);
        // get device size
        Display _display = getView().getDisplay();
        final Point _size = new Point();
        _display.getSize(_size);

        // set height depends on the device size
        if (_size.y < 1350) {
            mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y , true, getContext(), view);
        } else if (_size.y > 1350 && _size.y < 1900) {
            mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y * 3 / 6, true, getContext(), view);
        } else {
            mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y / 3, true, getContext(), view);
        }
    }

}
