package ir.vira.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.vira.Adapters.RecyclerHomeAdapter;
import ir.vira.R;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home,container,false);
        recyclerView = view.findViewById(R.id.frag_home_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),2, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new RecyclerHomeAdapter(new DatabaseTransaction(container.getContext()).getPoets() , container.getContext()));
        return view;
    }
}
