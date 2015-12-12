package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends MyMainActivityFragment {

    InterstitialAd mInterstitialAd;

    boolean needShowAd = false;
    boolean showJokeNow = false;
    String displayJoke = null;


    protected void resetFlags() {
        needShowAd = false;
        showJokeNow = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                resetFlags();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (needShowAd) {
                    needShowAd = false;
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                if (displayJoke != null) {
                    resetFlags();
                    jokeTeller.tellJoke(getActivity(), displayJoke);
                    displayJoke = null;
                } else showJokeNow = true;
                requestNewInterstitial();
            }

        });

        requestNewInterstitial();

//        AdView mAdView = (AdView) root.findViewById(R.id.adView);
//        // Create an ad request. Check logcat output for the hashed device ID to
//        // get test ads on a physical device. e.g.
//        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        mAdView.loadAd(adRequest);

        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public void tellJoke(View view) {
        if (progressEnabled()) return;
        enableProgress(true);
        resetFlags();
        displayJoke = null;
        jokeTeller.getJoke(JOKE_TELL, this);
        if (mInterstitialAd.isLoaded()) {
            needShowAd = false;
            mInterstitialAd.show();
        } else if (mInterstitialAd.isLoading()) needShowAd = true;
        else needShowAd = false;
    }


    @Override
    public void onTaskSuccess(int requestCode, Object result) {
        enableProgress(false);
        if ((requestCode == JOKE_TELL) && (result != null) && (result instanceof String) &&
                !((String) result).trim().isEmpty()) {
            displayJoke = (String) result;
            if (needShowAd) {
                if (mInterstitialAd.isLoaded()) {
                    needShowAd = false;
                    mInterstitialAd.show();
                }
            }
            if (showJokeNow) {
                resetFlags();
                jokeTeller.tellJoke(getActivity(), displayJoke);
                displayJoke = null;
            }
        } else resetFlags();
    }

    @Override
    public void onTaskFail ( int requestCode, Throwable t) {
        enableProgress(false);
        resetFlags();
        Toast.makeText(getActivity(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }


}
