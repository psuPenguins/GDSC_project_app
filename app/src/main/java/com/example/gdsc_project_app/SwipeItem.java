package com.example.gdsc_project_app;

import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;

import android.graphics.Point;
import android.util.Log;
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

    public SwipeItem(Context context, String question, Point CardViewHolderSize){
        mContext = context;
        mQuestion = question;
        mCardViewHolderSize = CardViewHolderSize;
    }

    // pretty sure this just sets the card up with the question
    @Resolve
    public void onResolved() {
        questionText.setText(mQuestion);
        mSwipeView.setAlpha(1);
    }

    @SwipeOutDirectional
    public void onSwipeOutDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name());
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
        mSwipeView.setAlpha(1);
    }

    @SwipeInDirectional
    public void onSwipeInDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name());
    }

    @SwipingDirection
    public void onSwipingDirection(SwipeDirection direction) {
        Log.d("DEBUG", "SwipingDirection " + direction.name());
    }

}
