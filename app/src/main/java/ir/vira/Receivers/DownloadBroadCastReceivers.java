package ir.vira.Receivers;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import ir.vira.CustomToast.PersianToast;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;

public class DownloadBroadCastReceivers extends BroadcastReceiver {

    private Poems poems;
    private static DatabaseTransaction databaseTransaction;
    private ImageView imageViewDownload;
    private String address;


    public DownloadBroadCastReceivers(Context context , Poems poems , ImageView imageViewDownload) {
        this.poems = poems;
        this.imageViewDownload = imageViewDownload;
        if (databaseTransaction == null)
            databaseTransaction = new DatabaseTransaction(context);
    }

    public void setImageViewDownload(ImageView imageViewDownload) {
        this.imageViewDownload = imageViewDownload;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        PersianToast persianToast = new PersianToast(context);
        File file = new File(poems.getVoice());
        address = Environment.getExternalStorageDirectory().getPath()+"/Ghazal/"+file.getName();
        file = new File(address);
        if (file.exists()){
            poems.setAddressDownloadedVoice(address);
            databaseTransaction.updateAddressesDownload(poems);
            imageViewDownload.animate().scaleX(0).scaleY(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageViewDownload.setImageResource(R.drawable.ic_check_circle);
                    imageViewDownload.animate().scaleY(1).scaleX(1).setDuration(500).setInterpolator(new OvershootInterpolator()).start();
                }
            }).start();
            persianToast.makeText("فایل با موفقیت دانلود شد.", R.string.iran_sans_bold , Toast.LENGTH_LONG);
        }else {
            persianToast.makeText("دانلود فایل ناموفق بود.", R.string.iran_sans_bold , Toast.LENGTH_LONG);
        }
    }
}
