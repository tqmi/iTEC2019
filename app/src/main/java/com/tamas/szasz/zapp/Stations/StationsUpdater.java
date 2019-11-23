package com.tamas.szasz.zapp.Stations;

import android.content.Context;

import com.tamas.szasz.zapp.NavigationActivity;
import com.tamas.szasz.zapp.Stations.retrofit_threads.stations.StationsListThread;

public class StationsUpdater extends Thread {

    private boolean running;
    private static StationsUpdater instance;
    private NavigationActivity context;

    private StationsUpdater() {
        super();
    }

    public static StationsUpdater getInstance(){
        if(instance == null)
            instance = new StationsUpdater();
        return instance;
    }

    public synchronized void start(NavigationActivity context) {
        this.context = context;

        StationsListThread stationsListThread = new StationsListThread();
        stationsListThread.run();

        try{
            stationsListThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        running = true;

        super.start();
    }

    @Override
    public void run() {

        running = true;

        while(running) {

            StationsListThread stationsListThread = new StationsListThread();
            stationsListThread.run();


            try {
                stationsListThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            context.refreshMap();

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
    }

}
