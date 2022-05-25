package com.c323FinalProject.ctaddeuc;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    View view;
    ListView favoritesListView;
    List<Favorite> favoriteList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites_layout, container, false);
        DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);

        Cursor res = dbHandler.getFavorites();

        while(res.getCount() > 0 && res.moveToNext()){
            favoriteList.add(new Favorite(res.getString(1), res.getString(2), res.getString(4), "")); //add categories stored in db to list
        }
        favoritesListView = view.findViewById(R.id.favoritesListView);

        ArrayAdapter<Favorite> favoriteArrayAdapter = new FavoriteArrayAdapter();
        favoritesListView.setAdapter(favoriteArrayAdapter);


        return view;
    }

    private class FavoriteArrayAdapter extends ArrayAdapter<Favorite> {

        public FavoriteArrayAdapter(){
            super(view.getContext(), R.layout.category_layout, favoriteList);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.favorite_layout, parent, false);

                Favorite favorite = favoriteList.get(position);
                TextView favoriteTextView = convertView.findViewById(R.id.favoriteTextView);
                ImageButton removeImageButton = convertView.findViewById(R.id.removeImageButton);

                favoriteTextView.setText(favorite.getReviewName());

                removeImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "Item removed from Favorites", Toast.LENGTH_SHORT);
                        DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);
                        dbHandler.removeFavorite(favorite.getReviewName());

                    }
                });



            }
            return convertView;
        }
    }
}
