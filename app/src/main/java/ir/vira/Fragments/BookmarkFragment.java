package ir.vira.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ir.vira.Adapters.RecyclerBookmarkAdapter;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class BookmarkFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseTransaction databaseTransaction;
    private Context context;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_bookmarks , container , false);
        recyclerView = view.findViewById(R.id.frag_bookmark_recycler);
        textView = view.findViewById(R.id.frag_bookmark_text);
        Utils utils = new Utils(context);
        utils.setFonts(textView , R.string.iran_sans_regular);
        databaseTransaction = new DatabaseTransaction(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext() , LinearLayoutManager.VERTICAL , false));
        List<Poems> poems = databaseTransaction.getPoems(true);
        if (poems.size() != 0){
            textView.setVisibility(View.GONE);
            recyclerView.setAdapter(new RecyclerBookmarkAdapter(poems , container.getContext() , textView));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Poems> poems = databaseTransaction.getPoems(true);
        if (poems.size() != 0){
            textView.setVisibility(View.GONE);
            recyclerView.setAdapter(new RecyclerBookmarkAdapter(poems , context , textView));
        }else {
            recyclerView.setAdapter(null);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
