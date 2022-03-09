package com.example.gdsc_project_app;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gdsc_project_app.adapters.CommentsAdapter;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
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

        // question samples
        /*
        questions.add("Do you like grapes?");
        questions.add("Pineapple on pizza?");
        questions.add("Do you pee in the shower?");
        questions.add("Is ketchup on mac n cheese ok?");
        questions.add("Boneless wings are better than bone in wings.");
        questions.add("Lukewarm water is better than cold water.");
        questions.add("Rap music is music");
        questions.add("URg?");
         */
        // Define the class we would like to query
        // for later
        Point p = new Point(0, 0);

        queryQuestion();


        // TODO: link the private variables to the elements in the xml files
    }

    private void queryQuestion(){
        ParseQuery<Question> query = new ParseQuery<>("Question");
        query.findInBackground(new FindCallback<Question>() {
        @Override
        public void done(List<Question> dbQs, ParseException e) {
            Point p = new Point(0,0);
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
