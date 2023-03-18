package com.kannada.ringtoneapp.best.songs.ringtones.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


import com.kannada.ringtoneapp.best.songs.ringtones.Adconfig;

import com.kannada.ringtoneapp.best.songs.ringtones.R;
import com.kannada.ringtoneapp.best.songs.ringtones.ui.list.RingtoneListFragment;


public class HomeFragment extends Fragment {
    private String privacyUrl = "https://kannadaringtones99.blogspot.com/2023/03/kannada-ringtones.html";

    private AdView adViewfacebook;


    private InterstitialAd interstitialAd;

    ImageButton share, rate, privacy;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setHasOptionsMenu(true);
        View inflate = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);

        adViewfacebook = new com.facebook.ads.AdView(getContext(), Adconfig.BANNER, AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(getContext(), Adconfig.INTERSTITIAL);

        LinearLayout adContainer = (LinearLayout) inflate.findViewById(R.id.banner_container);
        adContainer.addView(adViewfacebook);

        adViewfacebook.loadAd();

        inflate.setClickable(true);
        inflate.setFocusable(true);

//        loadads();
        share=inflate.findViewById(R.id.share);
        rate=inflate.findViewById(R.id.idrate);
        privacy=inflate.findViewById(R.id.privacy);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getContext().getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
                sendIntent.setType("text/plain");
                getContext().startActivity(sendIntent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), " unable to find market app", Toast.LENGTH_LONG).show();
                }
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(privacyUrl));
                startActivity(i);
            }
        });


        ((ImageButton) inflate.findViewById(R.id.nextButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                ProgressDialog progressDialog = ProgressDialog.show(getContext(),"Ad Loading","Please Wait Loading Ad...",true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        ShowFbInter();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new RingtoneListFragment()).addToBackStack(null).commitAllowingStateLoss();
                    }
                },3000);

            }
        });
        return inflate;
    }
    public void ShowFbInter(){
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new RingtoneListFragment()).addToBackStack(null).commitAllowingStateLoss();

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new RingtoneListFragment()).addToBackStack(null).commitAllowingStateLoss();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }






//    public void loadads() {
//
//        isloaded =false;
//        noloaded =false;
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        InterstitialAd.load(getContext(), getString(R.string.Interstitial), adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        isloaded = true;
//                        if (noloaded){
//                            progressDialog.dismiss();
//                            showIntAds();
//                        }
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        mInterstitialAd = null;
//                    }
//                });
//    }


//    public void showIntAds(){
//        if (mInterstitialAd != null){
//            mInterstitialAd.show(getActivity());
//            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent();
//                    mInterstitialAd = null;
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new RingtoneListFragment()).addToBackStack(null).commitAllowingStateLoss();
//
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                    mInterstitialAd = null;
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new RingtoneListFragment()).addToBackStack(null).commitAllowingStateLoss();
//
//                }
//            });
//        }
//    };



    @Override 
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        TextView textView = (TextView) getActivity().findViewById(R.id.titletext);
        textView.setText(getString(R.string.app_name));
        textView.setTextColor(getResources().getColor(R.color.white));
    }


}
