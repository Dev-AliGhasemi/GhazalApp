package ir.vira.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import ir.vira.BooksActivity;
import ir.vira.PoemsActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poets;
import ir.vira.Utils.Utils;

public class RecyclerBooksSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Books> books , tempBooks;
    private List<Poets> poets;
    private Context context;

    public RecyclerBooksSearchAdapter(List<Books> books, Context context , List<Poets> poets) {
        this.books = books;
        this.context = context;
        this.poets = poets;
        tempBooks = books;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_books_search_item , parent , false);
        return new BooksSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BooksSearchViewHolder booksSearchViewHolder = (BooksSearchViewHolder) holder;
        booksSearchViewHolder.textViewPublisher.setText(tempBooks.get(position).getPublisher());
        booksSearchViewHolder.textViewBookName.setText(tempBooks.get(position).getName());
        booksSearchViewHolder.textViewAuthor.setText(poets.get(tempBooks.get(position).getPoetID()-1).getName());
        Glide.with(context).load(tempBooks.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(booksSearchViewHolder.imageView);
        booksSearchViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , PoemsActivity.class);
                intent.putExtra("book" , tempBooks.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tempBooks.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence charSequence) {
                tempBooks = new ArrayList<>();
                if (charSequence.length() != 0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        books.forEach((book)->{if (book.getName().contains(charSequence)){tempBooks.add(book);}});
                    else
                        for (Books book:books) {
                            if (book.getName().contains(charSequence))
                                tempBooks.add(book);
                        }
                }else
                    tempBooks = books;
                return (FilterResults) (new FilterResults().values = tempBooks);
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}
class BooksSearchViewHolder extends RecyclerView.ViewHolder{


    ImageView imageView;
    TextView textViewBookName , textViewAuthor , textViewPublisher;
    CardView cardView;
    private Utils utils;


    public BooksSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        if (utils == null)
            utils = new Utils(itemView.getContext());
        imageView = itemView.findViewById(R.id.recycler_books_search_image);
        textViewAuthor = itemView.findViewById(R.id.recycler_books_search_author);
        textViewBookName = itemView.findViewById(R.id.recycler_books_search_name);
        textViewPublisher = itemView.findViewById(R.id.recycler_books_search_publisher);
        cardView = itemView.findViewById(R.id.recycler_books_search_card);
        utils.setFonts(textViewBookName , R.string.iran_sans_bold);
        utils.setFonts(textViewAuthor , R.string.iran_sans_regular);
        utils.setFonts(textViewPublisher , R.string.iran_sans_regular);
    }
}
