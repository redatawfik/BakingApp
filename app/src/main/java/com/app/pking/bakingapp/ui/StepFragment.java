package com.app.pking.bakingapp.ui;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.model.Step;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepFragment extends Fragment {

    static final String TAG = StepFragment.class.getSimpleName();

    private Context context;
    private Step mStep;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private long playerPosition;
    private boolean isPlayWhenReady;


    public void setContext(Context context) {
        this.context = context;
    }

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable("k");
        }


        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        mPlayerView = rootView.findViewById(R.id.exo_video_view);
        TextView description = rootView.findViewById(R.id.fr_tv_description);
        ImageView imageView = rootView.findViewById(R.id.iv_step_image);


        if (mStep != null) {
            description.setText(mStep.getDescription());
        } else {
            Log.v(TAG, "This fragment has a null step");
        }

        if (mStep.getThumbnailURL() != null && !mStep.getThumbnailURL().equals("")) {
            Glide.with(this).load(mStep.getThumbnailURL()).into(imageView);
        } else if (mStep.getVideoURL() != null && !mStep.getVideoURL().equals("")) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(R.drawable.prepare_image);
        }


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        try {
            if (mStep.getVideoURL() != null && !mStep.getVideoURL().equals("")) {
                initializePlayer(Uri.parse(mStep.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
            }

        } catch (NullPointerException e) {
            Log.e(TAG, "error in initializing player===============================");
        }
    }

    private void initializePlayer(Uri uri) {
        if (mPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    context,
                    trackSelector,
                    loadControl);
            mPlayerView.setPlayer(mPlayer);


            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            if (playerPosition != C.TIME_UNSET) mPlayer.seekTo(playerPosition);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(isPlayWhenReady);
        }


    }

    public void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            Log.v("=====================", "Player released");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("onSaveInstanceState", "===================================================================================");
        outState.putParcelable("k", mStep);
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.v("onPause", "===================================================================================");
        if (mPlayer != null) {
            playerPosition = mPlayer.getCurrentPosition();
            isPlayWhenReady = mPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    public void setmStep(Step mStep) {
        this.mStep = mStep;
    }


    public long getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(long playerPosition) {
        this.playerPosition = playerPosition;
    }

    public boolean isPlayWhenReady() {
        return isPlayWhenReady;
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        isPlayWhenReady = playWhenReady;
    }


}
