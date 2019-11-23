package com.tamas.szasz.zapp.main.fragments.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.tamas.szasz.zapp.R;

public class CustomPopupWindow extends PopupWindow {


    public CustomPopupWindow(View view, int width, int height, boolean focusable, ViewGroup parent, View viewPos) {
        super(view, width, height, focusable);
        setPopupProps(parent, viewPos);
    }

    public CustomPopupWindow(View view, int width, int height, boolean focusable, Context context, View viewPos) {
        super(view, width, height, focusable);
        setPopupProps(context, viewPos);
    }

    private void setPopupProps(Context context, View viewPos) {
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg_popwindow));
        // make it focusable to show the keyboard to enter in `EditText`
        this.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.PopupAnimation);

    }

    private void setPopupProps(ViewGroup parent, View viewPos) {
        this.setBackgroundDrawable(parent.getResources().getDrawable(R.drawable.bkg_popwindow));
        // make it focusable to show the keyboard to enter in `EditText`
        this.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
    }

    public void setLocation(View view, int gravity, int x, int y) {
        this.showAtLocation(view, gravity, x, y);
    }
}
