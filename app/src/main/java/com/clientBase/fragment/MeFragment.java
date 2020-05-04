package com.clientBase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.client.R;
import com.clientBase.base.BaseFragment;
import com.clientUI.MyCarActivity;
import com.clientUI.MyCollectActivity;
import com.clientUI.MyFaBuListActivity;
import com.clientUI.MyOrderActivity;
import com.clientUI.SoftMessageActivity;
import com.clientUI.UserActivity;


/**
 *
 * @author WangXuan
 *
 */
public class MeFragment extends BaseFragment {

	// 获取view
	private View rootView;
	private RelativeLayout mtvUser;
	private RelativeLayout mrlMyCollect;
	private RelativeLayout mrlReview;
	private RelativeLayout mtvSoft;
	private RelativeLayout mrlMyOrder;
	private RelativeLayout mrlMySend;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_content, null);

		return rootView;
	}

	/**
	 * 获取控件
	 */
	@Override
	public void initWidget() {
		mtvSoft = (RelativeLayout) rootView.findViewById(R.id.mtvSoft);
		mrlMyCollect = (RelativeLayout) rootView.findViewById(R.id.mrlMyCollect);
		mrlReview = (RelativeLayout) rootView.findViewById(R.id.mrlReview);
		mtvUser = (RelativeLayout) rootView.findViewById(R.id.mtvUser);
		mrlMyCollect.setOnClickListener(this);
		mtvUser.setOnClickListener(this);
		mtvSoft.setOnClickListener(this);
		mrlReview.setOnClickListener(this);

		mrlMyOrder = (RelativeLayout) rootView.findViewById(R.id.mrlMyOrder);
		mrlMyOrder.setOnClickListener(this);


		mrlMySend = (RelativeLayout) rootView.findViewById(R.id.mrlMySend);
		mrlMySend.setOnClickListener(this);
	}

	/**
	 * 处理点击事件
	 */
	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.mrlMySend:
				Intent mrlMySend = new Intent(getActivity(), MyFaBuListActivity.class);
				getActivity().startActivity(mrlMySend);
				break;
			case R.id.mrlReview:
				Intent mrlReview = new Intent(getActivity(), MyCarActivity.class);
				getActivity().startActivity(mrlReview);
				break;
			case R.id.mrlMyCollect:
				Intent mrlMyCollect = new Intent(getActivity(), MyCollectActivity.class);
				getActivity().startActivity(mrlMyCollect);
				break;
			case R.id.mrlMyOrder:
				Intent mrlMyOrder = new Intent(getActivity(), MyOrderActivity.class);
				getActivity().startActivity(mrlMyOrder);
				break;
			case R.id.mtvSoft:
				Intent mtvSoft = new Intent(getActivity(), SoftMessageActivity.class);
				getActivity().startActivity(mtvSoft);
				break;

			case R.id.mtvUser:
				Intent mtvUser = new Intent(getActivity(), UserActivity.class);
				getActivity().startActivity(mtvUser);
				break;


			default:
				break;
		}

	}

	/**
	 * 处理数据
	 */
	@Override
	public void initData() {

	}

	@Override
	public void onResume() {
		super.onResume();
		initWidget();
		initData();
	}
}
