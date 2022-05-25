package com.c323FinalProject.ctaddeuc;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    ListView categoriesListView;
    List<Category> categoryList = new ArrayList<>();

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_categories_layout, container, false);
        categoriesListView = view.findViewById(R.id.trashListView);
        DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);

        Cursor res = dbHandler.getCategories();

        if(res.getCount() == 0){
            dbHandler.addCategoryDB(new Category("Movies"));
            dbHandler.addCategoryDB(new Category("Restaurants"));
            dbHandler.addCategoryDB(new Category("Books"));
            dbHandler.addCategoryDB(new Category("Places"));

        }


        while(res.getCount() > 0 && res.moveToNext()){
            categoryList.add(new Category(res.getString(1))); //add categories stored in db to list
        }


        ArrayAdapter<Category> categoryArrayAdapter = new CategoryArrayAdapter();
        categoriesListView.setAdapter(categoryArrayAdapter);

        return view;

    }


    private class CategoryArrayAdapter extends ArrayAdapter<Category> {

        public CategoryArrayAdapter(){
            super(view.getContext(), R.layout.category_layout, categoryList);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
           if(convertView == null){
               convertView = getLayoutInflater().inflate(R.layout.category_layout, parent, false);

               Category category = categoryList.get(position);
               TextView categoryTextView = convertView.findViewById(R.id.favoriteTextView);

               categoryTextView.setText(category.getCategoryName());

               categoryTextView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       //start second fragment
                       FragmentManager fragmentManager = getParentFragmentManager();
                       //pass selected category
                       Bundle bundle = new Bundle(); //https://developer.android.com/guide/fragments/communicate
                       bundle.putString("category", category.getCategoryName() + "");
                       DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);
                       Cursor res = dbHandler.getCategoryId(category);
                       String id = "";
                       while(res.moveToNext()){
                           id = res.getString(0);
                       }

                       bundle.putString("id", id);
                       getParentFragmentManager().setFragmentResult("requestKey", bundle);
                       fragmentManager.beginTransaction().replace(R.id.framelayout_id, new ReviewListFragment()).commit();

                   }
               });



           }
           return convertView;
        }
    }

}
