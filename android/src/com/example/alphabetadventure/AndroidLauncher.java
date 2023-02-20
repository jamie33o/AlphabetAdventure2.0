package com.example.alphabetadventure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements AdHandler{
	private static final String TAG = "AndroidLauncher";
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

			RelativeLayout layout = new RelativeLayout(this);
			AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
			View gameview = initializeForView(new MainClass(this), config);
			layout.addView(gameview);


			adView = new AdView(this);

			adView.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					Log.i(TAG, "Ad Loaded....");

				}
			});

			adView.setAdSize(AdSize.FULL_BANNER);
			adView.setAdUnitId("ca-app-pub-3061474414782380/5431935249");

			AdRequest.Builder builder = new AdRequest.Builder();
			RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

			//puts add at bottom of page
			adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			adParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);


			layout.addView(adView, adParams);
			adView.loadAd(builder.build());

			setContentView(layout);


		}


	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show? SHOW_ADS :HIDE_ADS);
	}
}

/*

public class AndroidLauncher extends AndroidApplication implements AdsController  {



	InterstitialAd mInterstitialAd;
	private static final String TAG = "Androidlauncher";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// // Create the layout
		RelativeLayout layout = new RelativeLayout(this);
		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libGDX View
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new MainClass(), config);
		layout.addView(gameView);

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {}
		});

		AdRequest adRequest = null;
		InterstitialAd.load(this,"ca-app-pub-3061474414782380/4407709758", adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						Log.i(TAG, "onAdLoaded");
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						Log.d(TAG, loadAdError.toString());
						mInterstitialAd = null;
					}
				});

		mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
			@Override
			public void onAdClicked() {
				// Called when a click is recorded for an ad.
				Log.d(TAG, "Ad was clicked.");
			}

			@Override
			public void onAdDismissedFullScreenContent() {
				// Called when ad is dismissed.
				// Set the ad reference to null so you don't show the ad a second time.
				Log.d(TAG, "Ad dismissed fullscreen content.");
				mInterstitialAd = null;
			}

			@Override
			public void onAdFailedToShowFullScreenContent(AdError adError) {
				// Called when ad fails to show.
				Log.e(TAG, "Ad failed to show fullscreen content.");
				mInterstitialAd = null;
			}

			@Override
			public void onAdImpression() {
				// Called when an impression is recorded for an ad.
				Log.d(TAG, "Ad recorded an impression.");
			}

			@Override
			public void onAdShowedFullScreenContent() {
				// Called when ad is shown.
				Log.d(TAG, "Ad showed fullscreen content.");
			}

		});
		loadInterstitialAd();
	}

	@Override
	public void loadInterstitialAd() {
		AdRequest adRequest = new AdRequest.Builder().build();

	}

	@Override
	public void showInterstitialAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(mInterstitialAd!=null) {
					mInterstitialAd.show(AndroidLauncher.this);
				}
				else loadInterstitialAd();
			}
		});


	}
}*/
