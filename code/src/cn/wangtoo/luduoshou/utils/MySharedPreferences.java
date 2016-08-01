package cn.wangtoo.luduoshou.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MySharedPreferences {
	private static Context mContext;
	private SharedPreferences.Editor editor;
	private SharedPreferences preferences;

	private final String PREFERENCE_NAME =MySharedPreferences.class.getName() ;

	private static class MySharedPreferencesHold {
		/**
		 * 鍗曚緥瀵硅薄瀹炰緥
		 */
		static final MySharedPreferences INSTANCE = new MySharedPreferences();
	}

	public static MySharedPreferences getInstance(Context context) {
		mContext = context;
		return MySharedPreferencesHold.INSTANCE;
	}

	/**
	 * private鐨勬瀯閫犲嚱鏁扮敤浜庨伩鍏嶅鐣岀洿鎺ヤ娇鐢╪ew鏉ュ疄渚嬪寲瀵硅薄
	 */
	private MySharedPreferences() {
		preferences = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		editor = preferences.edit();
	}

	/**
	 * readResolve鏂规硶搴斿鍗曚緥瀵硅薄琚簭鍒楀寲鏃跺��
	 */
	private Object readResolve() {
		return getInstance(mContext);
	}

	public void saveIsFirstUse(boolean flag) {
		editor.putBoolean("IsFirstUse", flag);
		editor.commit();
	} 

	public boolean getIsFirstUse() {
		return preferences.getBoolean("IsFirstUse", true);
	}

 
	
	
	

	public void saveIsLogined(boolean flag) {
		editor.putBoolean("IsLogined", flag);
		editor.commit();
	} 

	public boolean getIsLogined() {
		return preferences.getBoolean("IsLogined", false);
	}

	/**
	 * 登录手机号码
	 */
	public void saveUserPhoneNum(String phoneNum){
		editor.putString("LoginPhoneNum", phoneNum);
		editor.commit();
	}
	public String getUserPhoneNum(){
		return preferences.getString("LoginPhoneNum", "");
	}
	 
	public void saveUserPwdMD5(String phoneNum){
		editor.putString("UserPwdMD5", phoneNum);
		editor.commit();
	}
	public String getUserPwdMD5(){
		return preferences.getString("UserPwdMD5", "");
	}
	
	
	/**
	 * 登录的token
	 */
	public void saveUserToken(String str){
		editor.putString("token", str);
		editor.commit();
	}
	public String getUserToken(){
		return preferences.getString("token", "");
	}
	 
 
	/**
	 * 用户身高
	 */
	public void saveUserLoginHight(double hight){
		editor.putFloat("LoginHight",(float)hight);
		editor.commit();
	}
	public float getUserLoginHight(){
		return	preferences.getFloat("LoginHight",0);
	}
	/**
	 * 用户体重
	 */
	
	public void saveUserLoginWight(double wight){
		editor.putFloat("LoginWight",(float)wight);
		editor.commit();
	}
	public float getUserLoginWight(){
		return	preferences.getFloat("LoginWight",0);
	}
	/**
	 * 用户头像
	 */
	public void saveUserLoginHeadURL(String headUrl){
		editor.putString("LoginHeadURL", headUrl);
		editor.commit();
	}
	public String getUserLoginHeadURL(){
		return preferences.getString("LoginHeadURL", "");
	}
	
	
	/**
	 * 登录用户生日
	 */
	public void saveUserLoginBirthday(String birthday){
		editor.putString("LoginBirthday", birthday);
		editor.commit();
	}
	public String getUserLoginBirthday(){
		return preferences.getString("LoginBirthday", "");
	}
	
	 
	/**
	 * 登录用户邮箱
	 */
	public void saveUserLoginEmail(String email){
		editor.putString("LoginEmail", email);
		editor.commit();
	}
	public String getUserLoginEmail(){
		return preferences.getString("LoginEmail", "");
	}
 
	public void saveUserLoginNickName(String signName){
		editor.putString("NickName", signName);
		editor.commit();
	}
	public String getUserLoginNickName(){
		return preferences.getString("NickName", "");
	}
	 
	
 
	public void saveFamilyCode(String code){
		editor.putString("FamilyCode", code);
		editor.commit();
	}
	public String getFamilyCode(){
		return preferences.getString("FamilyCode", "");
	}
 
	
	
	public void saveUserGender(String gender){ // 0 女      1男      2 保密
		editor.putString("UserGender", gender);
		editor.commit();
	}
	public String getUserGender(){
		return preferences.getString("UserGender", "");
	}
	
	
	
	public void saveMessageNotify(boolean flag) {
		editor.putBoolean("MessageNotify", flag);
		editor.commit();
	} 

	public boolean getMessageNotify() {
		return preferences.getBoolean("MessageNotify", true);
	}

	
	public void saveMessageVoice(boolean flag) {
		editor.putBoolean("MessageVoice", flag);
		editor.commit();
	} 

	public boolean getMessageVoice() {
		return preferences.getBoolean("MessageVoice", true);
	}
	
	
	
	public void saveMessageVibrate(boolean flag) {
		editor.putBoolean("MessageVibrate", flag);
		editor.commit();
	} 

	public boolean getMessageVibrate() {
		return preferences.getBoolean("MessageVibrate", true);
	}
	
	
	
	
	public void saveTodaySigned(boolean flag) {
		editor.putBoolean("TodaySigned", flag);
		editor.commit();
	} 

	public boolean getTodaySigned() {
		return preferences.getBoolean("TodaySigned", false);
	}
}
