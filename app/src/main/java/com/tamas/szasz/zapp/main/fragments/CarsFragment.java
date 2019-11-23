package com.tamas.szasz.zapp.main.fragments;

import android.app.DatePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.SettingsActivity;
import com.tamas.szasz.zapp.adapter.CarsAdapter;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.AddThread;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.main.fragments.model.CustomPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CarsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Car> mDataSet;
    private CustomPopupWindow mPopWindow;
    private TextInputEditText mTextInputEditTextDate;
    private final Calendar calendar = Calendar.getInstance();
    private String mLastTechRevision;
    private TextInputEditText mTIETModel, mTIETCompany, mTIETAutonomy, mTIETYear, mTIETBatteryLife, mTIETLastTechRevision;
    private TextInputLayout mTILModel, mTILCompany, mTILAutonomy, mTILBatteryLife, mTILYear, mTILLastTechRevision;

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

        mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y - 300 , true, getContext(), view);
        mPopWindow.setLocation(view, Gravity.BOTTOM, 0, 0);
        setUpEditTexts(_inflatedView);
        setUpLLayouts(_inflatedView);
        setUpPopupButtons(_inflatedView);
    }

    private void setUpLLayouts(View inflatedView) {
        mTILModel = inflatedView.findViewById(R.id.frag_cars_add_TIL_model);
        mTILCompany = inflatedView.findViewById(R.id.frag_cars_add_TIL_company);
        mTILAutonomy = inflatedView.findViewById(R.id.frag_cars_add_TIL_autonomy);
        mTILBatteryLife = inflatedView.findViewById(R.id.frag_cars_add_TIL_batteryLife);
        mTILYear = inflatedView.findViewById(R.id.frag_cars_add_TIL_year);
        mTILLastTechRevision = inflatedView.findViewById(R.id.frag_cars_add_TIL_lastTechRevision);
    }

    private void setUpEditTexts(View inflatedView) {
        mTIETModel = inflatedView.findViewById(R.id.frag_cars_add_TIET_model);
        mTIETCompany = inflatedView.findViewById(R.id.frag_cars_add_TIET_company);
        mTIETAutonomy = inflatedView.findViewById(R.id.frag_cars_add_TIET_autonomy);
        mTIETBatteryLife = inflatedView.findViewById(R.id.frag_cars_add_TIET_batteryLife);
        mTIETYear = inflatedView.findViewById(R.id.frag_cars_add_TIET_year);
        mTIETLastTechRevision = inflatedView.findViewById(R.id.frag_cars_add_TIET_lastTechRevision);


    }

    private void setUpPopupButtons(View inflatedView) {

        Button addCar = inflatedView.findViewById(R.id.frag_cars_add_BTN_add_car);
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(carInfoCorrect()) {
                    AddThread addThread = new AddThread(mTIETModel.getText().toString(),
                            mTIETCompany.getText().toString(),
                            Integer.parseInt(mTIETYear.getText().toString()),
                            Integer.parseInt(mTIETAutonomy.getText().toString()),
                            Integer.parseInt(mTIETBatteryLife.getText().toString()),
                            mLastTechRevision,
                            getContext());
                    addThread.run();
                    try {
                        addThread.join();
                        mPopWindow.dismiss();
                        Toast.makeText(getContext(), "Added new car!", Toast.LENGTH_SHORT).show();
                        mDataSet = User.getInstance().getCars();
                        mAdapter.notifyDataSetChanged();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateET();
            }

        };
        mTextInputEditTextDate = inflatedView.findViewById(R.id.frag_cars_add_TIET_lastTechRevision);
        mTextInputEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private boolean carInfoCorrect() {
        if(mTIETLastTechRevision.getText().toString().equals("")) {
            mTILLastTechRevision.setError("Empty");
            return false;
        }
        mTILLastTechRevision.setErrorEnabled(false);
        if(mTIETYear.getText().toString().equals("")) {
            mTILYear.setError("Empty");
            return false;
        }
        mTILYear.setErrorEnabled(false);
        if(mTIETBatteryLife.getText().toString().equals("")) {
            mTILBatteryLife.setError("Empty");
            return false;
        }
        mTILBatteryLife.setErrorEnabled(false);
        if(mTIETModel.getText().toString().equals("")) {
            mTILModel.setError("Empty");
            return false;
        }
        mTILModel.setErrorEnabled(false);
        if(mTIETAutonomy.getText().toString().equals("")) {
            mTILAutonomy.setError("Empty");
            return false;
        }
        mTILAutonomy.setErrorEnabled(false);
        if(mTIETCompany.getText().toString().equals("")) {
            mTILCompany.setError("Empty");
            return false;
        }
        mTILCompany.setErrorEnabled(false);
        return true;
    }


    private void updateET() {
        String dateFormat = "yyyy-MM-dd";
        String timeFormat = "HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        SimpleDateFormat sdfTime = new SimpleDateFormat(timeFormat, Locale.US);
        mTextInputEditTextDate.setText(sdf.format(calendar.getTime()));
        mLastTechRevision = sdf.format(calendar.getTime() ) + "T" + sdfTime.format(calendar.getTime());
    }

}
