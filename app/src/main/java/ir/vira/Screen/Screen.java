package ir.vira.Screen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import java.util.HashMap;

public class Screen {
    public static HashMap<String , Integer> getScreenSize(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        HashMap<String , Integer> sizes = new HashMap<>();
        sizes.put("width" , display.getWidth());
        sizes.put("height" , display.getHeight());
        return sizes;
    }
}
