package cn.wangtoo.luduoshou.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class MUtil {
	public static long formatTimeSringToLong(String strTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date;
		try {
			date = formatter.parse(strTime);
			if (date == null) {
				return 0;
			} else {
				long currentTime = formatTimeDateToLong(date); // date类型转成long类型
				return currentTime;
			}
		} catch (ParseException e) {

		}
		// String类型转成date类型
		return 0;

	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long formatTimeDateToLong(Date date) {
		return date.getTime();
	}

	public static String formatTimeLongToString(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return sDateFormat.format(time);
	}

	// 获取年号
	public static int formatTimeYear(long time) {
		int n = 0;
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String str = sDateFormat.format(time);
		n = Integer.parseInt(str.substring(0, 4));

		return n;
	}

	// 获取当天几号
	public static int formatTimeDay(long time) {
		int n = 0;
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String str = sDateFormat.format(time);
		n = Integer.parseInt(str.substring(8, 10));

		return n;
	}

	public static String formatTimeYMDHMSF(long time) {

		Timestamp ts = new Timestamp(time);

		return ts.toString();
	}

	public static String formatTimeMD(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd");
		String formatTime = sDateFormat.format(time);

		return formatTime;
	}

	public static String formatTimeD(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd");
		String formatTime = sDateFormat.format(time);

		return formatTime;
	}

	public static String formatTimeYMD(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formatTime = sDateFormat.format(time);

		return formatTime;
	}

	public static String formatTimeYM(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月");
		String formatTime = sDateFormat.format(time);

		return formatTime;
	}

	public static String[] formatTimeArray(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		String formatTime = sDateFormat.format(time);

		return formatTime.split(" ");
	}

	public static String formatHMS(long million) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HHmmss");
		String time = sDateFormat.format(million);

		return time;
	}

	public static String getLungVolumnFileName(String account, int memberId,
			int index, String measureDate) {
		String formatTime = measureDate.replace("-", "");

		formatTime = formatTime.replace(":", "");
		formatTime = formatTime.replace(" ", "");

		return account + "01" + formatTime + memberId + "" + index + ".wav";

	}

	public static String getHeartVolumnFileName(String account, int memberId,
			String measureDate) {
		String formatTime = measureDate.replace("-", "");

		formatTime = formatTime.replace(":", "");
		formatTime = formatTime.replace(" ", "");

		return account + "02" + formatTime + memberId + ".wav";

	}

	/**
	 * * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNum(String mobilestring) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][3458]\\d{9}";// "[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobilestring)) {
			return false;
		} else
			return mobilestring.matches(telRegex);
	}

	/**
	 * 有网没网
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		if (isWifiConnected(context)) {

			return true;
		}
		if (isMobileConnected(context)) {

			return true;
		}
		return false;
	}

	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isConnected();
			}
		}
		return false;
	}

	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isConnected();
			}
		}
		return false;
	}

	/**
	 * 软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// Get the package info
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			// Log.e(THIS_FILE, "Exception", e);
		}
		return versionName;
	}

	/**
	 * 检测sdk
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean CheckIsSamsung() {
		String brand = android.os.Build.BRAND;
		if (brand.toLowerCase().equals("samsung")) {
			return true;
		}
		return false;
	}

	public static String getSecretPhone(String phone) {
		if (phone.length() >= 11) {

			phone = phone.substring(0, 3) + "*****" + phone.substring(8, 11);

		}
		return phone;
	}

	// 从字节数组到十六进制字符串转换
	public static String Bytes2HexString(byte[] b) {
		final byte[] hex = "0123456789ABCDEF".getBytes();
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 
	 * @param content
	 * @param path
	 *            填null 的话，会默认放到 "/sdcard/AlanDebug/"下
	 * @param fileName
	 */
	public static void saveFile(String content, String path, String fileName) {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				if (path == null) {
					path = "/sdcard/AlanDebug/";
				}
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName,
						true);
				fos.write(content.getBytes());
				fos.close();

			}

		} catch (Exception e) {
			Log.e("", "an error occured while writing file...", e);
		}

	}

	public static String getWeekByTimeStr(String timestr) {
		int year = Integer.parseInt(timestr.substring(0, 4));
		int month = Integer.parseInt(timestr.substring(5, 7));
		int day = Integer.parseInt(timestr.substring(8, 10));

		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);

		String week = "";
		int weekIndex = c.get(Calendar.DAY_OF_WEEK);

		switch (weekIndex) {
		case 1:
			week = "周日";
			break;
		case 2:
			week = "周一";
			break;
		case 3:
			week = "周二";
			break;
		case 4:
			week = "周三";
			break;
		case 5:
			week = "周四";
			break;
		case 6:
			week = "周五";
			break;
		case 7:
			week = "周六";
			break;
		}
		return week;
	}

	public static boolean isDevicePluged(Context context) {
		AudioManager localAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);

		return localAudioManager.isWiredHeadsetOn();
	}

	 

	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static String File2HexString(String filepath) {
		return Bytes2HexString(File2byte(filepath));
	}

	public static String getPath(String path) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getAbsolutePath();
	}

	public static File getFile(String path) {
		File f = new File(path);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	public static String getHeartVolumnWAVPath() {
		return getPath(Environment.getExternalStorageDirectory() + "/"
				+ "TingYinBao/HeartVolumn/");
	}

	public static String getLungVolumnWAVPath() {
		return getPath(Environment.getExternalStorageDirectory() + "/"
				+ "TingYinBao/LungVolumn/");
	}

	public static void deleteFile(String filePath) {
		if (filePath == null || filePath.length() < 1) {
			return;
		}
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}

	}

	public static boolean fileExists(String filePath) {
		if (filePath == null || filePath.length() < 1) {
			return false;
		}

		File f = new File(filePath);
		return f.exists();

	}

	public void saveBitmap(Bitmap mBitmap, String filepath) {
		File f = new File(filepath);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param value
	 * @return 输出最小值
	 */
	public static int getMinNumber(int... value) {
		int min = -1;

		// 输入参数不是null,注意：没有给定参数时候，value!=null
		if (value != null) {
			// 首先判断这个方法是否给定了参数，如果没有给，输出提示信息
			if (value.length == 0) {

			}
			// 如果给了参数，那么输出全部
			else {
				min = value[0];

				// 需要注意的是，value是一个数组，取值时候用遍历数组的方式进行
				for (int i = 0; i < value.length; i++) {
					if (value[i] < min) {
						min = value[i];
					}
				}

			}
		} else {

		}

		return min;
	}

	public static String reSetHeadImageURL(String oldURL) {
		String newURL;
		int position = oldURL.indexOf("?");
		if (position > 0) {
			newURL = oldURL.substring(0, position);

			newURL = newURL + "?" + System.currentTimeMillis();

		} else {
			newURL = oldURL + "?" + System.currentTimeMillis();
		}

		Log.e("", " new String :" + newURL);

		return newURL;
	}

}
