package com.example.gdsc_project_app;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;

import android.app.Activity;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.content.Context;


@Layout(R.layout.item_swipe_card)
public class SwipeItem {

    @View(R.id.questionText)
    TextView questionText;

    @SwipeView
    android.view.View mSwipeView;

    // mQuestion is the question and mContext it for SwipeActivity
    // mCardViewHolderSize is something that might be needed later, not sure right now.
    private String mQuestion;
    private Context mContext;
    private Point mCardViewHolderSize;
    private SwipePlaceHolderView mHolderView;
    private Activity mActivity;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");

    public SwipeItem(Activity activity, SwipePlaceHolderView holderView, Context context, String question, Point CardViewHolderSize){
        mContext = context;
        mQuestion = question;
        mCardViewHolderSize = CardViewHolderSize;
        mHolderView = holderView;
        mActivity = activity;
    }

    // pretty sure this just sets the card up with the question
    @Resolve
    public void onResolved() {
        questionText.setText(mQuestion);
        mSwipeView.setAlpha(1);
    }

    @SwipeOutDirectional
    public void onSwipeOutDirectional(SwipeDirection direction) {
        Log.i("DEBUG", "SwipeOutDirectional " + direction.name());
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.i("DEBUG", "onSwipeCancelState");
        mSwipeView.setAlpha(1);
    }

    @SwipeInDirectional
    public void onSwipeInDirectional(SwipeDirection direction) {
        Log.i("DEBUG", "SwipeInDirectional " + direction.name());
    }

    @SwipingDirection
    public void onSwipingDirection(SwipeDirection direction) {
        Log.i("DEBUG", "SwipingDirection " + direction.name());
    }

    // out = left
    @SwipeOut
    public void onSwipedOut() {
        Log.i("DEBUG", "onSwipedLeft");
        SwipeFragment.addSwipeProgress();
        if (mHolderView.getChildCount() == 1) {
            doneSwipingPopup();
        }
    }

    // in = right
    @SwipeIn
    public void onSwipeIn() {
        Log.i("DEBUG", "onSwipedRight");
        SwipeFragment.addSwipeProgress();
        if (mHolderView.getChildCount() == 1) {
            doneSwipingPopup();
        }
    }

    // popup screen telling user that there is no more questions
    public void doneSwipingPopup(){
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        android.view.View popupView = inflater.inflate(R.layout.swipe_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
//        popupView.setOnTouchListener(new android.view.View.OnTouchListener() {
//            @Override
//            public boolean onTouch(android.view.View v, MotionEvent event) {
//                popupWindow.dismiss();
//                return true;
//            }
//        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                usersRef.child(currentUserID).child("swipe").setValue(true);
                popupWindow.dismiss();
            }
        }, 5000);   //5 second
    }


}
