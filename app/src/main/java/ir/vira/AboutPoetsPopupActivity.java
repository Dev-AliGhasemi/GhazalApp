package ir.vira;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;
import ir.vira.Screen.Screen;
import ir.vira.Utils.Utils;

public class AboutPoetsPopupActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewTitle,textViewText;
    private Poets poets;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_poets_popup_activty);
        getWindow().setLayout((int)(Screen.getScreenSize(this).get("width")*0.9) , (int)(Screen.getScreenSize(this).get("height")*0.9));
        int poetID = getIntent().getIntExtra("poetID" , 1);
        DatabaseTransaction databaseTransaction = new DatabaseTransaction(this);
        poets = databaseTransaction.getPoet(poetID);
        activityInit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void activityInit() {
        imageView = findViewById(R.id.about_poem_image);
        textViewText = findViewById(R.id.about_poem_text);
        textViewTitle = findViewById(R.id.about_poem_title);
        button = findViewById(R.id.about_poem_ok);
        Utils utils = new Utils(this);
        utils.changeLayoutDirection(LayoutDirection.LTR);
        utils.setFonts(textViewText , R.string.iran_sans_bold);
        utils.setFonts(textViewTitle , R.string.iran_sans_bold);
        utils.setFonts(button , R.string.iran_sans_bold);
        Glide.with(this).load(poets.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).apply(RequestOptions.circleCropTransform()).into(imageView);
        textViewTitle.setText(poets.getName());
        textViewText.setText(poets.getDescription());
    }
}
