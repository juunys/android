package com.juny.repartilhe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private ViewPagerAdapter viewPagerAdapter;
    private int dotscount;
    private ImageView[] dots;

    FirebaseAuth auth;


   /* @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {

        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel = findViewById(R.id.SliderDots);

        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        // Add the dots images
        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.login_dot_noactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        // Set the first dot as the Active
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.login_dot_active));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.login_dot_noactive));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.login_dot_active));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void signupClicked(View view) {

        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.GONE);
    }

    public void fbClicked(View view) {
    }
}
