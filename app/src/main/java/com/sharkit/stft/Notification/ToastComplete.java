package com.sharkit.stft.Notification;

import android.content.Context;
import android.widget.Toast;

public class ToastComplete extends Exception{
    public ToastComplete(Context context, String message) {
        Toast.makeText( context, message, Toast.LENGTH_SHORT).show();
    }
}
