package ir.vira.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ir.vira.MediaController.MediaController;

public class ConnectivityBroadReceiver extends BroadcastReceiver {
    private MediaController mediaController;

    public ConnectivityBroadReceiver(MediaController mediaController) {
        this.mediaController = mediaController;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (mediaController.isMediaPlayerPlaying())
                mediaController.stopMusic();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
