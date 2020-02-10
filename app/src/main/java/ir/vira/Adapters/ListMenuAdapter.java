package ir.vira.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ir.vira.R;
import ir.vira.Utils.Utils;

public class ListMenuAdapter extends BaseAdapter {

    private ImageView imageView;
    private TextView textView;
    private Object[][] items;

    public ListMenuAdapter(Context context) {
        items = new Object[3][2];
        int[] images = {R.drawable.ic_people , R.drawable.ic_update , R.drawable.ic_message};
        for (int i = 0; i < items.length; i++) {
            items[i][0] = context.getResources().getStringArray(R.array.text_array)[i];
            items[i][1] = images[i];
        }
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_menu_item , viewGroup , false);
        textView = view.findViewById(R.id.list_menu_text);
        imageView = view.findViewById(R.id.list_menu_image);
        Utils utils = new Utils(viewGroup.getContext());
        utils.setFonts(textView , R.string.iran_sans_bold);
        textView.setText(items[position][0]+"");
        imageView.setImageResource((Integer) items[position][1]);
        return view;
    }
}
