package ir.vira.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import ir.vira.BooksActivity;
import ir.vira.R;
import ir.vira.RoomDatabase.Entities.Poets;
import ir.vira.Utils.Utils;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Poets> poets;
    private Context context;

    public RecyclerHomeAdapter(List<Poets> poets , Context context) {
        this.poets = poets;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.textView.setText(poets.get(position).getName());
        Glide.with(homeViewHolder.itemView.getContext()).load(poets.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(homeViewHolder.imageView);
        homeViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , BooksActivity.class);
                intent.putExtra("poet" , poets.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poets.size();
    }
}
class HomeViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView imageView;
    static Utils utils;
    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        if (utils == null)
            utils = new Utils(itemView.getContext());
        textView = itemView.findViewById(R.id.recycler_home_text);
        imageView = itemView.findViewById(R.id.recycler_home_image);
        utils.setFonts(textView,R.string.iran_sans_bold);
    }
}
