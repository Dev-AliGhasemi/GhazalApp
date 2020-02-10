package ir.vira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Arrays;

import ir.vira.CustomToast.PersianToast;
import ir.vira.Network.NetworkState;
import ir.vira.ServerTransaction.ServerTransaction;
import ir.vira.Utils.Utils;

public class SplashScreen extends AppCompatActivity {

    private Button button;
    private SpinKitView spinKitView;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityInit();
        SharedPreferences sharedPreferences = getSharedPreferences("pref_cached" , Context.MODE_PRIVATE);
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(SplashScreen.this , PoetsActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        if (sharedPreferences.contains("isDataCached"))
            thread.start();
        else
            getData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData(){
        spinKitView.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        if (NetworkState.isNetworkActive(this)){
            ServerTransaction serverTransaction = new ServerTransaction(this);
            serverTransaction.getDataFromServer(button , spinKitView , thread);
        }else{
            PersianToast persianToast = new PersianToast(this);
            persianToast.makeText("شما به اینترنت متصل نمی باشید.",R.string.iran_sans_bold, Toast.LENGTH_LONG);
            spinKitView.setVisibility(View.INVISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }

    private void activityInit() {
        Utils utils = new Utils(this);
        utils.changeLayoutDirection(LayoutDirection.LTR);
        button = findViewById(R.id.splash_btn_try_again);
        spinKitView = findViewById(R.id.splash_progress);
        utils.setFonts(button , R.string.iran_sans_bold);
    }
}
