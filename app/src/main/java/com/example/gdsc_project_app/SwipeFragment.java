package com.example.gdsc_project_app;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.parse.Parse.getApplicationContext;

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

public class SwipeFragment extends Fragment {

    public static final String TAG = "SwipeFragment";
    private SwipePlaceHolderView swipeView;
    //private Context someContext;

    //FBUser user;
    FirebaseUser currentUser;
    String currentUserUID;
    String currentUsername;
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
        //someContext = getApplicationContext();

        swipeView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL);

        // generate the cards from database.
        queryQuestion();
    }


    // generate cards from data base
    private void queryQuestion() {
//        ParseQuery<Question> query = new ParseQuery<>("Question");
//        query.findInBackground(new FindCallback<Question>() {
//            @Override
//            public void done(List<Question> dbQs, ParseException e) {
//                Point p = new Point(0, 0); // Point maybe useless, don't know yet.
//                if (e == null) {
//                    for (Question q : dbQs) {
//                        Log.i(TAG, q.getQuestion());
//                        Log.i(TAG, "" + swipeView.getChildCount());
//                        swipeView.addView(new SwipeItem(getActivity(), swipeView, getApplicationContext(), q.getQuestion(), p));
//                    }
//                } else {
//                    Log.e(TAG, "ERROR getting data");
//                }
//            }
//        });
        //Getting User
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUID = currentUser.getUid();
        Log.i(TAG, "PF CurrentUID:"+currentUserUID);
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

}
