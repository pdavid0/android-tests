package ca.ypg.pdavid.com.yp_dine;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ca.ypg.pdavid.com.yp_dine.merchant.MerchantListFragment;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends AppCompatActivity
		implements ItemListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private View.OnClickListener myOnClickListener = new View.OnClickListener() {

		@Override public void onClick(View v) {
			int notificationId = 001;
			// Build intent for notification content
			Context context = v.getContext();
			Intent viewIntent = new Intent(context, ItemDetailActivity.class);
			viewIntent.putExtra("EXTRA_EVENT_ID", 01);
			PendingIntent viewPendingIntent =
					PendingIntent.getActivity(context, 0, viewIntent, 0);

			NotificationCompat.Builder notificationBuilder =
					new NotificationCompat.Builder(context)
							.setSmallIcon(R.drawable.ic_headset)
							.setContentTitle("eventTitle")
							.setContentText("eventLocation")
							.setContentIntent(viewPendingIntent);

			// Get an instance of the NotificationManager service
			NotificationManagerCompat notificationManager =
					NotificationManagerCompat.from(context);

			// Build the notification and issues it with notification manager.
			notificationManager.notify(notificationId, notificationBuilder.build());
		}
	};
	private DrawerLayout mDrawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.item_list))
					.setActivateOnItemClick(true);
			Snackbar
					.make(findViewById(R.id.item_detail_container), R.string.item_list_snackbar_text, Snackbar.LENGTH_LONG)
					.setAction(R.string.item_list_snackbar_action, myOnClickListener)
					.show(); // Don’t forget to show!
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.getBackground().setAlpha(0);

		final ActionBar ab = getSupportActionBar();
		ab.setHomeAsUpIndicator(R.drawable.ic_menu);
		ab.setDisplayHomeAsUpEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		if (navigationView != null) {
			setupDrawerContent(navigationView);
		}

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		if (viewPager != null) {
			setupViewPager(viewPager);
		}

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
						        .setAction(
								        "Action", new View.OnClickListener() {
									        @Override public void onClick(View v) {

									        }
								        }
						        ).show();
					}
				}
		);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);


		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.sample_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						menuItem.setChecked(true);
						mDrawerLayout.closeDrawers();
						return true;
					}
				}
		);
	}

	private void setupViewPager(ViewPager viewPager) {
		Adapter adapter = new Adapter(getSupportFragmentManager());
		adapter.addFragment(new ItemListFragment(), "Concierge");
		adapter.addFragment(new MerchantListFragment(), "Nearby");
		adapter.addFragment(new ItemListFragment(), "Curators");
		viewPager.setAdapter(adapter);
	}

	/**
	 * Callback method from {@link ItemListFragment.Callbacks}
	 * indicating that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
			                           .replace(R.id.item_detail_container, fragment)
			                           .commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	static class Adapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragments      = new ArrayList<>();
		private final List<String>   mFragmentTitles = new ArrayList<>();

		public Adapter(FragmentManager fm) {
			super(fm);
		}

		public void addFragment(Fragment fragment, String title) {
			mFragments.add(fragment);
			mFragmentTitles.add(title);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitles.get(position);
		}
	}

}
