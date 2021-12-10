package com.example.cookieclicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private ImageView iv;
    private TextView tvCounter, tvCookieSeconds, temp;
    private Button btnUpgrade;
    private int counter = 0, cookiesPerSecondCounter = 0, upgradeCost = 10;
    private Boolean clicked = false;
    private Timer timer = new Timer();
    private ConstraintLayout layout;
    private double rand;
    private float rand2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
        tvCounter = findViewById(R.id.tvCounter);
        tvCookieSeconds = findViewById(R.id.tvCookieSeconds);
        btnUpgrade = findViewById(R.id.btnUpgrade);
        btnUpgrade.setAlpha(0.5f);
        layout = findViewById(R.id.layout);
        temp = findViewById(R.id.tvTemp);
        final ConstraintSet constraintSet = new ConstraintSet();

        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(clicked)
                        {
                            counter += cookiesPerSecondCounter;
                            tvCounter.setText(counter + " cookie(s)");

                            if(counter >= upgradeCost)
                            {
                                btnUpgrade.setClickable(true);
                                btnUpgrade.setAlpha(1f);
                            }
                            else
                            {
                                btnUpgrade.setClickable(false);
                                btnUpgrade.setAlpha(0.5f);
                            }
                        }
                    }
                });
            }
        }, 0, 1000);

        iv.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v)
            {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale));
                counter++;
                tvCounter.setText(counter + " cookie(s)");

                final TextView text = new TextView(MainActivity.this);
                text.setId(View.generateViewId());
                final ConstraintSet constraintSet = new ConstraintSet();
                text.setText("+1");

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                text.setLayoutParams(params);

                layout.addView(text);
                constraintSet.clone(layout);

                constraintSet.connect(text.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
                constraintSet.connect(text.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(text.getId(),ConstraintSet.RIGHT,layout.getId(),ConstraintSet.RIGHT);
                constraintSet.connect(text.getId(),ConstraintSet.LEFT,layout.getId(),ConstraintSet.LEFT);

                rand = (Math.random() * 0.4 + 0.30);
                rand2 = (float)(rand);
                constraintSet.setHorizontalBias(text.getId(), rand2);
                constraintSet.setVerticalBias(text.getId(), 0.10f); //lower number for +1 to go higher
                constraintSet.applyTo(layout);

                final AnimationSet set = new AnimationSet(true);

                Animation translate = new TranslateAnimation(0, 0, 200, 0);
                translate.setDuration(500);
                set.addAnimation(translate);

                final Animation alphaAnim = new AlphaAnimation(1.0f, 0.0f);
                alphaAnim.setDuration(700);
                set.addAnimation(alphaAnim);

                text.startAnimation(set);

                set.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(final Animation animation)
                    {

                        layout.post(new Runnable()
                        {
                            public void run ()
                            {
                                MainActivity.this.runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        layout.removeView(text);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });

                if(counter >= upgradeCost)
                {
                    btnUpgrade.setClickable(true);
                    btnUpgrade.setAlpha(1f);

                    final TextView desc = new TextView(MainActivity.this);
                    desc.setId(View.generateViewId());
                    final ConstraintSet constraintSet2 = new ConstraintSet();

                    ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    desc.setLayoutParams(params2);

                    layout.addView(desc);
                    constraintSet2.clone(layout);

                    constraintSet2.connect(desc.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
                    constraintSet2.connect(desc.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                    constraintSet2.connect(desc.getId(),ConstraintSet.RIGHT,layout.getId(),ConstraintSet.RIGHT);
                    constraintSet2.connect(desc.getId(),ConstraintSet.LEFT,layout.getId(),ConstraintSet.LEFT);

                    constraintSet2.setVerticalBias(desc.getId(), 0.56f);
                    constraintSet2.applyTo(layout);

                    final AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f) ;
                    fadeIn.setDuration(1200);
                    fadeIn.setFillAfter(true);

                    desc.startAnimation(fadeIn);
                }
                else
                {
                    btnUpgrade.setClickable(false);
                    btnUpgrade.setAlpha(0.5f);
                }
            }
        });

        btnUpgrade.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v)
            {
                counter = 0;
                clicked = true;

                tvCounter.setText(counter + " cookie(s)");
                cookiesPerSecondCounter++;

                //temp.setText(temp.getText() + "X ");

                tvCookieSeconds.setText(cookiesPerSecondCounter + " cookie(s) per second");

                btnUpgrade.setClickable(false);
                btnUpgrade.setAlpha(0.5f);

                try
                {
                    final ImageView g = new ImageView(MainActivity.this);
                    g.setId(View.generateViewId());
                    g.setImageResource(R.drawable.star);
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(100,100);
                    g.setLayoutParams(params);

                    layout.addView(g);
                    constraintSet.clone(layout);

                    constraintSet.connect(g.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
                    constraintSet.connect(g.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(g.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                    constraintSet.connect(g.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);

                    double x = Math.random();
                    double x2 = (double) (Math.random() * 0.05 + 0.60);

                    constraintSet.setHorizontalBias(g.getId(), (float)x);
                    Log.d("TAG", "" + x);
                    constraintSet.setVerticalBias(g.getId(), (float)x2);
                    Log.d("TAG", "" + x2);
                    constraintSet.applyTo(layout);

                    final AnimationSet set = new AnimationSet(true);

                    //fade in
                    final ScaleAnimation gAnim = new ScaleAnimation(0f, 1.0f, 0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    gAnim.setDuration(1500);
                    set.addAnimation(gAnim);

                    //spin
                    RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(1500);
                    rotate.setInterpolator(new LinearInterpolator());
                    set.addAnimation(rotate);

                    g.startAnimation(set);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}