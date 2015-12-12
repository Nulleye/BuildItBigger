package com.nulleye.udacity.jokeandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_INTENT_TEXT = "joke_intent_text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        final String jokeText = getIntent().getStringExtra(JOKE_INTENT_TEXT);
        TextView jokeTextView = (TextView) findViewById(R.id.jokeText);
        if ((jokeText != null) && !jokeText.trim().isEmpty()) jokeTextView.setText(jokeText);
        else jokeTextView.setText(R.string.no_joke_text);

    }

}
