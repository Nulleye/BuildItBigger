package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends MyMainActivityFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    @Override
    public void tellJoke(View view){
//1
//        Toast.makeText(this, new JokeSmith().getJoke(), Toast.LENGTH_SHORT).show();

//2
//        Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
//        intent.putExtra(JokeActivity.JOKE_INTENT_TEXT, new JokeSmith().getJoke());
//        startActivity(intent);

//3
        if (progressEnabled()) return;
        enableProgress(true);
        jokeTeller.getJoke(JOKE_TELL, this);
    }


    @Override
    public void onTaskSuccess(int requestCode, Object result) {
        enableProgress(false);
        if ((requestCode == JOKE_TELL) && (result != null) && (result instanceof String) &&
                !((String) result).trim().isEmpty()) jokeTeller.tellJoke(getActivity(), (String) result);
    }

    @Override
    public void onTaskFail(int requestCode, Throwable t) {
        enableProgress(false);
        Toast.makeText(getActivity(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
