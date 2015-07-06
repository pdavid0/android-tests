package ca.ypg.pdavid.com.yp_dine.merchant;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import ca.ypg.pdavid.com.yp_dine.R;
import ca.ypg.pdavid.com.yp_dine.fragment.RecyclerViewFragment;

/**
 * Created on 15-02-07.
 *
 * @author phil
 */
public class MerchantListFragment extends RecyclerViewFragment {
	private ArrayList<String> mCrimes;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new YPBindAdapter();
	}

	@Override protected void initDataset() {
		mCrimes = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			mCrimes.add("This is element #" + i);
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// handle item click
	}

	private class MerchantHolder extends RecyclerView.ViewHolder {
		private String mCrime;

		public MerchantHolder(View itemView) {
			super(itemView);

//			mSolvedCheckBox = (CheckBox) itemView
//					.findViewById(R.id.crime_list_item_solvedCheckBox);
		}

		public void bindCrime(String crime) {
			mCrime = crime;
//			mSolvedCheckBox.setChecked(crime.());
		}
	}

	private class YPBindAdapter extends RecyclerView.Adapter<MerchantHolder> {
		@Override
		public MerchantHolder onCreateViewHolder(ViewGroup parent, int pos) {
			View view = LayoutInflater.from(parent.getContext())
			                          .inflate(R.layout.list_item_merchant, parent, false);
			return new MerchantHolder(view);
		}

		@Override
		public void onBindViewHolder(MerchantHolder holder, int pos) {
			String crime = mCrimes.get(pos);
			holder.bindCrime(crime);
		}

		@Override
		public int getItemCount() {
			return mCrimes.size();
		}
	}
}
