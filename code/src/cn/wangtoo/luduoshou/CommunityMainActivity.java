package cn.wangtoo.luduoshou;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.commm.ui.fragments.CommunityMainFragment;

public class CommunityMainActivity extends FragmentActivity{
	   CommunitySDK mCommSDK = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCommSDK = CommunityFactory.getCommSDK(this);
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_community);
		
		 CommunityMainFragment fragment = new CommunityMainFragment();
	        fragment.setBackButtonVisibility(View.GONE);
	    getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
	}
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);     
	    setIntent(intent);
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
}
