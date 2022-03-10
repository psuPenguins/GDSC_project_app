package com.example.gdsc_project_app;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class SwipeActivity extends AppCompatActivity {

    public static final String TAG = "SwipeActivity";
    private SwipePlaceHolderView swipeView;
    private Context someContext; // not sure if I need this
    //TODO: create private variables (btn, tv..)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_swipe));

        swipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        someContext = getApplicationContext();

        swipeView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL);

        queryQuestion();

        // TODO: link the private variables to the elements in the xml files
    }

    private void queryQuestion(){
        ParseQuery<Question> query = new ParseQuery<>("Question");
        query.findInBackground(new FindCallback<Question>() {
        @Override
        public void done(List<Question> dbQs, ParseException e) {
            Point p = new Point(0,0); // Point maybe useless, don't know yet.
            if (e == null) {
                for (Question q : dbQs) {
                    Log.i(TAG, q.getQuestion());
                    swipeView.addView(new SwipeItem(someContext, q.getQuestion(), p));
                }
            } else {
                Log.e(TAG,"ERROR getting data");
            }
        }
        });
    }
    // TODO: create onclicklistener for the button to the change the activity
}
