package ir.vira.Adapters;

import android.Manifest;
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

import java.io.File;
import java.util.List;

import ir.vira.CustomToast.PersianToast;
import ir.vira.PoemsTextActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class RecyclerDownloadsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Poems> poems;
    private DatabaseTransaction databaseTransaction;
    private Context context;
    private TextView textView;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 29;
    private Utils utils ;

    public RecyclerDownloadsAdapter(List<Poems> poems, Context context , TextView textView) {
        this.poems = poems;
        this.context = context;
        this.textView = textView;
        databaseTransaction = new DatabaseTransaction(context);
        utils = new Utils(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_poems_item , parent , false);
        return new DownloadsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final DownloadsViewHolder downloadsViewHolder = (DownloadsViewHolder) holder;
        downloadsViewHolder.textView.setText(poems.get(position).getName());
        if (poems.get(position).isBookmark() == true){
            downloadsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark);
        }else
            downloadsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark_border);
        downloadsViewHolder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (poems.get(position).isBookmark() == false){
                    databaseTransaction.updateBookmark(poems.get(position) , true);
                    downloadsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark);
                }
                else{
                    databaseTransaction.updateBookmark(poems.get(position) , false);
                    downloadsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark_border);
                }
            }
        });
        downloadsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , PoemsTextActivity.class);
                intent.putExtra("poem" , poems.get(position));
                context.startActivity(intent);
            }
        });
        downloadsViewHolder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
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
        downloadsViewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (utils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE , "لطفا مجوز دسترسی نوشتن در حافظه را فعال کنید." , REQUEST_CODE_WRITE_EXTERNAL_STORAGE )){
                    File file = new File(poems.get(position).getAddressDownloadedVoice());
                    if (file.exists())
                        file.delete();
                    poems.get(position).setAddressDownloadedVoice(null);
                    databaseTransaction.updateAddressesDownload(poems.get(position));
                    poems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position , poems.size());
                    if (poems.size() == 0)
                        textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return poems.size();
    }
}
class DownloadsViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageViewBookmark,imageViewDownload , imageViewDelete;
    CardView cardView;
    static Utils utils;

    public DownloadsViewHolder(@NonNull View itemView) {
        super(itemView);
        if (utils == null)
            utils = new Utils(itemView.getContext());
        textView = itemView.findViewById(R.id.recycler_poems_name);
        imageViewBookmark = itemView.findViewById(R.id.recycler_poems_bookmark);
        imageViewDownload = itemView.findViewById(R.id.recycler_poems_download);
        imageViewDelete = itemView.findViewById(R.id.recycler_poems_delete);
        imageViewDelete.setVisibility(View.VISIBLE);
        cardView = itemView.findViewById(R.id.recycler_poems_card);
        utils.setFonts(textView,R.string.iran_sans_bold);
    }
}
