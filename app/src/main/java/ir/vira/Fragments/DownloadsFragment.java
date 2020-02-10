package ir.vira.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import ir.vira.Adapters.RecyclerDownloadsAdapter;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class DownloadsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private DatabaseTransaction databaseTransaction;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_downloads , container , false);
        context = container.getContext();
        recyclerView = view.findViewById(R.id.frag_download_recycler);
        textView = view.findViewById(R.id.frag_download_text);
        Utils utils = new Utils(context);
        utils.setFonts(textView , R.string.iran_sans_regular);
        databaseTransaction = new DatabaseTransaction(container.getContext());
        List<Poems> poems = databaseTransaction.getDownloadedPoems();
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext() , RecyclerView.VERTICAL , false));
        if (poems.size() != 0){
            textView.setVisibility(View.GONE);
            recyclerView.setAdapter(new RecyclerDownloadsAdapter(poems , context , textView));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Poems> poems = databaseTransaction.getDownloadedPoems();
        if (poems.size() != 0){
            textView.setVisibility(View.GONE);
            recyclerView.setAdapter(new RecyclerDownloadsAdapter(poems , context , textView));
        }else {
            recyclerView.setAdapter(null);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
