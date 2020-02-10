package ir.vira.BottomNavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import ir.vira.R;
import ir.vira.Utils.Utils;

public class BottomNavigation {

    private TextView textView;
    private ImageView imageView;
    private int[] images = {R.drawable.ic_bookmark , R.drawable.ic_download , R.drawable.ic_home , R.drawable.ic_search , R.drawable.ic_menu};
    private String[] texts = {"نشانک ها","دانلودها","خانه","جستجو","منو"};
    private TabLayout tabLayout;

    public BottomNavigation(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void setTabs(Context context){
        Utils utils = new Utils(context);
        for (int i = 0; i < images.length ; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = LayoutInflater.from(context).inflate(R.layout.tab_item, (ViewGroup) tabLayout.getRootView(),false);
            imageView = view.findViewById(R.id.tab_item_image);
            textView = view.findViewById(R.id.tab_item_text);
            textView.setText(texts[i]);
            imageView.setImageResource(images[i]);
            utils.setFonts(textView,R.string.iran_sans_regular);
            if (i != 2){
                imageView.setAlpha(0.5f);
                textView.setAlpha(0.5f);
            }
            tab.setCustomView(view);
            tabLayout.addTab(tab);
        }
        tabLayout.getTabAt(2).select();
    }
    public void changeAlpha(TabLayout.Tab tab , float alpha){
        View view = tab.getCustomView();
        textView = view.findViewById(R.id.tab_item_text);
        imageView = view.findViewById(R.id.tab_item_image);
        textView.setAlpha(alpha);
        imageView.setAlpha(alpha);
        tab.setCustomView(view);

    }
}
