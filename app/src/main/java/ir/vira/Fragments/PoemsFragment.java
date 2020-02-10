package ir.vira.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.vira.Adapters.RecyclerBookmarkAdapter;
import ir.vira.Adapters.RecyclerBooksAdapter;
import ir.vira.Adapters.RecyclerPoemsAdapter;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;

public class PoemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseTransaction databaseTransaction;
    private Context context;
    private static RecyclerPoemsAdapter recyclerPoemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_poems , container , false);
        databaseTransaction = new DatabaseTransaction(container.getContext());
        recyclerView = view.findViewById(R.id.frag_poems_recycler);
        recyclerPoemsAdapter = new RecyclerPoemsAdapter(databaseTransaction.getPoems() , context);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setAdapter(recyclerPoemsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerPoemsAdapter = new RecyclerPoemsAdapter(databaseTransaction.getPoems() , context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false));
        recyclerView.setAdapter(recyclerPoemsAdapter);
    }

    public static RecyclerPoemsAdapter getRecyclerPoemsAdapter() {
        return recyclerPoemsAdapter;
    }
}
