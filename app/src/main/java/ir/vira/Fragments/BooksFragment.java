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
import ir.vira.Adapters.RecyclerBooksSearchAdapter;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;

public class BooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseTransaction databaseTransaction;
    private Context context;
    private static RecyclerBooksSearchAdapter recyclerBooksSearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_books , container , false);
        recyclerView = view.findViewById(R.id.frag_books_recycler);
        databaseTransaction = new DatabaseTransaction(container.getContext());
        recyclerBooksSearchAdapter = new RecyclerBooksSearchAdapter(databaseTransaction.getBooks() , context ,databaseTransaction.getPoets());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setAdapter(recyclerBooksSearchAdapter);
        return view;
    }

    public static RecyclerBooksSearchAdapter getRecyclerBooksSearchAdapter() {
        return recyclerBooksSearchAdapter;
    }
}
