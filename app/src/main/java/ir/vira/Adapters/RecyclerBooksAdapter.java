package ir.vira.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ir.vira.AboutPoetsPopupActivity;
import ir.vira.PoemsActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;
import ir.vira.Utils.Utils;

public class RecyclerBooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Books> books;
    private String urlImage;
    private Context context;

    public RecyclerBooksAdapter(List<Books> books, String urlImage , Context context) {
        this.books = books;
        this.urlImage = urlImage;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_books_item , parent , false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        BooksViewHolder booksViewHolder = (BooksViewHolder) holder;
        if (position == 0){
            booksViewHolder.textView.setText("درباره شاعر");
            Glide.with(booksViewHolder.itemView.getContext()).load(urlImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(booksViewHolder.imageView);
        }else {
            booksViewHolder.textView.setText(books.get(position-1).getName());
            Glide.with(booksViewHolder.itemView.getContext()).load(books.get(position-1).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(booksViewHolder.imageView);
        }
        booksViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0){
                    Intent intent = new Intent(context , AboutPoetsPopupActivity.class);
                    intent.putExtra("poetID" , books.get(position).getPoetID());
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context , PoemsActivity.class);
                    intent.putExtra("book" , books.get(position-1));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size()+1;
    }
}
class BooksViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageView;
    CardView cardView;
    static Utils utils;

    public BooksViewHolder(@NonNull View itemView) {
        super(itemView);
        if (utils == null)
            utils = new Utils(itemView.getContext());
        textView = itemView.findViewById(R.id.recycler_books_text);
        imageView = itemView.findViewById(R.id.recycler_books_image);
        cardView = itemView.findViewById(R.id.recycler_books_card);
        utils.setFonts(textView , R.string.iran_sans_bold);
    }
}
