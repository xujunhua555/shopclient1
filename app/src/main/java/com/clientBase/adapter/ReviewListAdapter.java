package com.clientBase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.model.ReviewModel;

import java.util.List;

public class ReviewListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ReviewModel> list_result;
	private int posIndex;
	private Context mContext;

	public ReviewListAdapter(Context context, List<ReviewModel> list_result) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		this.list_result = list_result;
	}

	@Override
	public int getCount() {
		return list_result.size();
	}

	@Override
	public Object getItem(int position) {
		return list_result.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.review_item, null);
			holder = new ViewHolder();
			holder.mTvTitle = (TextView) convertView.findViewById(R.id.mTvTitle);
			holder.mTvMoney = (TextView) convertView.findViewById(R.id.mTvMoney);
			holder.mtvTime = (TextView) convertView.findViewById(R.id.mtvTime);


			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			holder.mTvTitle.setText("反馈用户："+list_result.get(position).getReviewUserName());
			holder.mTvMoney.setText(list_result.get(position).getReviewMessage());
			holder.mtvTime.setText(list_result.get(position).getReviewTime());

		} catch (Exception e) {
		}

		return convertView;

	}

	private class ViewHolder {
		private TextView mTvTitle,mtvState;
		private TextView mTvMoney;
		private TextView mtvTime;
		private ImageView mivShop;
	}

	public void setPos(int pos) {
		posIndex = pos;
	}

}
