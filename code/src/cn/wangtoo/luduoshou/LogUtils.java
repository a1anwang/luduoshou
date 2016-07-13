package cn.wangtoo.luduoshou;
import android.util.Log;


public class LogUtils {

	public static boolean Open=true;
	
	public static void e(String TAG,String msg){
		if(!Open)  return;
		
		Log.e(TAG, msg);
	}
	
}
