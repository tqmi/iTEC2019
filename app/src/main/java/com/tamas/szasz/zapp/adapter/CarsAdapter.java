package com.tamas.szasz.zapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.cars.Car;

import java.util.ArrayList;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {
    private ArrayList<Car> mDataSet;

    public static class CarsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewModel;
        private TextView mTextViewCompany;
        private TextView mTextViewYear;
        private TextView mTextViewAutonomy;
        private TextView mTextViewBatteryLeft;
        private TextView mTextViewLastTechRevision;
//        private ImageView mImageViewEdit;


        public CarsViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            mTextViewModel = itemView.findViewById(R.id.car_item_TV_model);
            mTextViewCompany= itemView.findViewById(R.id.car_item_TV_company);
            mTextViewYear = itemView.findViewById(R.id.car_item_TV_year);
            mTextViewBatteryLeft = itemView.findViewById(R.id.car_item_TV_batteryLeft);
            mTextViewAutonomy = itemView.findViewById(R.id.car_item_TV_autonomy);
            mTextViewLastTechRevision = itemView.findViewById(R.id.car_item_TV_lastTechRevision);

        }
        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void updateInfo(Car car) {
            mTextViewModel.setText(car.getModel());
            mTextViewCompany.setText(car.getCompany());
            mTextViewYear.setText(car.getYear() + "");
            mTextViewBatteryLeft.setText(car.getBatteryLeft() + " %");
            mTextViewAutonomy.setText(car.getAutonomy() + "");
            mTextViewLastTechRevision.setText(car.getLastTechRevision());
        }
    }

    public CarsAdapter(ArrayList<Car> myDataset) {
        mDataSet = myDataset;
    }

    @Override
    public CarsAdapter.CarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout _viewGroup = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);

        CarsViewHolder _vh = new CarsViewHolder(_viewGroup, parent);
        return _vh;

    }

    @Override
    public void onBindViewHolder(CarsViewHolder holder, int position) {
        Car _car = mDataSet.get(position);
        holder.updateInfo(_car);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
