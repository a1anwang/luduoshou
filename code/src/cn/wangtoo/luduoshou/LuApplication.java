package cn.wangtoo.luduoshou;

import android.app.Application;

public class LuApplication extends Application{

	private   String TAG="LuApplication";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtils.e(TAG, "onCreate ");
		
		// 确保不要重复注入同一类型的对象,建议在Application类的onCreate中执行该代码。
		//LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl()) ;
	}
	
	
}
