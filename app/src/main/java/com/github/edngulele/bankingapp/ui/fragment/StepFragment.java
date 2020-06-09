package com.github.edngulele.bankingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.model.Step;
import com.github.edngulele.bankingapp.util.Constants;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    private Step step;
    private TextView shortDesc;
    private TextView description;
    private Guideline horizontalHalf;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private boolean playWhenReady = true;
    private long position = -1;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            step = bundle.getParcelable("Step");
            Log.d("Step", "is not null");

        }

        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(Constants.SELECTED_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY);

        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);


        shortDesc = view.findViewById(R.id.tv_step_short_desc);
        description = view.findViewById(R.id.tv_step_desc);
        horizontalHalf = view.findViewById(R.id.horizontalHalf);

        playerView = view.findViewById(R.id.playerView);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shortDesc.setText(step.getShortDescription());
        description.setText(step.getDescription());

        if (!TextUtils.isEmpty(step.getVideoURL())) {
            initializerPlayer(Uri.parse(step.getVideoURL()));
        } else {
            playerView.setVisibility(View.INVISIBLE);
            horizontalHalf.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            position = simpleExoPlayer.getCurrentPosition();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private void initializerPlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            playerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            if (position > 0)
                simpleExoPlayer.seekTo(position);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constants.SELECTED_POSITION, position);
        outState.putBoolean(Constants.PLAY_WHEN_READY, playWhenReady);
    }
}
