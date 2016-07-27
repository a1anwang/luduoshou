package cn.wangtoo.luduoshou;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import cn.wangtoo.luduoshou.user.LoginActivity;

public class LaunchActivity extends LuBaseActivity implements AnimationListener {
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setNeedHideStatusBar(true);
		setStatusBarColor(Color.TRANSPARENT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		 
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

		Resources res = getResources();

		Configuration config = new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config, res.getDisplayMetrics());

		Animation launchAnimation = AnimationUtils.loadAnimation(this,
				R.anim.launch_animation);
		launchAnimation.setFillEnabled(true);
		launchAnimation.setFillAfter(true);
		layout.setAnimation(launchAnimation);
		launchAnimation.setAnimationListener(this);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().build());

	 
		
		
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
//		if (mySharedPreferences.getIsFirstUse()) {
//			mySharedPreferences.saveIsFirstUse(false);
//			Intent intent = new Intent(this, GuideActivity.class);
//			startActivity(intent);
//		} else {
			boolean logined = mySharedPreferences.getIsLogined();
			if (logined) {
				startActivity(new Intent(this, MainActivity.class));
			} else {
				startActivity(new Intent(this, LoginActivity.class));
			}
//		}
		this.finish();
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {

	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}

}
