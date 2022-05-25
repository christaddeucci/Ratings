package com.c323FinalProject.ctaddeuc;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

public class ReviewFragment extends Fragment {

    View view;
    String categoryId, reviewListId, reviewName, reviewDetails, reviewId;
    TextView reviewTextView, reviewDetailsTextView;
    ImageButton favoriteImageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_review_layout, container, false);

        //https://developer.android.com/guide/fragments/communicate
        getParentFragmentManager().setFragmentResultListener("requestKey2", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                categoryId = bundle.getString("categoryId");
                reviewName = bundle.getString("reviewName");
                reviewListId = bundle.getString("reviewListId");
                Log.i("categoryId", categoryId);
                Log.i("reviewName", reviewName);
                Log.i("reviewListId", reviewListId);
                reviewTextView = view.findViewById(R.id.reviewTextView);
                reviewDetailsTextView = view.findViewById(R.id.reviewDetailsTextView);
                favoriteImageButton = view.findViewById(R.id.favoriteImageButton);



                DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);
                Cursor res = dbHandler.getReview(reviewListId);

                while(res.moveToNext()){
                    reviewDetailsTextView.setText(res.getString(0));
                    reviewId = res.getString(1);
                }
                //Toast.makeText(view.getContext(), "reviewId: " + reviewId, Toast.LENGTH_SHORT);
                Log.i("reviewId", reviewId);



                reviewTextView.setText(reviewName);
                favoriteImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);
                        dbHandler.addToFavorites(reviewId, reviewListId, reviewName);

                        Toast.makeText(view.getContext(), "Add to Favorites", Toast.LENGTH_SHORT);
                    }
                });









            }
        });


        return view;
    }
}
