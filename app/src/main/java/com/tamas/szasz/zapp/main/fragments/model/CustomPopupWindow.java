package com.tamas.szasz.zapp.main.fragments.model;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.tamas.szasz.zapp.R;

public class CustomPopupWindow extends PopupWindow {

    private PopupWindow popupWindow;

    public CustomPopupWindow(View view, int width, int height, boolean focusable, ViewGroup parent, View viewPos) {
        this.popupWindow = new PopupWindow(view, width, height, focusable);
        setPopupProps(parent, viewPos);
    }

    public CustomPopupWindow(View view, int width, int height, boolean focusable, Context context, View viewPos) {
        this.popupWindow = new PopupWindow(view, width, height, focusable);
        setPopupProps(context, viewPos);
    }

    private void setPopupProps(Context context, View viewPos) {
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg_appointment_options));
        // make it focusable to show the keyboard to enter in `EditText`
        popupWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // show the popup at bottom of the screen and set some margin at bottom ie,
        popupWindow.showAtLocation(viewPos, Gravity.BOTTOM, 0, 0);
    }

    private void setPopupProps(ViewGroup parent, View viewPos) {
        popupWindow.setBackgroundDrawable(parent.getResources().getDrawable(R.drawable.bkg_appointment_options));
        // make it focusable to show the keyboard to enter in `EditText`
        popupWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // show the popup at bottom of the screen and set some margin at bottom ie,
        popupWindow.showAtLocation(viewPos, Gravity.BOTTOM, 0, 0);
    }
}
