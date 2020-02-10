package ir.vira.CustomToast;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ir.vira.Utils.Utils;

public class PersianToast extends Toast {


    private Context context;

    public PersianToast(Context context) {
        super(context);
        this.context = context;
    }

    public  void makeText(String text, int fontPath, int duration){
        Utils utils = new Utils(context);
        Toast toast = Toast.makeText(context, text, duration);
        View view = toast.getView();
        TextView textView = view.findViewById(android.R.id.message);
        utils.setFonts(textView , fontPath);
        this.setView(view);
        this.show();
    }
}
