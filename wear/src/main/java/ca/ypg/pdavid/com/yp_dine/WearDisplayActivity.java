package ca.ypg.pdavid.com.yp_dine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WearDisplayActivity extends Activity {

	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_wear_notification);
		mTextView = (TextView) findViewById(R.id.text);
	}
}
