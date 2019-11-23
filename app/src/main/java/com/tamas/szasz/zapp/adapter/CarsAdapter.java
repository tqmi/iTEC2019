package com.tamas.szasz.zapp.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.DeleteThread;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.main.fragments.model.CustomPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {
    private ArrayList<Car> mDataSet;
    private Context mContext;

    public class CarsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewModel;
        private TextView mTextViewCompany;
        private TextView mTextViewYear;
        private TextView mTextViewAutonomy;
        private TextView mTextViewBatteryLeft;
        private TextView mTextViewLastTechRevision;
        private TextView mTextViewModelPopUp;
        private TextView mTextViewCompanyPopUp;
        private TextView mTextViewAutonomyPopUp;
        private ImageView mImageViewEdit;
        private CustomPopupWindow mPopWindow;
        private Button mButtonRemoveCar;
        private String carID;
        private HashMap<String, String> details = new HashMap<>(3);
        private int carPosition;


        public CarsViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            mTextViewModel = itemView.findViewById(R.id.car_item_TV_model);
            mTextViewCompany= itemView.findViewById(R.id.car_item_TV_company);
            mTextViewAutonomy = itemView.findViewById(R.id.car_item_TV_autonomy);
            setImageClick( parent);

        }
        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void updateInfo(Car car, int position) {
            mTextViewModel.setText(car.getModel());
            mTextViewCompany.setText(car.getCompany());
            mTextViewAutonomy.setText(car.getAutonomy() + "");
            carPosition = position;
            carID = car.getId();
            setDetails(car);
        }

        private void setDetails(Car car) {
            details.put("year", car.getYear() + "");
            details.put("batteryLeft", car.getBatteryLeft() + "");
            details.put("lastTechRevision", getTechRevisionText(car.getLastTechRevision()));
        }

        private void setImageClick( final ViewGroup parent) {
            mImageViewEdit = itemView.findViewById(R.id.car_item_IV_options);

            mImageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptionsPopup(v, parent);
                }
            });
        }

        private void showOptionsPopup(View v, @NonNull ViewGroup parent) {
            // inflate the custom popup layout
            final View _inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_edit_car, null, false);
            // get device size
            Display _display = parent.getDisplay();
            final Point _size = new Point();
            _display.getSize(_size);

            // set height depends on the device size
            if (_size.y < 1350) {
                mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y , true, parent, v);
            } else if (_size.y > 1350 && _size.y < 1900) {
                mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y * 3 / 6, true, parent, v);
            } else {
                mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y / 3, true, parent, v);
            }
//            // set a background drawable with rounders corners
//            mPopWindow.setBackgroundDrawable(parent.getResources().getDrawable(R.drawable.bkg_appointment_options));
//            // make it focusable to show the keyboard to enter in `EditText`
//            mPopWindow.setFocusable(true);
//            // make it outside touchable to dismiss the popup window
//            mPopWindow.setOutsideTouchable(true);
//            mPopWindow.setAnimationStyle(R.style.PopupAnimation);
//            // show the popup at bottom of the screen and set some margin at bottom ie,
//            mPopWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            mPopWindow.setLocation(v, Gravity.BOTTOM, 0 , 0);

            setInformationInPopup(_inflatedView);
            setPopUpButtonsListeners(_inflatedView);

            setCloseImage(_inflatedView);
        }

        private void setCloseImage(View inflatedView) {
            ImageView _closeImg = inflatedView.findViewById(R.id.popup_edit_IV_Close);
            _closeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopWindow.dismiss();
                }
            });
        }

        private void setPopUpButtonsListeners(View inflatedView) {
            mButtonRemoveCar = inflatedView.findViewById(R.id.popup_edit_BTN_remove_car);
            mButtonRemoveCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteThread _deleteThread = new DeleteThread(carID, mContext);
                    _deleteThread.run();
                    try {
                        _deleteThread.join();
                        mDataSet.remove(carPosition);
                        mPopWindow.dismiss();
                        notifyDataSetChanged();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void setInformationInPopup(View inflatedView) {
            setUpTextViewsInPop(inflatedView);
            mTextViewModelPopUp.setText(mTextViewModel.getText().toString());
            mTextViewCompanyPopUp.setText(mTextViewCompany.getText().toString());
            mTextViewAutonomyPopUp.setText(mTextViewAutonomy.getText().toString());
            mTextViewLastTechRevision.setText(details.get("lastTechRevision"));
            mTextViewYear.setText(details.get("year"));
            mTextViewBatteryLeft.setText(details.get("batteryLeft"));
        }

        private void setUpTextViewsInPop(View inflatedView) {
            mTextViewYear = inflatedView.findViewById(R.id.popup_edit_TV_year);
            mTextViewBatteryLeft = inflatedView.findViewById(R.id.popup_edit_TV_battery_left);
            mTextViewLastTechRevision = inflatedView.findViewById(R.id.popup_edit_TV_last_tech_revision);
            mTextViewAutonomyPopUp = inflatedView.findViewById(R.id.popup_edit_TV_autonomy);
            mTextViewCompanyPopUp = inflatedView.findViewById(R.id.popup_edit_TV_company);
            mTextViewModelPopUp = inflatedView.findViewById(R.id.popup_edit_TV_model);
        }


        private String getTechRevisionText(String lastTechRevision) {
            String[] dateSplitted = lastTechRevision.split("T");
            String[] hourSplitted = dateSplitted[1].split(":");
            return dateSplitted[0] + "\n" + hourSplitted[0] + ": " + hourSplitted[1];
        }
    }

    public CarsAdapter(ArrayList<Car> myDataset, Context context) {
        mDataSet = myDataset;
        mContext = context;
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
        holder.updateInfo(_car, position);

    }

    public void setmDataSet(ArrayList<Car> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
