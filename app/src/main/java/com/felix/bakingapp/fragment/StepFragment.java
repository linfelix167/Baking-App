package com.felix.bakingapp.fragment;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.felix.bakingapp.R;
import com.felix.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String PLAYER = "player";

    private ArrayList<Step> stepList;
    private Step step;
    private SimpleExoPlayer mExoPlayer;
    private long mPlayerPosition;

    @BindView(R.id.player_view)
    SimpleExoPlayerView mSimpleExoPlayerView;

    @BindView(R.id.step_desc)
    TextView mStepTextView;

    public StepFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            stepList = getArguments().getParcelableArrayList(ARG_ITEM_ID);
            int position = getArguments().getInt("id");
            step = stepList.get(position);
        }

        if (savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        if (step != null) {
            mStepTextView.setText(step.getShortDescription());
            setupPlayer(Uri.parse(step.getVideoURL()));
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mPlayerPosition = mExoPlayer.getCurrentPosition();
        }
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER, mPlayerPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Button mPrevBtn = getActivity().findViewById(R.id.btn_prev);
        Button mNextBtn = getActivity().findViewById(R.id.btn_next);

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mStepTextView.setVisibility(View.GONE);
            mPrevBtn.setVisibility(View.GONE);
            mNextBtn.setVisibility(View.GONE);

            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().hide();
            }
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE
            );

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSimpleExoPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mSimpleExoPlayerView.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mStepTextView.setVisibility(View.VISIBLE);
            mPrevBtn.setVisibility(View.VISIBLE);
            mNextBtn.setVisibility(View.VISIBLE);

            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().show();
            }

            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSimpleExoPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.WRAP_CONTENT;
            mSimpleExoPlayerView.setLayoutParams(params);
        }
    }

    private void setupPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(this.getActivity(), "RecipeStep");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this.getActivity(), userAgent);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mPlayerPosition);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlayerPosition = mExoPlayer.getContentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public void setupView(int position) {
        step = stepList.get(position);
        if (step != null) {
            mStepTextView.setText(step.getShortDescription());
            releasePlayer();
            mPlayerPosition = 0;
            setupPlayer(Uri.parse(step.getVideoURL()));
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
