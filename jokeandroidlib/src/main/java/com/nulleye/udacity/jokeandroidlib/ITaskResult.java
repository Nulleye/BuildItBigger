package com.nulleye.udacity.jokeandroidlib;

/**
 * Created by cristian on 27/11/15.
 */
public interface ITaskResult {

    void onTaskSuccess(final int requestCode, final Object result);

    void onTaskFail(final int requestCode, final Throwable t);

}
