package com.mattcasanova.weatherreport.Utility;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Alerts {
    /**
     * Create a default action for basic alerts where the user has no options
     */
    private static DialogInterface.OnClickListener DEFAULT_ACTION = new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
            /* Do Nothing */
        }
    };

    /**
     *  Basic error alert to notify the user of some problem.
     *
     * @param title The title of the alert
     * @param msg The message to share with the user
     * @param buttonTitle The title of the button
     * @param context The view context to display on
     * @return A newly created alert dialog.
     */
    public static AlertDialog NoOptionAlert(String title, String msg, String buttonTitle, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(buttonTitle, DEFAULT_ACTION);
        return builder.show();

    }
}
