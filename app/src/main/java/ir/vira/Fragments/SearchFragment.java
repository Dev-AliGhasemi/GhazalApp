package ir.vira.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ir.vira.R;
import ir.vira.Utils.Utils;

public class SearchFragment extends Fragment {

    private EditText editText;
    private View view;
    private Context context;
    private TextView textViewPoems , textViewPoets , textViewBooks;
    private Utils utils;
    private FragmentTransaction fragmentTransaction;
    private ImageView imageViewDoSearch;
    private int whichTab = 2;
    private BooksFragment booksFragment;
    private PoetsFragment poetsFragment;
    private PoemsFragment poemsFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_search , container , false);
        context = container.getContext();
        utils = new Utils(context);
        fragmentInit();
        changeFontStyle(textViewPoets);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0){
                    switch (whichTab){
                        case 1:
                            BooksFragment.getRecyclerBooksSearchAdapter().getFilter().filter("");
                            break;
                        case 2:
                            PoetsFragment.getRecyclerPoetsSearchAdapter().getFilter().filter("");
                            break;
                        case 3:
                            PoemsFragment.getRecyclerPoemsAdapter().getFilter().filter("");
                            break;
                    }
                }
            }
        });
        imageViewDoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (whichTab){
                    case 1:
                        BooksFragment.getRecyclerBooksSearchAdapter().getFilter().filter(editText.getText());
                        break;
                    case 2:
                        PoetsFragment.getRecyclerPoetsSearchAdapter().getFilter().filter(editText.getText());
                        break;
                    case 3:
                        PoemsFragment.getRecyclerPoemsAdapter().getFilter().filter(editText.getText());
                        break;

                }
            }
        });
        textViewPoets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichTab = 2;
                changeFontStyle(textViewPoets);
            }
        });
        textViewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichTab = 1;
                changeFontStyle(textViewBooks);
            }
        });
        textViewPoems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichTab = 3;
                changeFontStyle(textViewPoems);
            }
        });
        return view;
    }

    private void changeFontStyle(TextView textView) {
        utils.setFonts(textViewPoets , R.string.iran_sans_regular);
        utils.setFonts(textViewBooks , R.string.iran_sans_regular);
        utils.setFonts(textViewPoems , R.string.iran_sans_regular);
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (textView.getId()){
            case R.id.frag_search_tab_book:
                utils.setFonts(textViewBooks , R.string.iran_sans_bold);
                fragmentTransaction.replace(R.id.frag_search_frame , booksFragment);
                break;
            case R.id.frag_search_tab_poem:
                utils.setFonts(textViewPoems , R.string.iran_sans_bold);
                fragmentTransaction.replace(R.id.frag_search_frame , poemsFragment);
                break;
            case R.id.frag_search_tab_poet:
                utils.setFonts(textViewPoets , R.string.iran_sans_bold);
                fragmentTransaction.replace(R.id.frag_search_frame , poetsFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    private void fragmentInit() {
        poemsFragment = new PoemsFragment();
        poetsFragment = new PoetsFragment();
        booksFragment = new BooksFragment();
        editText = view.findViewById(R.id.frag_search_edit);
        textViewPoems = view.findViewById(R.id.frag_search_tab_poem);
        textViewPoets = view.findViewById(R.id.frag_search_tab_poet);
        textViewBooks = view.findViewById(R.id.frag_search_tab_book);
        imageViewDoSearch = view.findViewById(R.id.frag_search_btn);
        utils.setFonts(textViewBooks , R.string.iran_sans_regular);
        utils.setFonts(textViewPoems , R.string.iran_sans_regular);
        utils.setFonts(textViewPoets , R.string.iran_sans_bold);
        utils.setFonts(editText , R.string.iran_sans_bold);
    }
}
