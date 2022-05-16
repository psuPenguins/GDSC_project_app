package com.example.gdsc_project_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    //TODO: create private variables (btn, tv..)
    private Button btnLogOut;
    private Button btnSwipe;
    private Button btnRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "I'm in MainActivity");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.mainFragment_view);
        //setup the pager
        setupViewPager(mViewPager);


        // TODO: link the private variables to the elements in the xml files
        btnLogOut = findViewById((R.id.btnLogOut));
        btnSwipe = findViewById((R.id.btnSwipe));
        btnRoom = findViewById((R.id.btnRoom));

        // TODO: create onclicklistener for the button to the change the activity
        btnSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick Swipe button");
                goSwipeActivity();
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick Room button");
                goRoomActivity();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Logout button");
                goLoginActivity();
            }
        });
    }



    //declaring transition functions
    private void goSwipeActivity(){
        Intent i = new Intent(this, SwipeActivity.class);
        Log.i(TAG, "Going into SwipeActivity");
        startActivity(i);
    }

    private void goRoomActivity(){
        Intent i = new Intent(this, RoomActivity.class);
        Log.i(TAG, "Going into RoomActivity");
        startActivity(i);
    }

    private void goLoginActivity(){
        Intent i = new Intent(this, LoginActivity.class);
        Log.i(TAG, "Going into LoginActivity");
        startActivity(i);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RoomFragment(), "Fragment1");
        adapter.addFragment(new SwipeFragment(), "Fragment2");
        viewPager.setAdapter(adapter);
    }


}