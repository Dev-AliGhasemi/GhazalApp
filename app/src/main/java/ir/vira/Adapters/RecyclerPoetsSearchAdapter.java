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
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import ir.vira.BooksActivity;
import ir.vira.PoemsActivity;
import ir.vira.PoetsActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;
import ir.vira.Utils.Utils;

public class RecyclerPoetsSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Poets> poets , tempPoets;
    private Context context;

    public RecyclerPoetsSearchAdapter(Context context , List<Poets> poets) {
        this.context = context;
        this.poets = poets;
        tempPoets = poets;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_poets_search_item , parent , false);
        return new PoetsSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PoetsSearchViewHolder poetsSearchViewHolder = (PoetsSearchViewHolder) holder;
        poetsSearchViewHolder.textViewPoetName.setText(tempPoets.get(position).getName());
        poetsSearchViewHolder.textViewDateBorn.setText(tempPoets.get(position).getDateBorn());
        poetsSearchViewHolder.textViewLocationBorn.setText(" محل تولد : "+tempPoets.get(position).getLocationBorn());
        Glide.with(context).load(tempPoets.get(position).getImage()).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.ALL).into(poetsSearchViewHolder.imageView);
        poetsSearchViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , BooksActivity.class);
                intent.putExtra("poet" , tempPoets.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tempPoets.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence charSequence) {
                tempPoets = new ArrayList<>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    poets.forEach((poet)->{if (poet.getName().contains(charSequence)){tempPoets.add(poet);}});
                else
                    for (Poets poet:poets) {
                        if (poet.getName().contains(charSequence))
                            tempPoets.add(poet);
                    }
                return (FilterResults) (new FilterResults().values = tempPoets);
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}

class PoetsSearchViewHolder extends RecyclerView.ViewHolder{


    ImageView imageView;
    TextView textViewPoetName , textViewDateBorn , textViewLocationBorn , textViewDateBornFake;
    CardView cardView;
    private Utils utils;


    public PoetsSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        if (utils == null)
            utils = new Utils(itemView.getContext());
        imageView = itemView.findViewById(R.id.recycler_poets_search_image);
        textViewDateBorn = itemView.findViewById(R.id.recycler_poets_search_date);
        textViewLocationBorn = itemView.findViewById(R.id.recycler_poets_search_location);
        textViewPoetName = itemView.findViewById(R.id.recycler_poets_search_name);
        textViewDateBornFake = itemView.findViewById(R.id.recycler_poets_search_date_fake);
        cardView = itemView.findViewById(R.id.recycler_poets_search_card);
        utils.setFonts(textViewPoetName , R.string.iran_sans_bold);
        utils.setFonts(textViewLocationBorn , R.string.iran_sans_regular);
        utils.setFonts(textViewDateBorn , R.string.iran_sans_regular);
        utils.setFonts(textViewDateBornFake , R.string.iran_sans_regular);
    }
}
