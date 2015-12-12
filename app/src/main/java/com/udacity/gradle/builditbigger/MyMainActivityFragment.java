package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.nulleye.udacity.jokeandroidlib.ITaskResult;
import com.nulleye.udacity.jokeandroidlib.JokeSmithEndpoint;

/**
 * Created by cristian on 1/12/15.
 */
abstract public class MyMainActivityFragment extends Fragment implements ITaskResult {

    protected JokeSmithEndpoint jokeTeller;
    public static final int JOKE_TELL = 1;

    protected ProgressBar progressBar = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jokeTeller = new JokeSmithEndpoint();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }


    protected void enableProgress(boolean enable) {
        if (progressBar != null) progressBar.setVisibility((enable)? View.VISIBLE : View.GONE);
    }


    protected boolean progressEnabled() {
        return (progressBar != null) && (progressBar.getVisibility() == View.VISIBLE);
    }


    abstract public void tellJoke(View view);

}
