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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ir.vira.CustomToast.PersianToast;
import ir.vira.PoemsTextActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class RecyclerPoemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Poems> poems , tempPoems;
    private DatabaseTransaction databaseTransaction;
    private Context context;

    public RecyclerPoemsAdapter(List<Poems> poems , Context context) {
        this.poems = poems;
        tempPoems = poems;
        databaseTransaction = new DatabaseTransaction(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_poems_item , parent , false);
        return new PoemsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final PoemsViewHolder poemsViewHolder = (PoemsViewHolder) holder;
        poemsViewHolder.textView.setText(tempPoems.get(position).getName());
        if (!tempPoems.get(position).getVoice().equals("")){
            poemsViewHolder.imageViewDownload.setVisibility(View.VISIBLE);
            if (tempPoems.get(position).getAddressDownloadedVoice() != null)
                poemsViewHolder.imageViewDownload.setImageResource(R.drawable.ic_check_circle);
            else
                poemsViewHolder.imageViewDownload.setImageResource(R.drawable.ic_file_download);
        }else
            poemsViewHolder.imageViewDownload.setVisibility(View.INVISIBLE);
        if (tempPoems.get(position).isBookmark() == true){
            poemsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark);
        }else
            poemsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark_border);
        poemsViewHolder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempPoems.get(position).isBookmark() == false){
                    databaseTransaction.updateBookmark(tempPoems.get(position) , true);
                    poemsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark);
                }
                else{
                    databaseTransaction.updateBookmark(tempPoems.get(position) , false);
                    poemsViewHolder.imageViewBookmark.setImageResource(R.drawable.ic_bookmark_border);
                }
            }
        });
        poemsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , PoemsTextActivity.class);
                intent.putExtra("poem" , tempPoems.get(position));
                context.startActivity(intent);
            }
        });
        poemsViewHolder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianToast persianToast = new PersianToast(context);
                if (tempPoems.get(position).getAddressDownloadedVoice() != null){
                    persianToast.makeText("شما قبلا این فایل را دانلود کردید.",R.string.iran_sans_bold, Toast.LENGTH_LONG);
                }else {
                    persianToast.makeText("برای دانلود وارد شعر شوید",R.string.iran_sans_bold,Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tempPoems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                tempPoems = new ArrayList<>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    poems.forEach((poem) ->{if (poem.getName().contains(charSequence)){tempPoems.add(poem);}});
                else
                    for (Poems poem:poems) {
                        if (poem.getName().contains(charSequence))
                            tempPoems.add(poem);
                    }
                return (FilterResults) (new FilterResults().values = tempPoems);
                } @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}
class PoemsViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageViewBookmark,imageViewDownload;
    CardView cardView;
    static Utils utils;

    public PoemsViewHolder(@NonNull View itemView) {
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
