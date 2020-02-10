package ir.vira.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ir.vira.CustomToast.PersianToast;
import ir.vira.Fonts.SetFonts;
import ir.vira.R;

public class Utils {
    private Context context;
    private static SetFonts setFonts;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static List<Integer> enteredRequestCodes = new ArrayList<>();

    public Utils(Context context) {
        this.context = context;
    }

    public void setFonts(View view , int fontPath){
        if (setFonts == null)
            setFonts = new SetFonts(context , fontPath);
        else
            setFonts.setPath(fontPath);
        setFonts.setFont(view);
    }

    public void setToolbar(Toolbar toolbar , String title , AppCompatActivity activity , DrawerLayout drawerLayout , int direction , int colorArrow){
        toolbar.setLayoutDirection(direction);
        actionBarDrawerToggle = new ActionBarDrawerToggle(activity,drawerLayout,0,0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        activity.setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(colorArrow);
    }
    public void setToolbar(Toolbar toolbar , String title , boolean isDisplayHomeAsUp , boolean isHomeButtonEnable , AppCompatActivity activity , int direction , int homeIndicator) {
        toolbar.setLayoutDirection(direction);
        activity.setSupportActionBar(toolbar);
        activity.setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplayHomeAsUp);
        activity.getSupportActionBar().setHomeButtonEnabled(isHomeButtonEnable);
        activity.getSupportActionBar().setHomeAsUpIndicator(homeIndicator);
    }
    public void setToolbar(Toolbar toolbar , String title , AppCompatActivity activity , int direction) {
        toolbar.setLayoutDirection(direction);
        activity.setSupportActionBar(toolbar);
        activity.setTitle(title);
    }

    public void changeLayoutDirection(int direction){
        Activity activity = (Activity) context;
        activity.getWindow().getDecorView().setLayoutDirection(direction);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return actionBarDrawerToggle;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean checkPermission(String permission , String message , int requestCode){
        if (ActivityCompat.checkSelfPermission(context , permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else {
            if (enteredRequestCodes.indexOf(requestCode)<0){
                ActivityCompat.requestPermissions((Activity)context , new String[]{permission} , requestCode);
                enteredRequestCodes.add(requestCode);
            }
            else
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission))
                ActivityCompat.requestPermissions((Activity)context , new String[]{permission} , requestCode);
            else {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:"+context.getPackageName()));
                context.startActivity(intent);
                PersianToast persianToast = new PersianToast(context);
                persianToast.makeText(message , R.string.iran_sans_bold , Toast.LENGTH_LONG);
            }
            return false;
        }
    }
}
