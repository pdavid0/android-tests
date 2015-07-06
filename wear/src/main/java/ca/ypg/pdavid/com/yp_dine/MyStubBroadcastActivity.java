package ca.ypg.pdavid.com.yp_dine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class MyStubBroadcastActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent i = new Intent();
		i.setAction("ca.ypg.pdavid.com.yp_dine.SHOW_NOTIFICATION");
		i.putExtra(WearPostNotificationReceiver.CONTENT_KEY, getString(R.string.title));
		sendBroadcast(i);
		finish();
	}
}
