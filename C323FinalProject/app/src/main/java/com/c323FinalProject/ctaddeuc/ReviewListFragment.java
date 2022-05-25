package com.c323FinalProject.ctaddeuc;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentResultListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewListFragment extends Fragment {

    View view;
    String category, id;
    List<ReviewList> reviewLists = new ArrayList<>();
    ListView reviewListsListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviewlist_layout, container, false);

        //https://developer.android.com/guide/fragments/communicate
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                category = bundle.getString("category");
                id = bundle.getString("id");
                Log.i("category", category);
                Log.i("id", id);

                DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);
                Cursor res = dbHandler.getReviewList(id);

                while(res.moveToNext()){
                    reviewLists.add(new ReviewList(id, res.getString(1), ""));
                }

                ArrayAdapter<ReviewList> reviewListArrayAdapter = new ReviewListArrayAdapter();

                reviewListsListView = view.findViewById(R.id.reviewlistListView);
                reviewListsListView.setAdapter(reviewListArrayAdapter);







            }
        });


        return view;

    }

    private class ReviewListArrayAdapter extends ArrayAdapter<ReviewList> {

        public ReviewListArrayAdapter(){
            super(view.getContext(), R.layout.reviewlist_layout, reviewLists);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.reviewlist_layout, parent, false);

                ReviewList reviewList = reviewLists.get(position);
                TextView reviewlistTextView = convertView.findViewById(R.id.reviewlistTextView);

                reviewlistTextView.setText(reviewList.getReviewListName());

                reviewlistTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //start second fragment
                        FragmentManager fragmentManager = getParentFragmentManager();
                        //pass selected category
                        Bundle bundle = new Bundle(); //https://developer.android.com/guide/fragments/communicate
                        bundle.putString("categoryId",  reviewList.getCategoryId()+ "");
                        bundle.putString("reviewName", reviewList.getReviewListName()+"");

                        DBHandler dbHandler = new DBHandler(view.getContext(), null, null, 1);
                        Cursor res = dbHandler.getReviewId(reviewList);
                        String reviewListId = "";
                        while(res.moveToNext()){
                            reviewListId = res.getString(0);
                        }


                        bundle.putString("reviewListId", reviewListId);
                        getParentFragmentManager().setFragmentResult("requestKey2", bundle);
                        fragmentManager.beginTransaction().replace(R.id.framelayout_id, new ReviewFragment()).commit();

                    }
                });



            }
            return convertView;
        }
    }


}
