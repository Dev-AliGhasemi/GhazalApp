package ir.vira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import ir.vira.Utils.Utils;

public class AboutUsActivity extends AppCompatActivity {

    private TextView textViewGhazal , textViewVira , textViewTitleToolbar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        activityInit();
    }

    private void activityInit() {
        Utils utils = new Utils(this);
        toolbar = findViewById(R.id.about_toolbar);
        textViewGhazal = findViewById(R.id.about_text_ghazal);
        textViewVira = findViewById(R.id.about_text_vira);
        textViewTitleToolbar = toolbar.findViewById(R.id.toolbar_book_poet_name);
        textViewTitleToolbar.setVisibility(View.GONE);
        utils.changeLayoutDirection(LayoutDirection.LTR);
        utils.setToolbar(toolbar , "" , true , true , this , LayoutDirection.LTR , R.drawable.ic_arrow_dark);
        utils.setFonts(textViewGhazal , R.string.iran_sans_regular);
        utils.setFonts(textViewVira , R.string.iran_sans_regular);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()  == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
