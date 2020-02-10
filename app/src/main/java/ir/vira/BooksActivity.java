package ir.vira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ir.vira.Adapters.RecyclerBooksAdapter;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poets;
import ir.vira.Utils.Utils;

public class BooksActivity extends AppCompatActivity {


    private ImageView imageView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        activityInit();
        Poets poets = (Poets) getIntent().getSerializableExtra("poet");
        Glide.with(this).load(poets.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        textView.setText(poets.getName());
        DatabaseTransaction databaseTransaction = new DatabaseTransaction(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this  , 3, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new RecyclerBooksAdapter(databaseTransaction.getBooks(poets.getId()),poets.getImage(),this));

    }

    private void activityInit() {
        Utils utils = new Utils(this);
        utils.changeLayoutDirection(LayoutDirection.LTR);
        imageView = findViewById(R.id.books_poet_image);
        recyclerView = findViewById(R.id.books_recycler);
        toolbar = findViewById(R.id.books_toolbar);
        textView = toolbar.findViewById(R.id.toolbar_book_poet_name);
        utils.setToolbar(toolbar , "" , true , true , this , LayoutDirection.LTR , R.drawable.ic_arrow_dark);
        utils.setFonts(textView , R.string.iran_sans_bold);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
