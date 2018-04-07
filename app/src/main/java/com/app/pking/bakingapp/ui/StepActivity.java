package com.app.pking.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.model.Step;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    List<Step> mStepList;
    int index;
    StepFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        setStep();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment = new StepFragment();
        fragment.setmStep(mStepList.get(index));
        fragment.setContext(this);

        fragmentManager.beginTransaction()
                .add(R.id.step_container, fragment)
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
        if(index+1 >= mStepList.size()){
            Toast.makeText(this,"End of steps",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, StepActivity.class);

            intent.putExtra("step", Parcels.wrap(mStepList));
            intent.putExtra("index", mStepList.get(index).getId()+1);
            startActivity(intent);
        }

    }
}
