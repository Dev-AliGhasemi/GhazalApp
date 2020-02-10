package ir.vira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ir.vira.Adapters.RecyclerPoemsAdapter;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.Utils.Utils;

public class PoemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageView;
    private TextView textView;
    private Toolbar toolbar;
    private Books books;
    private DatabaseTransaction databaseTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poems);
        books = (Books) getIntent().getSerializableExtra("book");
        activityInit();
        databaseTransaction = new DatabaseTransaction(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        recyclerView.setAdapter(new RecyclerPoemsAdapter(databaseTransaction.getPoems(books.getId()),this));
    }

    private void activityInit() {
        Utils utils = new Utils(this);
        toolbar = findViewById(R.id.poems_toolbar);
        imageView = findViewById(R.id.poems_book_image);
        recyclerView = findViewById(R.id.poems_recycler);
        textView = toolbar.findViewById(R.id.toolbar_book_poet_name);
        textView.setText(books.getName());
        utils.changeLayoutDirection(LayoutDirection.LTR);
        Glide.with(this).load(books.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        utils.setToolbar(toolbar,"" , true , true , this , LayoutDirection.LTR , R.drawable.ic_arrow_dark);
        utils.setFonts(textView , R.string.iran_sans_bold);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        recyclerView.setAdapter(new RecyclerPoemsAdapter(databaseTransaction.getPoems(books.getId()),this));
    }
}
