package com.barbar.sarvalue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SarValReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context contexto, Intent intento) {
        if ("android.provider.Telephony.SECRET_CODE".equals(intento.getAction())) {
            Log.d(null, " -- -- SarValReceiver");
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setClass(contexto, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(i);
        }

    }

}
