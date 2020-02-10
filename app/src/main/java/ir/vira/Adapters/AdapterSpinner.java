package ir.vira.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ir.vira.Models.ModelFonts;
import ir.vira.R;
import ir.vira.Utils.Utils;

public class AdapterSpinner extends ArrayAdapter<ModelFonts> {


    private View view;
    private static final int OPEN_TYPE = 1;
    private static final int CLOSE_TYPE = 0;
    private TextView textView;
    private ImageView imageView;
    private Utils utils;
    private boolean isLightMode;


    public AdapterSpinner(@NonNull Context context, int resource, @NonNull List<ModelFonts> objects , boolean isLightMode) {
        super(context, resource, objects);
        utils = new Utils(context);
        this.isLightMode = isLightMode;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position , parent , CLOSE_TYPE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position , parent , OPEN_TYPE);
    }

    public View getCustomView(int position , ViewGroup viewGroup , int type){
        if (type == OPEN_TYPE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_open_view , viewGroup , false);
            imageView = view.findViewById(R.id.spinner_font_image);
            imageView.setImageResource(getItem(position).getFontImage());
        }else{
            if (isLightMode)
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_close_view_light, viewGroup , false);
            else
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_close_view_dark, viewGroup , false);
        }
        textView = view.findViewById(R.id.spinner_font_text);
        utils.setFonts(textView , R.string.iran_sans_bold);
        textView.setText(getItem(position).getFontName());
        textView.setSelected(true);
        return  view;
    }
}
