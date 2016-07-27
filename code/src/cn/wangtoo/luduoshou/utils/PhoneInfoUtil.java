package cn.wangtoo.luduoshou.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneInfoUtil {

	public static String getIMEI(Context context) {
		String imei = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

		return imei;
	}

	public static String getPhoneModel( ) {
		String model = android.os.Build.MODEL;

		return model.toUpperCase();
	}

	public static String getAndroidVersion( ) {
		String verson = android.os.Build.VERSION.RELEASE;

		return verson;
	}

	public static String getBaseband_Ver() {
		String Version = "";
		try {
			Class cl = Class.forName("android.os.SystemProperties");
			Object invoker = cl.newInstance();
			Method m = cl.getMethod("get", new Class[] { String.class,
					String.class });
			Object result = m.invoke(invoker, new Object[] {
					"gsm.version.baseband", "no message" });
			 
			Version = (String) result;
		} catch (Exception e) {
		}
		return Version;
	}
}
