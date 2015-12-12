package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.nulleye.udacity.jokeandroidlib.ITaskResult;
import com.nulleye.udacity.jokeandroidlib.JokeSmithEndpoint;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by cristian on 27/11/15.
 */

public class JokeTest extends AndroidTestCase implements ITaskResult {
//public class JokeTest extends TestCase implements ITaskResult {

    public static final String TAG = JokeTest.class.getSimpleName();
    public static final int JOKE_TEST = 1;

    private CountDownLatch signal;
    private String result;
    private Throwable resultException;

    private void setSignal(final int countDown) {
        result = null;
        resultException = null;
        signal = new CountDownLatch(countDown);
    }

    private void waitSignal(final int seconds) {
        try {
            signal.await(seconds, TimeUnit.SECONDS);
        } catch(InterruptedException ie) {
            resultException = ie;
        }
    }

    @SmallTest
    public void testGetJoke() {
//        android.util.Log.d(TAG, "testGetJoke: started!");
        setSignal(1);
        new JokeSmithEndpoint().getJoke(JOKE_TEST, this);
        waitSignal(30);
        if (resultException != null) fail(resultException.getMessage());
        else assertTrue(true);
    }


    @Override
    public void onTaskSuccess(int requestCode, Object result) {
//        android.util.Log.d(TAG, "onTaskSuccess: " + result);

        if (requestCode == JOKE_TEST) {
            if (result instanceof String) {
                final String res = (String) result;
                if ((res != null) && !res.trim().isEmpty()) this.result = res;
                else this.resultException = new Exception("Success result is empty!");
                signal.countDown();
                return;
            }
        }
        this.resultException = new Exception("Unexpected success result!");
        signal.countDown();
    }


    @Override
    public void onTaskFail(int requestCode, Throwable t) {
//        android.util.Log.d(TAG, "onTaskFail: " + t.getMessage());
        this.resultException = t;
        signal.countDown();
    }

}
