package cn.wangtoo.luduoshou;

import android.os.Bundle;
import android.view.View;
import cn.wangtoo.luduoshou.utils.MySharedPreferences;

import com.comyou.baseactivity.BaseActivity;

public class LuBaseActivity extends BaseActivity {

	public MySharedPreferences mySharedPreferences;

	public LuApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		application = (LuApplication) getApplication();
		mySharedPreferences = MySharedPreferences.getInstance(this);
	}

	public void backAction(View v) {
		this.finish();
	}

}
