package com.example.gdsc_project_app;

import static com.parse.Parse.getApplicationContext;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class SwipeFragment extends Fragment {

    public static final String TAG = "SwipeFragment";
    private SwipePlaceHolderView swipeView;
    //private Context someContext;

    public SwipeFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_swipe, parent, false);
    }

    //actions within frame
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "I'm in SwipeFragment");
        super.onCreate(savedInstanceState);

        swipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeView);
        //someContext = getApplicationContext();

        swipeView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL);

        // generate the cards from database.
        queryQuestion();

    }


    // generate cards from data base
    private void queryQuestion(){
        ParseQuery<Question> query = new ParseQuery<>("Question");
        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> dbQs, ParseException e) {
                Point p = new Point(0,0); // Point maybe useless, don't know yet.
                if (e == null) {
                    for (Question q : dbQs) {
                        Log.i(TAG, q.getQuestion());
                        swipeView.addView(new SwipeItem(getApplicationContext(), q.getQuestion(), p));
                    }
                } else {
                    Log.e(TAG,"ERROR getting data");
                }
            }
        });
    }
    // TODO: create onclicklistener for the button to the change the activity
}
