package com.clientUI;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.client.R;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.util.ToastUtil;

import net.tsz.afinal.http.AjaxParams;


public class PayMessageActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mtvMsg;
	private Button mPay;
	private int choiceType = 1;
	private RadioGroup mrdChoice;
	private RadioButton mrbWeiXin = null;
	private RadioButton mrbPay = null;
	private EditText mtvAddress,mtvbeizhu;
	private TextView mtvName;
	private ShopModel shopModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_message);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;
			case R.id.mPay:


				if (TextUtils.isEmpty(mtvAddress.getText().toString())) {
					ToastUtil.ShowCentre(this, "请输入配送地址");
					return;
				}
				createTopicPost(true);
				break;

		}
	}

	@Override
	public void initWidget() {



		mtvbeizhu = (EditText) findViewById(R.id.mtvbeizhu);
		mtvAddress = (EditText) findViewById(R.id.mtvAddress);
		mrdChoice = (RadioGroup) findViewById(R.id.mrdChoice);
		mrbWeiXin = (RadioButton) findViewById(R.id.mrbWeiXin);
		mrbPay = (RadioButton) findViewById(R.id.mrbPay);
		mtvName = (TextView) findViewById(R.id.mtvName);
		mrdChoice = (RadioGroup) findViewById(R.id.mrdChoice);
		mrbWeiXin = (RadioButton) findViewById(R.id.mrbWeiXin);
		mrbPay = (RadioButton) findViewById(R.id.mrbPay);
		mPay = (Button) findViewById(R.id.mPay);
		mtvMsg = (TextView) findViewById(R.id.mtvMsg);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("支付确认");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mPay.setOnClickListener(this);
	}

	@Override
	public void initData() {

		shopModel =(ShopModel) this.getIntent().getSerializableExtra("msg");
		mtvMsg.setText(shopModel.getShopMoney()+"元");
		mtvName.setText(shopModel.getShopName());


			mtvMsg.setText(shopModel.getShopMoney()+"元");


		mrdChoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mrbWeiXin.getId()) {
					choiceType = 1;
				} else if (checkedId == mrbPay.getId()) {
					choiceType = 2;
				}
			}
		});
	}

	private void createTopicPost(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addOrder");
		params.put("orderMessageId", shopModel.getShopId());
		params.put("orderMessageName", shopModel.getShopName());


		params.put("orderMessageMoney",shopModel.getShopMoney());
		params.put("orderAddress",mtvAddress.getText().toString());
		params.put("orderBZ",mtvbeizhu.getText().toString());

		params.put("orderUserId", MemberUserUtils.getUid(this));
		params.put("orderUserName",MemberUserUtils.getName(this));
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		ToastUtil.show(PayMessageActivity.this, entry.getRepMsg());
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
			}
		}, 2000);

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(PayMessageActivity.this, strMsg);

	}
}
