package com.tamas.szasz.zapp.credentials;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.tamas.szasz.zapp.cars.Car;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class User {

    private ArrayList<Car> cars;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private static User instance;
    private Context context;

    private User(){
        cars = new ArrayList<>();
    }

    public static User getInstance() {
        if(instance == null)
            instance = new User();
        return instance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public ArrayList<Car> getCars(){
        return cars;
    }

    public Car getCarAt(int i){
        return cars.get(i);
    }

    public void setToken(String token) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor _userEditor = sharedPreferences.edit();
        _userEditor.putString("TOKEN", token);
        _userEditor.apply();
        this.token = token;
        Log.d("TOKEN","savingToken");
        Log.d("TOKEN",token);
    }

    public void addCar(Car car){

        if (cars.contains(car))
            return;

        cars.add(car);

    }

    public void deleteToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor _userEditor = sharedPreferences.edit();
        _userEditor.remove("TOKEN");
        _userEditor.apply();

        cars.clear();

    }

    public boolean deleteCar(String id) {
        int position = findCarByID(id);
        if(position!= -1) {
            cars.remove(position);
            return true;
        } else {
            return false;
        }
    }

    private int findCarByID(String id) {
        int counter = 0;
        for(Car car: cars) {
            if(car.getId().equals(id)) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

}
