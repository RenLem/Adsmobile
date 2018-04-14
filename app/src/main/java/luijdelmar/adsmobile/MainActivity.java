package luijdelmar.adsmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{

    private AdView adViewBanner;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd videoAd;
    private Button btn_interstitial;
    private Button btn_rewarded_video;
    private TextView textView_log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Stvaranje pomoću java coda*/
        MobileAds.initialize(this, "ca-app-pub-7881865912945789/1251549999");
        videoAd = MobileAds.getRewardedVideoAdInstance(this);
        videoAd.setRewardedVideoAdListener(this);

        //Banner
        adViewBanner = findViewById(R.id.adViewBanner);

        AdRequest adRequestBanner = new AdRequest.Builder()
                .addTestDevice("3CC4119728B3B6A1E560EA42B26174A7") // For emulator AdRequest.DEVICE_ID_EMULATOR
                .build();

        adViewBanner.loadAd(adRequestBanner);



        //Interstitial
        interstitialAd = new InterstitialAd(this);
                interstitialAd.setAdUnitId("ca-app-pub-7881865912945789/1251549999");

        AdRequest adRequestInterstitial = new AdRequest.Builder()
                .addTestDevice("3CC4119728B3B6A1E560EA42B26174A7") // For emulator AdRequest.DEVICE_ID_EMULATOR
                .build();

        interstitialAd.loadAd(adRequestInterstitial);

        //Gumb za interstitial ADS
        btn_interstitial = findViewById(R.id.btn_interstitial);

        btn_interstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });



        //Rewarded Video

        //Tekst za log Rewarded Video ADS
        textView_log = findViewById(R.id.textView_log);
        //Gumb za Rewarded Video ADS
        btn_rewarded_video = findViewById(R.id.btn_rewarded_video);

        videoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder()
                .addTestDevice("3CC4119728B3B6A1E560EA42B26174A7")
                .build());
        btn_rewarded_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_rewarded_video.setEnabled(false);
                if (videoAd.isLoaded()) {
                    videoAd.show();
                }
            }
        });

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        textView_log.append("An ad has loaded.\n");
        btn_rewarded_video.setEnabled(true);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        textView_log.append("An ad has opened.\n");
    }

    @Override
    public void onRewardedVideoStarted() {
        textView_log.append("An ad has started.\n");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        textView_log.append("An ad has closed.\n");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        textView_log.append(String.format(Locale.getDefault(), "You recieved %d %s!\n", rewardItem.getAmount(), rewardItem.getType()));
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        textView_log.append("An ad has caused focus to leave.\n");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        textView_log.append("An ad has fail loaded.\n");
    }

    /**Lifecycle videa  -NE FUNKCIONIRA VIDEO SA OVIM
    @Override
    public void onResume() {
        videoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        videoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        videoAd.destroy(this);
        super.onDestroy();
    }*/

}
