package cn.wangtoo.luduoshou;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.wangtoo.luduoshou.utils.MConfig;
import cn.wangtoo.luduoshou.utils.ToastUtils;
import cn.wangtoo.luduoshou.views.MyProgressDialog;

import com.bumptech.glide.Glide;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.commm.ui.fragments.CommunityMainFragment;
import com.umeng.socialize.utils.Log;

public class MainActivity extends LuBaseActivity {
	CommunitySDK mCommSDK = null;

	ImageView image_head;
	TextView tv_nickname;
	Toolbar toolbar;
	LinearLayout layout_info;

	MenuItem signmenuItem;

	Handler handler = new Handler();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Glide.with(this).load(application.loginUser.headImageURL)
				.placeholder(R.drawable.umeng_comm_male).into(image_head);

		tv_nickname.setText(application.loginUser.nickname);
		LogUtils.e("", " 签到天数:" + application.loginUser.signinDays);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacks(syncrunnable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mCommSDK = CommunityFactory.getCommSDK(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		image_head = (ImageView) findViewById(R.id.image_head);

		tv_nickname = (TextView) findViewById(R.id.tv_nickname);

		CommunityMainFragment fragment = new CommunityMainFragment();
		fragment.setBackButtonVisibility(View.GONE);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, fragment).commit();

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		toolbar.setTitle("");

		setSupportActionBar(toolbar);

		toolbar.setOnMenuItemClickListener(onMenuItemClick);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
				mDrawerLayout, toolbar, R.string.drawer_open,
				R.string.drawer_close);
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		layout_info = (LinearLayout) findViewById(R.id.layout_info);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		signmenuItem = menu.findItem(R.id.action_signin);
		updateSignState();

		syncSignState();

		return true;
	}

	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			switch (menuItem.getItemId()) {
			case R.id.action_signin:
				signAction();

				break;
			case R.id.action_share:
				break;
			}
			return true;
		}
	};

	private void updateSignState() {
		if (signmenuItem != null) {

			if (mySharedPreferences.getTodaySigned()) {
				signmenuItem.setIcon(R.drawable.icon_sign_signed);
			} else {
				signmenuItem.setIcon(R.drawable.icon_sign_normal);
			}

		}
	}

	private void syncSignState() {
		handler.removeCallbacks(syncrunnable);
		handler.post(syncrunnable);

	}

	Runnable syncrunnable = new Runnable() {

		@Override
		public void run() {
			final OkHttpClient client = new OkHttpClient();
			String url = MConfig.ServerIP + "SyncSignState.php?uid="
					+ application.loginUser.uid;
			Request request = new Request.Builder().url(url).build();
			client.newCall(request).enqueue(new Callback() {

				@Override
				public void onResponse(Call arg0, Response response)
						throws IOException {
					final String responseStr = response.body().string();
					LogUtils.e("", " responseStr:" + responseStr);
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(responseStr);
						int err = jsonObject.getInt("err");

						if (err == 0) {
							jsonObject = jsonObject.getJSONObject("data");

							boolean todaySigned = jsonObject
									.getBoolean("todaySigned");
							mySharedPreferences.saveTodaySigned(todaySigned);

							int signinDays = jsonObject.getInt("signinDays");

							application.loginUser.signinDays = signinDays;

							application.loginUser.save();

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									updateSignState();
								}
							});

						} else {
							handler.postDelayed(syncrunnable, 5000);
						}

					} catch (JSONException e) {
						handler.postDelayed(syncrunnable, 5000);
					}

				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					handler.postDelayed(syncrunnable, 5000);

				}
			});
		}
	};

	protected void signAction() {

		if (mySharedPreferences.getTodaySigned())
			return;

		final MyProgressDialog myProgressDialog = new MyProgressDialog(this);

		myProgressDialog.show();

		OkHttpClient okHttpClient = new OkHttpClient();
		String url = MConfig.ServerIP + "signin.php";

		RequestBody formBody = new FormBody.Builder().add("uid",
				"" + application.loginUser.uid).build();

		Request request = new Request.Builder().url(url).post(formBody).build();

		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response response)
					throws IOException {
				final String responseStr = response.body().string();
				Log.e("", "responseStr:" + responseStr);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						myProgressDialog.dismiss();
						try {
							JSONObject jsonObject = new JSONObject(responseStr);

							int err = jsonObject.getInt("err");
							if (err == 0 || err == 3) {
								// 签到成功
								jsonObject = jsonObject.getJSONObject("data");

								mySharedPreferences.saveTodaySigned(true);

								int signinDays = jsonObject
										.getInt("signinDays");

								application.loginUser.signinDays = signinDays;

								application.loginUser.save();

								ToastUtils.showToast(MainActivity.this, "签到成功",
										2000);
								updateSignState();
							} else {
								ToastUtils.showToast(MainActivity.this,
										"签到失败，请重试   ", 2000);

							}
						} catch (JSONException e) {

							Log.e("", " " + e.toString());

							ToastUtils.showToast(MainActivity.this,
									"签到失败，请重试   ", 2000);
							myProgressDialog.dismiss();
						}

					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ToastUtils.showToast(MainActivity.this, "签到失败，请重试   ",
								2000);
						myProgressDialog.dismiss();
					}
				});

			}
		});

	}
}
