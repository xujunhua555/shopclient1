package com.clientBase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.config.Consts;
import com.clientBase.listener.IsSendListner;
import com.clientBase.model.ShopModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopSendAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ShopModel> list_result;
	private int posIndex;
	private Context mContext;
	private IsSendListner misSendListner;
	public ShopSendAdapter(Context context, List<ShopModel> list_result , IsSendListner isSendListner) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		this.list_result = list_result;
		this.misSendListner = isSendListner;
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
	public View getView(final  int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.look_item, null);
			holder = new ViewHolder();
			holder.mTvTitle = (TextView) convertView.findViewById(R.id.mTvTitle);
			holder.mTvMoney = (TextView) convertView.findViewById(R.id.mTvMoney);
			holder.mtvTime = (TextView) convertView.findViewById(R.id.mtvTime);
			holder.mivShop = (ImageView) convertView.findViewById(R.id.mivShop);
			holder.mtvState = (TextView) convertView.findViewById(R.id.mtvState);


			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			holder.mTvTitle.setText(list_result.get(position).getShopName());
			holder.mTvMoney.setText("价格：" + list_result.get(position).getShopMoney() + "元");
			holder.mtvState.setVisibility(View.VISIBLE);
			if(list_result.get(position).getShopIsSend().equals("1")){
				holder.mtvState.setText("已上架");


				holder.mtvState.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						misSendListner.setIsSend(position,list_result.get(position));
					}
				});


			}else if(list_result.get(position).getShopIsSend().equals("2")){
				holder.mtvState.setText("已下架");

				holder.mtvState.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						misSendListner.setIsSend(position,list_result.get(position));
					}
				});

			}else  if(list_result.get(position).getShopIsSend().equals("3")){
				holder.mtvState.setText("已卖出");

				holder.mtvState.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						misSendListner.setIsSend(position,list_result.get(position));
					}
				});


			}else  if(list_result.get(position).getShopIsSend().equals("4")){
				holder.mtvState.setText("已发货");


			}

			holder.mtvTime.setText("发布时间：" + list_result.get(position).getShopCreatime());

			if (!TextUtils.isEmpty(list_result.get(position).getShopImg())) {
				Picasso.with(mContext).load(Consts.URL_IMAGE + list_result.get(position).getShopImg())
						.placeholder(R.drawable.default_drawable_show_pictrue).into(holder.mivShop);
			}


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
