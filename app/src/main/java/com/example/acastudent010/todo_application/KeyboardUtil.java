package com.example.acastudent010.todo_application;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public final class KeyboardUtil {
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view.getWindowToken() != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
