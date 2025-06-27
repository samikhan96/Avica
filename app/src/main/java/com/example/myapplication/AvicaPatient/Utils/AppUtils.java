package com.example.myapplication.AvicaPatient.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;


import com.example.myapplication.AvicaPatient.Avica;
import com.example.myapplication.AvicaPatient.BuildConfig;
import com.example.myapplication.AvicaPatient.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppUtils {
    public static ProgressDialog progressDialog;

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToDDMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "E";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToTime(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String[] getAppVersions(Context activity) {
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            String versionName = pInfo.versionName;
            int verCode = pInfo.versionCode;

            return new String[]{String.valueOf(verCode), versionName};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"", ""};
        }
    }

    public static void installApk(Activity activitySplash, String downloadedPath) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File apkFile = new File(downloadedPath);
            Uri apkUri = FileProvider.getUriForFile(activitySplash, BuildConfig.APPLICATION_ID + ".provider", apkFile);
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activitySplash.startActivityForResult(intent, 1001);
        } else {
            Uri uri = Uri.parse("file://" + downloadedPath);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            install.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            activitySplash.startActivity(install);
        }
    }

    public static void showProgressDialog(Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Avica"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
    }

    public static void dismisProgressDialog(Context context) {
        progressDialog.dismiss();
    }

    public static void Toast(String Message, boolean Long) {
        Toast toast = Toast.makeText(Avica.getContext(), "  " + Message + "  ", Toast.LENGTH_LONG);
        TextView textView = (((TextView) ((LinearLayout) toast.getView()).getChildAt(0)));
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(Color.BLACK);
        toast.getView().setBackgroundColor(Avica.getContext().getResources().getColor(R.color.white));
        toast.show();
    }

    public static void Toast(String Message) {
        Toast.makeText(Avica.getContext(), "  " + String.valueOf(Message) + "  ", Toast.LENGTH_SHORT).show();
    }

    // Static method to convert date format and set text to TextView
    public static void setFormattedDate(String isoDate, TextView textView) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = inputFormat.parse(isoDate);
            String dobFormatted = outputFormat.format(date);
            textView.setText(dobFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
            textView.setText("Invalid date");
        }

    }

    public static void setFormattedTime(String isoDate, TextView textView) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure correct parsing of Zulu time

        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault()); // Only time
        try {
            Date date = inputFormat.parse(isoDate);
            String timeFormatted = outputFormat.format(date);
            textView.setText(timeFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
            textView.setText("Invalid time");
        }
    }

}