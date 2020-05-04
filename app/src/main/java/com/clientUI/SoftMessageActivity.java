package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.base.BaseActivity;
import com.clientBase.db.MemberUserUtils;


public class SoftMessageActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;

	private Button mExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_message);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;
			case R.id.mExit:
				MemberUserUtils.setUid(this,"");
				Intent intent = new Intent(SoftMessageActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;

		}
	}

	@Override
	public void initWidget() {
		mExit = (Button) findViewById(R.id.mExit);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);


		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mExit.setOnClickListener(this);
		mTvTitle.setText("软件信息");

	}



	@Override
	public void initData() {

	}


}
