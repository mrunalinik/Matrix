package com.example.matrix.matrix;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by mrunalinikoritala on 9/27/2017.
 */

public class CommonUtility {

    public static void showErrorAlert(final Context CTX, String MSG) {
        AlertDialog.Builder alert = new AlertDialog.Builder(CTX);
        alert.setTitle("");
        alert.setCancelable(false);
        alert.setMessage(MSG);
        alert.setPositiveButton(
                CTX.getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog1, int which) {
                        dialog1.dismiss();


                    }
                });
        alert.show();
    }

}
