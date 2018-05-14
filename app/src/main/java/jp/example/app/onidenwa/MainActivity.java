package jp.example.app.onidenwa;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import jp.example.app.onidenwa.ItemFragment.OnListFragmentInteractionListener;
import jp.example.app.onidenwa.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener, OnListFragmentInteractionListener {

    // count down time mode in millisecond
    private static final int COUNT_10 = 10000;
    private static final int COUNT_30 = 30000;
    private static final int COUNT_50 = 50000;

    private ImageButton mTimer;
    private int mTimerCount = -1;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.icon_t_01));
        getSupportActionBar().setTitle(null);

        // setup show timer dialog
        mTimer = toolbar.findViewById(R.id.img_timer);
        mTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTimer.isSelected()) {
                    showTimerSelectionDialog();
                } else {
                    mTimer.setSelected(false);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.icon_menu_01);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create new fragment and transaction
        Fragment newFragment = ItemFragment.newInstance(10);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.tablayout, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        if (!mTimer.isSelected()) {
            makeCall(item);
        } else {
            showWaitingDialog(item);
        }
    }

    private void makeCall(DummyContent.DummyItem item) {
        Intent intent = new Intent(this, PhoneActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(PhoneActivity.CALLER_AVATAR, item.avatar);
        bundle.putInt(PhoneActivity.CALLER_VOICE, item.content);
        bundle.putString(PhoneActivity.PHONE_NUMBER, item.tel);
        bundle.putString(PhoneActivity.CALLER_NAME, item.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Show dialog choose countdown time to make a call.
     */
    private void showTimerSelectionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.popup_timer);

        dialog.findViewById(R.id.btn_timer_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.setSelected(true);
                mTimerCount = COUNT_10;
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_timer_30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.setSelected(true);
                mTimerCount = COUNT_30;
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_timer_50).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.setSelected(true);
                mTimerCount = COUNT_50;
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showWaitingDialog(final DummyContent.DummyItem item) {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup_wait_call);

        // setup notification time count down.
        TextView textView = dialog.findViewById(R.id.tv_title);
        textView.setText(getString(R.string.string_waiting_noti, mTimerCount / 1000));

        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeCall(item);
                dialog.dismiss();
            }
        }, mTimerCount);

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerCount = -1;
                mTimer.setSelected(false);
                mHandler.removeCallbacksAndMessages(null);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
