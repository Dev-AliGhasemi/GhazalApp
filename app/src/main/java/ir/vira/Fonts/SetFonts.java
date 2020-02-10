package ir.vira.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SetFonts {
    private Context context;
    private int fontPath;
    private Typeface font;

    public SetFonts(Context context, int fontPath) {
        this.context = context;
        this.fontPath = fontPath;
        font = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(fontPath));
    }
    public void setPath(int fontPath){
        font = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(fontPath));
        fontPath = fontPath;
    }
    public int getPathFont(){
        return fontPath;
    }
    public void setFont(View view)
    {
        if (view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setTypeface(font);
        }else if (view instanceof Button){
            Button button = (Button) view;
            button.setTypeface(font);
        }else if (view instanceof EditText){
            EditText editText = (EditText) view;
            editText.setTypeface(font);
        }else if (view instanceof TextInputLayout){
            TextInputLayout textInputLayout = (TextInputLayout) view;
            textInputLayout.setTypeface(font);
        }
    }
}
