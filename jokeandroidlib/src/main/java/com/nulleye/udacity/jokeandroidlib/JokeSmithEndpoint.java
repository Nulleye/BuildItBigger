package com.nulleye.udacity.jokeandroidlib;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nulleye.udacity.jokebackend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by cristian on 23/11/15.
 */
public class JokeSmithEndpoint {

    // options for running against local devappserver

    // - 10.0.2.2 is localhost's IP address in Android emulator
    // - turn off compression when running against local devappserver
    public static final String EMULATOR = "10.0.2.2:8080";

    //My computer local network IP address
    public static final String LOCAL_NETWORK = "192.168.1.41:8080";

    public static String ENDPOINT_SERVER = LOCAL_NETWORK;

    // end options for devappserver


    static class EndpointsAsyncTask extends AsyncTask<Pair<Integer,ITaskResult>, Void, Pair<String,Throwable>> {

        private static MyApi myApiService = null;
        int requestCode;
        ITaskResult response;

        @Override
        protected Pair<String,Throwable> doInBackground(Pair<Integer,ITaskResult>... params) {
            try {
                requestCode = params[0].first;
                response = params[0].second;

                if(myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            .setRootUrl("http://" + ENDPOINT_SERVER + "/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    myApiService = builder.build();
                }

                return new Pair<>(myApiService.getJoke().execute().getData(), null);

            } catch (IOException e) {
                return new Pair<String,Throwable>(null, e);
            }
        }


        @Override
        protected void onPostExecute(Pair<String,Throwable> result) {
            if (response != null) {
                if (result.second == null) response.onTaskSuccess(requestCode, result.first);
                else response.onTaskFail(requestCode, result.second);
            }
        }


    }


    public void getJoke(final int requestCode, final ITaskResult response) {
        try {
            new EndpointsAsyncTask().execute(new Pair<>(requestCode, response));
        } catch (Exception e) {
            if (response != null) response.onTaskFail(requestCode, e);
        }
    }


    public void tellJoke(final Context context, final String joke) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_INTENT_TEXT, joke);
        context.startActivity(intent);
    }


}
