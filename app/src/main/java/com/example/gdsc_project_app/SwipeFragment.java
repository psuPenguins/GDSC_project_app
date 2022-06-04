package com.example.gdsc_project_app;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.parse.Parse.getApplicationContext;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SwipeFragment extends Fragment {

    public static final String TAG = "SwipeFragment";
    private SwipePlaceHolderView swipeView;
    private static ProgressBar pbSwipeProgress;
    private static int currentProgress = 0;

    //FBUser user;
    ArrayList<String> questions = new ArrayList<String>();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference topicsRef = database.getReference("Topics");

    public SwipeFragment() { }

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
        pbSwipeProgress = (ProgressBar) view.findViewById(R.id.pbSwipeProgress);

        swipeView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL);

        instructionPopup();

        // generate the cards from database.
        queryQuestion();
        pbSwipeProgress.setMax(100);
    }


    // generate cards from data base
    private void queryQuestion() {
        //Getting Username
        topicsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questions.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FBTopic topic = snapshot.getValue(FBTopic.class);
                    questions.add(topic.question);
                    Log.i(TAG, "topic question: " + topic.question);
                }

                for (String question : questions) {
                    Point p = new Point(0, 0); // Point maybe useless, don't know yet.
                    Log.i(TAG, "" + swipeView.getChildCount());
                    swipeView.addView(new SwipeItem(getActivity(), swipeView, getApplicationContext(), question, p));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "The read failed for user: " + databaseError.getCode());
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(getContext(), MainActivity.class);
        Log.i("SwipItem", "Going into Room");
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void addSwipeProgress(){
        currentProgress = currentProgress + 14;
        pbSwipeProgress.setProgress(currentProgress);
    }

    public void instructionPopup(){
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        android.view.View popupView = inflater.inflate(R.layout.swipe_instruction_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, 1500, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, -150);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
