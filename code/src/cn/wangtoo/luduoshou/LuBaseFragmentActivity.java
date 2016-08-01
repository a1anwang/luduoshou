package cn.wangtoo.luduoshou;

import android.os.Bundle;
import android.view.View;
import cn.wangtoo.luduoshou.utils.MySharedPreferences;

import com.comyou.baseactivity.BaseActivity;
import com.comyou.baseactivity.FragmentBaseActivity;

public class LuBaseFragmentActivity extends FragmentBaseActivity {

	public MySharedPreferences mySharedPreferences;

	public LuApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setNeedHideStatusBar(false);
		setStatusBarColor(getResources().getColor(R.color.blue));
		super.onCreate(savedInstanceState);
		application = (LuApplication) getApplication();
		mySharedPreferences = MySharedPreferences.getInstance(this);
	}

	public void backAction(View v) {
		this.finish();
	}

}
