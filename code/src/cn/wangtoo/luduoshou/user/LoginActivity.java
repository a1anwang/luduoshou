package cn.wangtoo.luduoshou.user;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import cn.wangtoo.luduoshou.LuBaseActivity;
import cn.wangtoo.luduoshou.MainActivity;
import cn.wangtoo.luduoshou.R;
import cn.wangtoo.luduoshou.model.UserAccount;
import cn.wangtoo.luduoshou.utils.MConfig;
import cn.wangtoo.luduoshou.utils.MD5Util;
import cn.wangtoo.luduoshou.utils.MUtil;
import cn.wangtoo.luduoshou.utils.ToastUtils;
import cn.wangtoo.luduoshou.views.MyProgressDialog;

import com.activeandroid.query.Delete;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

public class LoginActivity extends LuBaseActivity {

	UMShareAPI mShareAPI;

	EditText edit_phone, edit_pwd;

	MyProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mShareAPI = UMShareAPI.get(this);
		Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
		
		edit_phone=(EditText) findViewById(R.id.edit_phone);
		edit_pwd=(EditText) findViewById(R.id.edit_pwd);
		
		
	}

	public void qqAction(View v) {
		SHARE_MEDIA platform = SHARE_MEDIA.QQ;
		mShareAPI.doOauthVerify(this, platform, umAuthListener);
	}

	public void sinaAction(View v) {
		SHARE_MEDIA platform = SHARE_MEDIA.SINA;
		mShareAPI.doOauthVerify(this, platform, umAuthListener);

	}

	public void forgetPwdAction(View v) {
		startActivity(new Intent(this, ForgetPwdActivity.class));
	}

	public void registerAction(View v) {
		startActivity(new Intent(this, RegisterActivity.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}

	private void showLoginProgressDialog() {
		if (myProgressDialog == null) {
			myProgressDialog = new MyProgressDialog(this);

			myProgressDialog.setTitle("登录中");
		}

		myProgressDialog.show();
	}

	private void dismissProgressDialog() {
		if (myProgressDialog != null) {
			myProgressDialog.dismiss();
		}
	}

	private UMAuthListener umAuthListener = new UMAuthListener() {
		@Override
		public void onComplete(SHARE_MEDIA platform, int action,
				Map<String, String> data) {
			Log.e("", "登陆成功 " + action + " " + data.toString());

			// 新浪微博 登陆成功 0 {com.sina.weibo.intent.extra.NICK_NAME=为谁沉沦小童鞋,
			// access_token=2.00iwUCWF0SaJwk83ed3155a9kkiVKC,
			// com.sina.weibo.intent.extra.USER_ICON=null, uid=5054102528,
			// userName=为谁沉沦小童鞋, _weibo_appPackage=com.sina.weibo,
			// expires_in=157679999, _weibo_transaction=1468844993127,
			// refresh_token=2.00iwUCWF0SaJwk2a5e79128elyvWRD}
			// qq 登陆成功 0 {access_token=16F39FE268383A0FEE4BEAA1CE6770D6,
			// page_type=, appid=, pfkey=a2a41ee04cadb10977e62257fe60e76c,
			// uid=6821FF21EC2144905736BAF0D3918A5D, auth_time=, sendinstall=,
			// pf=desktop_m_qq-10000144-android-2002-, expires_in=7776000,
			// pay_token=45231B45DFC4CDFE184E6680E1B0F0A3, ret=0,
			// openid=6821FF21EC2144905736BAF0D3918A5D}
			// qq登陆之后获取
			// 昵称等信息：http://wiki.open.qq.com/wiki/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF

			// 创建CommUser前必须先初始化CommunitySDK

			showLoginProgressDialog();

			OkHttpClient okHttpClient = new OkHttpClient();
			RequestBody formBody = null;
			String url = MConfig.ServerIP + "login.php";

			if (platform == SHARE_MEDIA.QQ) {
				formBody = new FormBody.Builder()
						.add("QQ_id", data.get("openid"))
						.add("accountType", "QQ").build();

			} else if (platform == SHARE_MEDIA.SINA) {
				formBody = new FormBody.Builder()
						.add("WeiBo_id", data.get("uid"))
						.add("accountType", "WeiBo").build();
			}

			Request request = new Request.Builder().url(url).post(formBody)
					.build();

			okHttpClient.newCall(request).enqueue(new Callback() {

				@Override
				public void onResponse(Call arg0, final Response response)
						throws IOException {
					final String responseStr= response.body().string();
					Log.e("", "responseStr:"+responseStr);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								JSONObject jsonObject = new JSONObject(responseStr);

								int err = jsonObject.getInt("err");
								
								Log.e("", " err:"+err);
								
								if (err == 0) {
									// 登陆成功
									

									jsonObject = jsonObject
											.getJSONObject("data");

									UserAccount userAccount = new UserAccount();

									userAccount.uid = jsonObject.getInt("uid");
									userAccount.nickname = jsonObject
											.getString("nickname");
									userAccount.phoneNum = jsonObject
											.getString("phoneNum");
									userAccount.QQ_id = jsonObject
											.getString("QQ_id");
									userAccount.WeiBo_id = jsonObject
											.getString("WeiBo_id");
									userAccount.accountType = jsonObject
											.getString("accountType");
									userAccount.headImageURL = jsonObject
											.getString("headImageURL");

									loginToUmeng(userAccount);

								} else if (err == 1) {
									// 缺少参数
									dismissProgressDialog();
								} else if (err == 2) {
									ToastUtils.showToast(LoginActivity.this,
											"帐号或者密码不正确，请重新输入", 2000);
									dismissProgressDialog();
								} else {
									ToastUtils.showToast(LoginActivity.this,
											"登陆失败，请重试 ", 2000);
									dismissProgressDialog();
								}
							} catch (JSONException e) {
								
								Log.e("", " "+e.toString());
								
								ToastUtils.showToast(LoginActivity.this,
										"登陆失败，请重试 ", 2000);
								dismissProgressDialog();
							}

						}
					});

				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							ToastUtils.showToast(LoginActivity.this, "登陆失败，请重试 ", 2000);
							dismissProgressDialog();
						}
					});
					
				
				}
			});

		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Log.e("", "登陆onError " + action + " ");
			dismissProgressDialog();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			// Toast.makeText(getApplicationContext(), "Authorize cancel",
			// Toast.LENGTH_SHORT).show();
		}
	};

	public void phoneLoginAction(View v) {

		String phoneNum = edit_phone.getText().toString();

		if (!MUtil.isMobileNum(phoneNum)) {

			ToastUtils.showToast(this, getString(R.string.phonenum_is_wrong),
					2000);
			return;
		}

		String pwd = edit_pwd.getText().toString();
		if (TextUtils.isEmpty(pwd)) {
			ToastUtils.showToast(this, getString(R.string.pwd_is_empty), 2000);
			return;
		}

		showLoginProgressDialog();

		OkHttpClient okHttpClient = new OkHttpClient();
		String url = MConfig.ServerIP + "login.php";

		RequestBody formBody = new FormBody.Builder().add("phoneNum", phoneNum)
				.add("pwd", MD5Util.md5(pwd)).add("accountType", "Phone").build();

		Request request = new Request.Builder().url(url).post(formBody).build();

		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response response)
					throws IOException {
				final String responseStr= response.body().string();
				Log.e("", "responseStr:"+responseStr);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							JSONObject jsonObject = new JSONObject(responseStr);

							int err = jsonObject.getInt("err");
							if (err == 0) {
								// 登陆成功
								mySharedPreferences.saveIsLogined(true);

								jsonObject = jsonObject.getJSONObject("data");

								UserAccount userAccount = new UserAccount();

								userAccount.uid = jsonObject.getInt("uid");
								userAccount.nickname = jsonObject
										.getString("nickname");
								userAccount.phoneNum = jsonObject
										.getString("phoneNum");
								userAccount.QQ_id = jsonObject
										.getString("QQ_id");
								userAccount.WeiBo_id = jsonObject
										.getString("WeiBo_id");
								userAccount.accountType = jsonObject
										.getString("accountType");
								userAccount.headImageURL = jsonObject
										.getString("headImageURL");

								loginToUmeng(userAccount);

							} else if (err == 1) {
								// 缺少参数
								ToastUtils.showToast(LoginActivity.this,
										"登陆失败，请重试  1", 2000);
								dismissProgressDialog();
							} else if (err == 2) {
								ToastUtils.showToast(LoginActivity.this,
										"帐号或者密码不正确，请重新输入", 2000);dismissProgressDialog();
							} else {
								ToastUtils.showToast(LoginActivity.this,
										"登陆失败，请重试  2", 2000);
								dismissProgressDialog();
							}
						} catch (JSONException e) {
							
							Log.e("", " "+e.toString());
							
							ToastUtils.showToast(LoginActivity.this,
									"登陆失败，请重试 3", 2000);
							dismissProgressDialog();
						}

					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ToastUtils.showToast(LoginActivity.this, "登陆失败，请重试  4", 2000);
						dismissProgressDialog();
					}
				});
				
			 
			}
		});

	}

	private void loginToUmeng(final UserAccount userAccount) {

		CommunitySDK sdk = CommunityFactory.getCommSDK(LoginActivity.this);
		CommUser user = new CommUser();
		user.name = userAccount.nickname;
		user.id = "" + userAccount.uid;
		sdk.loginToUmengServerBySelfAccount(this, user, new LoginListener() {

			@Override
			public void onStart() {
				 

			}
			@Override
			public void onComplete(int stCode, CommUser commUser) {
				if (ErrorCode.NO_ERROR == stCode) {
					// 登录成功，可以打开社区，也可以进行其他的操作，开发者自己定义
					dismissProgressDialog();
					new Delete().from(UserAccount.class).execute();

					userAccount.save();
					application.loginUser=userAccount;
					mySharedPreferences.saveIsLogined(true);
					
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					LoginActivity.this.finish();
				} else {
					
					Log.e("", " ");
					
					ToastUtils.showToast(LoginActivity.this, "登陆失败，请重试  5", 2000);
					dismissProgressDialog();
				}
			}
		});

	}
}
