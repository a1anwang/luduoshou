package cn.wangtoo.luduoshou;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends LuBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setStatusBarColor(Color.RED);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setLogo(R.drawable.ic_launcher);

		toolbar.setTitle("标题");
		toolbar.setSubtitle("副标题");

		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.icon_star_off);

		toolbar.setOnMenuItemClickListener(onMenuItemClick);

		// 获取CommunitySDK实例, 参数1为Context类型
		// CommunitySDK mCommSDK = CommunityFactory.getCommSDK(this);
		// 打开微社区的接口, 参数1为Context类型
		// mCommSDK.openCommunity(this);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
				mDrawerLayout, toolbar, R.string.drawer_open,
				R.string.drawer_close);
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			String msg = "";
			switch (menuItem.getItemId()) {
			case R.id.action_edit:
				msg += "Click edit";
				break;
			case R.id.action_share:
				msg += "Click share";
				break;
			case R.id.action_settings:
				msg += "Click setting";
				break;
			}

			if (!msg.equals("")) {
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
			return true;
		}
	};

 
}
