package com.angelmxc.test.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

public class AnimacionesActivity extends FragmentActivity {
    public static final int duracion=2*1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animaciones);

        TextView texto=(TextView)findViewById(R.id.textView);
        Button btn=(Button)findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonDeAbajo bda=new BotonDeAbajo();
                bda.show(getSupportFragmentManager(),"BotonDeAbajo");

            }
        });

        RotateAnimation animR = new RotateAnimation(0,720,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animR.setDuration(duracion);
        animR.setFillAfter(true);

        Animation animS = new ScaleAnimation(
                1f, 3f, // Start and end values for the X axis scaling
                1f, 3f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        animS.setFillAfter(true); // Needed to keep the result of the animation
        animS.setDuration(duracion);

        AnimationSet animationSet = new AnimationSet(true);

        animationSet.addAnimation(animS);
        animationSet.addAnimation(animR);
        animationSet.setFillAfter(true);

        texto.startAnimation(animationSet);

    }
}
