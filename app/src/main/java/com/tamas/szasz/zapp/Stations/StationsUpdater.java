package com.tamas.szasz.zapp.Stations;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.NavigationActivity;
import com.tamas.szasz.zapp.Stations.retrofit_threads.stations.StationsListThread;

public class StationsUpdater extends Thread {

    private boolean running;
    private static StationsUpdater instance;
    private Context context;

    private StationsUpdater() {
        super();
    }

    public static StationsUpdater getInstance(){
        if(instance == null)
            instance = new StationsUpdater();
        return instance;
    }

    @Override
    public void run() {

        running = true;

        while(running) {

            StationsListThread stationsListThread = new StationsListThread();
            stationsListThread.run();

            Log.d("Running", "Run");

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    public void closeThread(){
        running = false;
        interrupt();
        instance = null;
    }

    public void setContext(Context context){
        this.context = context;
    }

}
