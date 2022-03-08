package com.example.gdsc_project_app;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity {

    public static final String TAG = "SwipeActivity";
    private SwipePlaceHolderView swipeView;
    private Context someContext; // not sure if I need this
    private ArrayList<String> questions; // question bank

    //TODO: create private variables (btn, tv..)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_swipe));

        swipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        someContext = getApplicationContext();

        swipeView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_DEFAULT);

        // question samples
        questions = new ArrayList<>();
        questions.add("Do you like grapes?");
        questions.add("Pineapple on pizza?");
        questions.add("Do you pee in the shower?");
        questions.add("Is ketchup on mac n cheese ok?");
        questions.add("Boneless wings are better than bone in wings.");
        questions.add("Lukewarm water is better than cold water.");
        questions.add("Rap music is music");
        questions.add("URg?");

        // for later
        Point p = new Point(0,0);

        for (String q: questions){
            swipeView.addView(new SwipeItem(someContext, q, p));
        }


        // TODO: link the private variables to the elements in the xml files
    }

    // TODO: create onclicklistener for the button to the change the activity
}
