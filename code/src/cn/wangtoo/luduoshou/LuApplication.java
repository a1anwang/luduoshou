package cn.wangtoo.luduoshou;

import java.util.List;

import android.app.Application;

import cn.wangtoo.luduoshou.model.UserAccount;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.socialize.PlatformConfig;

public class LuApplication extends Application {

	private String TAG = "LuApplication";

	
   public 	UserAccount loginUser;
	
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
		LogUtils.e(TAG, "onCreate ");

		// 确保不要重复注入同一类型的对象,建议在Application类的onCreate中执行该代码。
		LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
		PlatformConfig.setSinaWeibo("693571326",
				"02b6d96f7cb806525f22fcee1183f658");
		// 新浪微博 appkey appsecret
		PlatformConfig.setQQZone("1105546812", "Yxi7KD9aTw4bX78a");
		// QQ和Qzone appid appkey
		
		
		initLoginUser();
		
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
	private void initLoginUser() {
		List<UserAccount> list= new Select().from(UserAccount.class).execute();
		if(list!=null && list.size()>0){
			loginUser=list.get(0);
		}
		
	}

}
