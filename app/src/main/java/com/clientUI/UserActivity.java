package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.client.R;
import com.clientBase.base.BaseActivity;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.UserModel;

public class UserActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mName;
	private String state;
	private RelativeLayout mtvUserName;
	private TextView mPhone;
	private TextView mAddress;
	private RelativeLayout mrlPhone;
	private RelativeLayout mrlAddress;
	private RelativeLayout mrlpeisongAddress;
	private TextView mtvMyAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				UserActivity.this.finish();
				break;
			case R.id.mrlPhone:

				break;
			case R.id.mrlAddress:
				Intent mrlAddress = new Intent(UserActivity.this, UpdatePswdActivity.class);
				startActivity(mrlAddress);
				break;
			case R.id.mtvUserName:
				Intent mtvUserName = new Intent(UserActivity.this, UpdateNameActivity.class);
				startActivity(mtvUserName);
				break;
		}
	}

	@Override
	public void initWidget() {
		mtvMyAddress = (TextView) findViewById(R.id.mtvMyAddress);
		mPhone = (TextView) findViewById(R.id.mPhone);
		mAddress = (TextView) findViewById(R.id.mAddress);
		mrlPhone = (RelativeLayout) findViewById(R.id.mrlPhone);
		mrlAddress = (RelativeLayout) findViewById(R.id.mrlAddress);
		mrlpeisongAddress = (RelativeLayout) findViewById(R.id.mrlpeisongAddress);

		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mName = (TextView) findViewById(R.id.mName);

		mTvTitle.setText("我的资料");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mrlpeisongAddress.setOnClickListener(this);
		mrlAddress.setOnClickListener(this);

		mtvUserName = (RelativeLayout) findViewById(R.id.mtvUserName);
		mtvUserName.setOnClickListener(this);


	}

	@Override
	public void initData() {

		try {
			UserModel userModel = (UserModel) MemberUserUtils.getBean(this, "user_messgae");
			mName.setText("用户名称： " + userModel.getUname());
			mPhone.setText("手机号码： " + userModel.getUphone());



		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	protected void onResume() {
		super.onResume();
		initWidget();
		initData();
	}
}
