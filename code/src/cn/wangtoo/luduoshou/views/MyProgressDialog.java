package cn.wangtoo.luduoshou.views;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog{

	public MyProgressDialog(Context context) {
		super(context);
		
		setCancelable(false);
	}

}
