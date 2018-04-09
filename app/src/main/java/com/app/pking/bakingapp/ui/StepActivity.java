package com.app.pking.bakingapp.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.model.Step;
import com.google.android.exoplayer2.C;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    private static final String INDEX_KEY = "indexKey";
    private List<Step> mStepList;
    private int index;
    private StepFragment fragment;
    private long playerPosition = 0;
    private boolean isPlayWhenReady = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setStep();

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(INDEX_KEY);
            playerPosition = savedInstanceState.getLong("playerPosition");
            isPlayWhenReady = savedInstanceState.getBoolean("playerState");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment = new StepFragment();
        fragment.setmStep(mStepList.get(index));
        fragment.setContext(this);
        fragment.setPlayerPosition(playerPosition);
        fragment.setPlayWhenReady(isPlayWhenReady);

        fragmentManager.beginTransaction()
                .replace(R.id.step_container, fragment)
                .commit();

    }

    private void setStep() {
        Intent intent = getIntent();
        mStepList = Parcels.unwrap(intent.getParcelableExtra("step"));
        index = intent.getIntExtra("index", 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragment != null) {
            fragment.releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fragment != null) {
            fragment.releasePlayer();
        }

    }


    public void goToNextStep(View view) {
        if (index + 1 >= mStepList.size()) {
            Toast.makeText(this, "End of steps", Toast.LENGTH_SHORT).show();
        } else {
            index++;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = new StepFragment();
            fragment.setmStep(mStepList.get(index));
            fragment.setContext(this);

            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INDEX_KEY, index);


        outState.putLong("playerPosition", playerPosition);
        outState.putBoolean("playerState", isPlayWhenReady);
    }


    @Override
    protected void onPause() {
        super.onPause();
        playerPosition = fragment.getPlayerPosition();
        isPlayWhenReady = fragment.isPlayWhenReady();
    }
}
