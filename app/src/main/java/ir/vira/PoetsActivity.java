package ir.vira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import ir.vira.BottomNavigation.BottomNavigation;
import ir.vira.Fragments.BookmarkFragment;
import ir.vira.Fragments.DownloadsFragment;
import ir.vira.Fragments.HomeFragment;
import ir.vira.Fragments.MenuFragment;
import ir.vira.Fragments.SearchFragment;
import ir.vira.Utils.Utils;

public class PoetsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private BottomNavigation bottomNavigation;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poets);
        activityInit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bottomNavigation.changeAlpha(tab , 1);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                switch (tab.getPosition()){
                    case 0:
                        fragmentTransaction.replace(R.id.poet_frame , new BookmarkFragment());
                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.poet_frame , new DownloadsFragment());
                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.poet_frame , new HomeFragment());
                        break;
                    case 3:
                        fragmentTransaction.replace(R.id.poet_frame , new SearchFragment());
                        break;
                    case 4:
                        fragmentTransaction.replace(R.id.poet_frame , new MenuFragment());
                        break;
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                bottomNavigation.changeAlpha(tab , 0.5f);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void activityInit() {
        Utils utils = new Utils(this);
        toolbar = findViewById(R.id.poet_toolbar);
        tabLayout = findViewById(R.id.poet_bottom_navigation);
        utils.changeLayoutDirection(LayoutDirection.LTR);
        utils.setToolbar(toolbar , "" , this , LayoutDirection.LTR);
        bottomNavigation = new BottomNavigation(tabLayout);
        bottomNavigation.setTabs(this);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.poet_frame , new HomeFragment());
        fragmentTransaction.commit();
    }

}
