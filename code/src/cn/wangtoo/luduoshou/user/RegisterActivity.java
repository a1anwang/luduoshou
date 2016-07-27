package cn.wangtoo.luduoshou.user;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.socialize.utils.Log;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.wangtoo.luduoshou.LuBaseActivity;
import cn.wangtoo.luduoshou.R;
import cn.wangtoo.luduoshou.utils.MConfig;
import cn.wangtoo.luduoshou.utils.MD5Util;
import cn.wangtoo.luduoshou.utils.MUtil;
import cn.wangtoo.luduoshou.utils.ToastUtils;
import cn.wangtoo.luduoshou.views.AuthButton;
import cn.wangtoo.luduoshou.views.MyProgressDialog;

public class RegisterActivity extends LuBaseActivity {

	EditText edit_phone, edit_pwd, edit_code;

	AuthButton btn_get_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		 
		
		edit_code = (EditText) findViewById(R.id.edit_code);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		edit_pwd = (EditText) findViewById(R.id.edit_pwd);

		btn_get_code = (AuthButton) findViewById(R.id.btn_get_code);
		btn_get_code.setNormalStyle(getResources().getColor(R.color.blue),
				Color.WHITE, "获取验证码");

		btn_get_code.setCountdownStyle(
				getResources().getColor(R.color.light_gray), Color.DKGRAY);
		btn_get_code.setToNormal();
	}

	public void getCodeAction(View v) {

		String phoneNum = edit_phone.getText().toString();

		if (!MUtil.isMobileNum(edit_phone.getText().toString())) {

			ToastUtils.showToast(this, getString(R.string.phonenum_is_wrong),
					2000);
			return;
		}

		queryAuthCode(phoneNum);

	}

	public void registerAction(View v) {

		final String phoneNum = edit_phone.getText().toString();

		if (!MUtil.isMobileNum(phoneNum)) {
			ToastUtils.showToast(this, getString(R.string.phonenum_is_wrong),
					2000);
			return;
		}
		String pwd = edit_pwd.getText().toString();

		if (pwd == null || pwd.length() < 6) {
			ToastUtils.showToast(this, getString(R.string.pwd_too_short), 2000);

			return;
		}

		String code = edit_code.getText().toString();

		if (code.equals("")) {
			ToastUtils.showToast(this, getString(R.string.hint_code), 2000);

			return;
		}

		final MyProgressDialog myProgressDialog = new MyProgressDialog(this);
		myProgressDialog.show();
		OkHttpClient okHttpClient = new OkHttpClient();
		String url = MConfig.ServerIP + "register.php";

		RequestBody formBody = new FormBody.Builder().add("phoneNum", phoneNum)
				.add("pwd", MD5Util.md5(pwd)).add("authcode", code).build();

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
						myProgressDialog.dismiss();
						try {
							JSONObject jsonObject = new JSONObject(responseStr);

							int err = jsonObject.getInt("err");
							if (err == 0) {
								// 成功
								ToastUtils.showToast(RegisterActivity.this,
										String.format("注册成功，请重新登陆", phoneNum),
										2000);
								RegisterActivity.this.finish();
							} else if (err == 1) {
								// 缺少参数

							} else if (err == 2) {

								ToastUtils.showToast(RegisterActivity.this,
										String.format("手机号码已被注册", phoneNum),
										2000);
							} else if (err == 3) {

								ToastUtils
										.showToast(RegisterActivity.this,
												String.format("验证码不正确",
														phoneNum), 2000);
							} else if (err == 4) {

								ToastUtils
										.showToast(RegisterActivity.this,
												String.format("验证码已过期",
														phoneNum), 2000);
							} else {
								ToastUtils.showToast(RegisterActivity.this,
										"注册失败，请重试 ", 2000);
								
							}
						} catch (JSONException e) {
							ToastUtils.showToast(RegisterActivity.this,
									"注册失败，请重试 ", 2000);
						}

					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ToastUtils.showToast(RegisterActivity.this,
								"注册失败，请重试 ", 2000);
						myProgressDialog.dismiss();
					}
				});

			}
		});
	}

	private void queryAuthCode(final String phoneNum) {

		final MyProgressDialog myProgressDialog = new MyProgressDialog(this);
		myProgressDialog.show();

		OkHttpClient okHttpClient = new OkHttpClient();
		String url = MConfig.ServerIP + "getauthcode.php";

		RequestBody formBody = new FormBody.Builder()
				.add("targetPhoneNum", phoneNum).add("type", "1").build();

		Request request = new Request.Builder().url(url).post(formBody).build();

		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response response)
					throws IOException {
				final String responseStr= response.body().string();
				Log.e("", "responseStr:"+responseStr);
				runOnUiThread(new Runnable() {

					@Override
					public void run()  	{
						myProgressDialog.dismiss();
						try {
							JSONObject jsonObject = new JSONObject(responseStr);

							int err = jsonObject.getInt("err");
							if (err == 0) {
								// 成功
								ToastUtils.showToast(RegisterActivity.this,
										String.format("验证码已发送到%s", phoneNum),
										2000);
								btn_get_code.setToCountdown();
							} else {
								ToastUtils.showToast(RegisterActivity.this,
										"获取失败，请重试 ", 2000);
								
							}
						} catch (JSONException e) {
							ToastUtils.showToast(RegisterActivity.this,
									"获取失败，请重试 ", 2000);
						}

					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ToastUtils.showToast(RegisterActivity.this,
								"获取失败，请重试 ", 2000);
						myProgressDialog.dismiss();
					}
				});

			}
		});

	}
}
