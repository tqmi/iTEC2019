package com.tamas.szasz.zapp.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.CarUpdateThread;
import com.tamas.szasz.zapp.cars.retrofit_threads.cars.DeleteThread;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.main.fragments.model.CustomPopupWindow;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {
    private ArrayList<Car> mDataSet;
    private Context mContext;

    public class CarsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewModel, mTextViewCompany,  mTextViewAutonomy;
        private TextInputEditText mEDTYearPopUp, mEDTLastTechRevisionPopUp, mEDTModelPopUp,
                mEDTCompanyPopUp, mEDTAutonomyPopUp, mEDTBatteryLifePopUp;
        private ImageView mImageViewEdit;
        private CustomPopupWindow mPopWindow;
        private Button mButtonRemoveCar, mButtonEditCar;
        private String carID;
        private HashMap<String, String> details = new HashMap<>(3);
        private int carPosition;
        private Car mCar;
        private String mLastTechRevision;


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
            mCar = car;
            mTextViewModel.setText(car.getModel());
            mTextViewCompany.setText(car.getCompany());
            mTextViewAutonomy.setText(car.getAutonomy() + "");
            carPosition = position;
            carID = car.getId();
            mLastTechRevision = car.getLastTechRevision();
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
                mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y * 3 / 4 + 200, true, parent, v);
            } else {
                mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y / 2 + 200, true, parent, v);
            }
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
            final Calendar calendar = Calendar.getInstance();
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

            mButtonEditCar = inflatedView.findViewById(R.id.popup_edit_BTN_edit_car);
            mButtonEditCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCar.setAutonomy(Integer.parseInt(mEDTAutonomyPopUp.getText().toString()));
                    mCar.setLastTechRevision(mLastTechRevision);
                    mCar.setCompany(mEDTCompanyPopUp.getText().toString());
                    mCar.setModel(mEDTModelPopUp.getText().toString());
                    mCar.setYear(Integer.parseInt(mEDTYearPopUp.getText().toString()));
                    mCar.setBatteryLeft(Integer.parseInt(mEDTBatteryLifePopUp.getText().toString()));
                    CarUpdateThread carUpdateThread = new CarUpdateThread(mCar, mContext);
                    carUpdateThread.run();

                    try {
                        carUpdateThread.join();
                        mPopWindow.dismiss();
                        notifyDataSetChanged();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateET(calendar);
                }

            };
            mEDTLastTechRevisionPopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(mContext, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }

        private void updateET(Calendar calendar) {
            String dateFormat = "yyyy-MM-dd";
            String timeFormat = "HH:mm:ss.SSS";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            SimpleDateFormat sdfTime = new SimpleDateFormat(timeFormat, Locale.US);
            mEDTLastTechRevisionPopUp.setText(sdf.format(calendar.getTime()));
            mLastTechRevision = sdf.format(calendar.getTime() ) + "T" + sdfTime.format(calendar.getTime());
        }

        private void setInformationInPopup(View inflatedView) {
            setUpTextViewsInPop(inflatedView);
            mEDTModelPopUp.setText(mTextViewModel.getText().toString());
            mEDTCompanyPopUp.setText(mTextViewCompany.getText().toString());
            mEDTAutonomyPopUp.setText(mTextViewAutonomy.getText().toString());
            mEDTLastTechRevisionPopUp.setText(details.get("lastTechRevision"));
            mEDTYearPopUp.setText(details.get("year"));
            mEDTBatteryLifePopUp.setText(details.get("batteryLeft"));
        }

        private void setUpTextViewsInPop(View inflatedView) {
            mEDTYearPopUp = inflatedView.findViewById(R.id.popup_edit_TIET_year);
            mEDTBatteryLifePopUp = inflatedView.findViewById(R.id.popup_edit_TIET_batteryLife);
            mEDTLastTechRevisionPopUp= inflatedView.findViewById(R.id.popup_edit_TIET_lastTechRevision);
            mEDTAutonomyPopUp = inflatedView.findViewById(R.id.popup_edit_TIET_autonomy);
            mEDTCompanyPopUp = inflatedView.findViewById(R.id.popup_edit_TIET_company);
            mEDTModelPopUp = inflatedView.findViewById(R.id.popup_edit_TIET_model);
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
