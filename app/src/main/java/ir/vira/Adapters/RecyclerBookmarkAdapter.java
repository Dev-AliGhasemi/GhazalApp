package ir.vira.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ir.vira.CustomToast.PersianToast;
import ir.vira.PoemsTextActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class RecyclerBookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Poems> poems;
    private DatabaseTransaction databaseTransaction;
    private Context context;
    private TextView textView;

    public RecyclerBookmarkAdapter(List<Poems> poems , Context context , TextView textView) {
        this.poems = poems;
        this.context = context;
        this.textView = textView;
        databaseTransaction = new DatabaseTransaction(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_poems_item , parent , false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final BookmarkViewHolder bookmarkViewHolder = (BookmarkViewHolder) holder;
        bookmarkViewHolder.textView.setText(poems.get(position).getName());
        if (!poems.get(position).getVoice().equals("")){
            bookmarkViewHolder.imageViewDownload.setVisibility(View.VISIBLE);
            if (poems.get(position).getAddressDownloadedVoice() != null)
                bookmarkViewHolder.imageViewDownload.setImageResource(R.drawable.ic_check_circle);
            else
                bookmarkViewHolder.imageViewDownload.setImageResource(R.drawable.ic_file_download);
        }else
            bookmarkViewHolder.imageViewDownload.setVisibility(View.INVISIBLE);
        bookmarkViewHolder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseTransaction.updateBookmark(poems.get(position) , false);
                poems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position , poems.size());
                if (poems.size() == 0)
                    textView.setVisibility(View.VISIBLE);
            }
        });
        bookmarkViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , PoemsTextActivity.class);
                intent.putExtra("poem" , poems.get(position));
                context.startActivity(intent);
            }
        });
        bookmarkViewHolder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianToast persianToast = new PersianToast(context);
                if (poems.get(position).getAddressDownloadedVoice() != null){
                    persianToast.makeText("شما قبلا این فایل را دانلود کردید.",R.string.iran_sans_bold, Toast.LENGTH_LONG);
                }else {
                    persianToast.makeText("برای دانلود وارد شعر شوید",R.string.iran_sans_bold,Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return poems.size();
    }
}
class BookmarkViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageViewBookmark,imageViewDownload;
    CardView cardView;
    static Utils utils;

    public BookmarkViewHolder(@NonNull View itemView) {
        super(itemView);
        if (utils == null)
            utils = new Utils(itemView.getContext());
        textView = itemView.findViewById(R.id.recycler_poems_name);
        imageViewBookmark = itemView.findViewById(R.id.recycler_poems_bookmark);
        imageViewDownload = itemView.findViewById(R.id.recycler_poems_download);
        cardView = itemView.findViewById(R.id.recycler_poems_card);
        utils.setFonts(textView,R.string.iran_sans_bold);
    }
}
