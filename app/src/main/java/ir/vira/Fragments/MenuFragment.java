package ir.vira.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.vira.AboutUsActivity;
import ir.vira.Adapters.ListMenuAdapter;
import ir.vira.R;

public class MenuFragment extends Fragment {

    private ListView listView;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_menu , container , false);
        listView = view.findViewById(R.id.frag_menu_list);
        listView.setAdapter(new ListMenuAdapter(container.getContext()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        container.getContext().startActivity(new Intent(container.getContext() , AboutUsActivity.class));
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("bazaar://details?id="+container.getContext().getPackageName()));
                        intent.setPackage("com.farsitel.bazaar");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setAction(Intent.ACTION_EDIT);
                        intent.setData(Uri.parse("bazaar://details?id="+container.getContext().getPackageName()));
                        intent.setPackage("com.farsitel.bazaar");
                        startActivity(intent);
                        break;
                }
            }
        });
        return view;
    }
}
