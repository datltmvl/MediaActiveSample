package jp.example.app.onidenwa;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static jp.example.app.onidenwa.PhoneCallingFragment.OnTerminalCallListener;
import static jp.example.app.onidenwa.PhoneCallingFragment.newInstance;
import static jp.example.app.onidenwa.PhoneRingingFragment.CUT_OFF_PHONE;
import static jp.example.app.onidenwa.PhoneRingingFragment.OnFragmentInteractionListener;
import static jp.example.app.onidenwa.PhoneRingingFragment.PICK_UP_PHONE;
import static jp.example.app.onidenwa.PhoneRingingFragment.newInstance;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhoneActivity extends AppCompatActivity
        implements OnFragmentInteractionListener, OnTerminalCallListener {

    public static final String CALLER_AVATAR = "CALLER_AVATAR";
    public static final String CALLER_VOICE = "CALLER_VOICE";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CALLER_NAME = "CALLER_NAME";

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        setContentView(R.layout.activity_phone);
        // Create new fragment and transaction
        Fragment newFragment = newInstance(getIntent().getIntExtra(CALLER_AVATAR,
                R.drawable.oni_iukoto_calling_20170523), getIntent().getStringExtra(CALLER_NAME).
                concat("\n").concat(getIntent().getStringExtra(PHONE_NUMBER)));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onFragmentInteraction(int action) {
        if (action == CUT_OFF_PHONE) {
            finish();
        } else if (action == PICK_UP_PHONE) {
            // get info from previous screen.
            int avatar = getIntent().getIntExtra(CALLER_AVATAR,
                    R.drawable.oni_iukoto_calling_20170523);
            int voice = getIntent().getIntExtra(CALLER_VOICE,
                    R.raw.oni_iukoto_jp_old_talking_20170524);

            getSupportFragmentManager().popBackStack();
            // Create new fragment and transaction
            Fragment newFragment = newInstance(voice, avatar,
                    getIntent().getStringExtra(CALLER_NAME));
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onTerminalCall(Uri uri) {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        finish();
    }
}
