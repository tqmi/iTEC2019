package com.tamas.szasz.zapp.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.telecom.Call;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.credentials.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationRevision {
    private static final String NOTIFICATION_CHANNEL_ID = "channel_alert";
    private NotificationManager mNotificationManager;
    private Context mContext;
    private static final long ONE_YEAR = 31556952000L;
    private Calendar currentCalendar = Calendar.getInstance();

    public NotificationRevision(Context context) {
        mContext = context;
    }

    public void checkIfOutOfService() {
        ArrayList<Car> cars = User.getInstance().getCars();
        for(Car car: cars) {
            if(datePassed(car.getLastTechRevision())) {
                startNotificationForDifferentSDK(mContext, car.getModel());
                break;
            }
        }
    }

    private Calendar getCalendar(String dateS) {
        String dateFormat = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        try {
            Date date = sdf.parse(dateS);

            cal.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cal;
    }

    private boolean datePassed(String lastTechRevision) {

        Calendar calendar = getCalendar(lastTechRevision);
        Log.d("RES", calendar.getTimeInMillis() + "; " + currentCalendar.getTimeInMillis());
        return calendar.getTimeInMillis() + ONE_YEAR < currentCalendar.getTimeInMillis();
    }

    private void startNotificationForDifferentSDK(Context context, String model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager _manager = context.getSystemService(NotificationManager.class);
            NotificationChannel _channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Zapp",
                    NotificationManager.IMPORTANCE_HIGH);
            _channel.setDescription("Check service for you cars");
            _channel.enableVibration(true);
            _channel.setLightColor(Color.GREEN);
            _channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            _manager.createNotificationChannel(_channel);
            sendNotification(context, model);
        } else {
            sendNotification(context, model);
        }
    }
    private void sendNotification(Context context, String model) {
        NotificationCompat.Builder _builder = buildNotification(context, model);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1000, _builder.build());
    }

    private NotificationCompat.Builder buildNotification(Context context, String model) {
        NotificationCompat.Builder _builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Zapp")
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentText(context.getString(R.string.alert_out_of_verification) + model)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setTicker("Zapp")
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
            _builder.setPriority(android.app.Notification.PRIORITY_HIGH);
        }
        return _builder;
    }
}
