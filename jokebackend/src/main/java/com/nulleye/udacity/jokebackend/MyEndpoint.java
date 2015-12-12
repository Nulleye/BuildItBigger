/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.nulleye.udacity.jokebackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.nulleye.udacity.jokelib.JokeSmith;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "jokebackend.udacity.nulleye.com",
    ownerName = "jokebackend.udacity.nulleye.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
//    @ApiMethod(name = "sayHi")
//    public MyBean sayHi(@Named("name") String name) {
//
//        MyBean response = new MyBean();
//        response.setData("Hi, " + name);
//
//        return response;
//    }

    @ApiMethod(name = "getJoke")
    public MyBean getJokei() {

        MyBean response = new MyBean();
        response.setData(new JokeSmith().getJoke());

        //Simulate network delay
//        try {
//            Thread.sleep(5000);
//        } catch(Exception e) {
//          e.printStackTrace();
//        }

        return response;
    }


}
