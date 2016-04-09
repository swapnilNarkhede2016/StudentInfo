package com.accion.assigment.studentinfo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by swapnil on 9/4/16.
 */
public class Utils {
    /**
     * Converts android DIP/DP value to actual pixels for the current device density
     *
     * @param context
     * @param dp
     * @return
     */
    public static float dp2Px(Context context, float dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    /**
     * check connectivity of device
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Shows a progress Dialog
     *
     * @param c
     * @param msg
     */
    public static ProgressDialog progress(Context c, String msg) {
        ProgressDialog d = new ProgressDialog(c);
        d.setMessage(msg);
        d.setIndeterminate(true);
        d.setCanceledOnTouchOutside(false);
        d.show();
        return d;
    }
}
