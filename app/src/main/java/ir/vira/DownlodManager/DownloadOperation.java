package ir.vira.DownlodManager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import ir.vira.CustomToast.PersianToast;
import ir.vira.Network.NetworkState;
import ir.vira.R;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class DownloadOperation{

    private static Utils utils;
    private Context context;
    private String downloadLink;
    private Poems poems;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 30;
    private ImageView imageViewDownload;
    private DownloadManager downloadManager;
    private long downloadID;

    public DownloadOperation(String downloadLink, Poems poems , Context context , ImageView imageViewDownload) {
        this.downloadLink = downloadLink;
        this.poems = poems;
        this.context = context;
        this.imageViewDownload = imageViewDownload;
        if (utils == null)
            utils = new Utils(context);
    }

    public void doDownload(){
        if (NetworkState.isNetworkActive(context)){
            if (utils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE , "لطفا مجوز دسترسی نوشتن در حافظه را فعال کنید.",REQUEST_CODE_WRITE_EXTERNAL_STORAGE)){
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadLink));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(" دانلود صوت "+poems.getName());
                File file = new File(Environment.getExternalStorageDirectory() + "/Ghazal");
                if (!file.exists())
                    file.mkdir();
                file = new File(downloadLink);
                request.setDestinationInExternalPublicDir("/","Ghazal/"+file.getName());
                downloadID = downloadManager.enqueue(request);
//                downloadBroadCastReceivers = new DownloadBroadCastReceivers(context , poems , imageViewDownload);
//                context.registerReceiver(downloadBroadCastReceivers , new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        }else {
            PersianToast persianToast = new PersianToast(context);
            persianToast.makeText("شما به اینترنت متصل نیستید.", R.string.iran_sans_bold, Toast.LENGTH_LONG);
        }
    }
    public void cancelDownloading(){
        if (downloadManager != null){
            downloadManager.remove(downloadID);
            PersianToast persianToast = new PersianToast(context);
            persianToast.makeText("دانلود متوقف شد.",R.string.iran_sans_bold,Toast.LENGTH_LONG);
        }
    }

}
