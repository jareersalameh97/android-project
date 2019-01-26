package com.example.aloos.finalproject2.activites;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
//import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class Splash extends AwesomeSplash {



    @Override
    public void initSplash(ConfigSplash configSplash) {
        /* you don't have to override every property */

        //Customize Circular Reveal
      configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
      //  configSplash.setAnimCircularRevealDuration(2000); //int ms
      //  configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
      //  configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
       configSplash.setLogoSplash(R.drawable.ss); //or any other drawable
       // configSplash.setAnimLogoSplashDuration(2000); //int ms
       // configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
      // configSplash.setPathSplash(SyncStateContract.Constants.ACCOUNT_NAME); //set path String
        //configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
       // configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
       // configSplash.setAnimPathStrokeDrawingDuration(3000);
      //  configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorPrimaryDark); //any color you want form colors.xml
   //     configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.colorPrimaryDark); //path object filling color



        //Customize Title
       configSplash.setTitleSplash("HuQuizzes");
        configSplash.setTitleTextColor(R.color.colorPrimary);
     //  configSplash.setTitleTextSize(30f); //float value
     //   configSplash.setAnimTitleDuration(3000);
     //   configSplash.setAnimTitleTechnique(Techniques.FlipInX);
     //   configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {
                Intent i=new Intent(Splash.this,LogInActivity.class);
                startActivity(i);
                 finish();


    }
}
