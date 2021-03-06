package com.barbar.sarvalue;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
    public volatile MainActivity pUbiSarValueActivity = null;
    public volatile Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pUbiSarValueActivity = this;
        mHandler = new Handler();
        Thread workthread = new Thread(new OnCreateThread());
        workthread.start();
    }

    class OnCreateThread implements Runnable {
        public void run() {
            boolean exceptiono = false;
            mHandler.post(new Runnable() {
                public void run() {
                    Toast.makeText(pUbiSarValueActivity, "Please wait ..", Toast.LENGTH_SHORT).show();
                }
            });

            File file = new File("/data/system/Datawind_sar_Certificate.pdf");

            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    exceptiono = true;
                    Log.e(this.getClass().getName(), "Exception", e);
                }
            } else {
                exceptiono = true;
                Log.e(this.getClass().getName(), " -- -- file does not exist");
            }

            if (exceptiono) {
                mHandler.post(new Runnable() {
                    public void run() {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(pUbiSarValueActivity);
                        alert.setTitle("SAR Info");
                        alert.setMessage(
                                        "Telecommunications standards prevent the sale\n" +
                                        "of mobile phones that exceed a maximum exposure\n" +
                                        "level known as SAR of 1.6W/kg.\n\n" +
                                        "During testing, the maximum SAR recorded\n" +
                                        "for this model is below SAR Values of:\n" +
                                        "GSM900: Head 0.450 Watt/kg; Body 0.874 Watt/kg\n" +
                                        "GSM1800: Head 0.337 Watt/kg; Body 0.523 Watt/kg\n" +
                                        "W-CDMA2100: Head 0.906 Watt/kg; Body 0.446 Watt/kg\n\n")
                                        .setCancelable(false);

                        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                                pUbiSarValueActivity.finish();
                            }
                        });

                        alert.show();
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    public void run() {
                        pUbiSarValueActivity.finish();
                    }
                });
            }

        }
    }

}
