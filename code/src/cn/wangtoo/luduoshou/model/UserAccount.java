package cn.wangtoo.luduoshou.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class UserAccount extends Model{

	@Column(name = "uid")
	public int uid;
	
	
	@Column(name = "nickname")
	public String nickname;
	
	
	@Column(name = "phoneNum")
	public String phoneNum;
	
	
	@Column(name = "accountType")
	public String accountType; //QQ,WeiBo SelfAccount
	
	
	@Column(name = "QQ_id")
	public String QQ_id;
	
	
	@Column(name = "WeiBo_id")
	public String WeiBo_id;
	
	
	@Column(name = "headImageURL")
	public String headImageURL;
	
	@Column(name = "registerTime")
	public int registerTime;
	
	@Column(name = "signinDays")
	public int signinDays;
	
	
	
}
