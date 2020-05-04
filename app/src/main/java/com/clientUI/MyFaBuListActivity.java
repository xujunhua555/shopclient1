package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.ShopSendAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.listener.IsSendListner;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.observable.ShopObservable;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;


public class MyFaBuListActivity extends BaseActivity implements IsSendListner {

    // 标题
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    private TextView mIvStu;
    private ListView mListMessage;
    private List<ShopModel> list_result = new ArrayList<ShopModel>();
    private String state;
    private LinearLayout mllNomessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        initWidget();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mivCreateMessage:
                Intent intent = new Intent(this, CreatSendActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initWidget() {

        mIvStu = (TextView) findViewById(R.id.mIvStu);
        mIvStu.setText("添加");
        mIvStu.setVisibility(View.GONE);
        mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
        mListMessage = (ListView) findViewById(R.id.mListMessage);

        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        state = this.getIntent().getStringExtra("state");
        mTvTitle.setText("我的发布");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mIvStu.setOnClickListener(this);
    }

    @Override
    public void initData() {
        listShopPhoneUserMessage(true);
        mListMessage.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(MyFaBuListActivity.this, ShopMessageActivity.class);
                intent.putExtra("msg", list_result.get(position));
                startActivity(intent);
            }
        });
    }

    private void listShopPhoneUserMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listShopPhoneUserMessage");
        params.put("shopUserId", MemberUserUtils.getUid(this));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    private void updateStateMessage(boolean isShow, ShopModel shopModel) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "updateStateMessage");

        if (shopModel.getShopIsSend().equals("1")) {
            params.put("shopIsSend", "2");
        } else {
            params.put("shopIsSend", "1");
        }
        params.put("shopId", shopModel.getShopId());
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }

    private void updateOrderMessage(boolean isShow, ShopModel shopModel) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "updateOrderMessage");
        params.put("orderState", "2");
        params.put("orderMessageId", shopModel.getShopId());
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }

    ShopSendAdapter listAdapter;

    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {
            case Consts.actionId.resultFlag:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        list_result.clear();
                        list_result = mGson.fromJson(entry.getData(), new TypeToken<List<ShopModel>>() {
                        }.getType());
                        listAdapter = new ShopSendAdapter(this, list_result, this);
                        mListMessage.setAdapter(listAdapter);
                        mllNomessage.setVisibility(View.GONE);
                    } else {
                        mllNomessage.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case Consts.actionId.resultCode:
                ShopModel shopModel = list_result.get(posIndex);
                if (shopModel.getShopIsSend().equals("1")) {
                    shopModel.setShopIsSend("2");
                } else   if (shopModel.getShopIsSend().equals("2")) {
                    shopModel.setShopIsSend("1");
                }else   if (shopModel.getShopIsSend().equals("3")) {
                    shopModel.setShopIsSend("4");
                }
                list_result.set(posIndex,shopModel);
                listAdapter.notifyDataSetChanged();
                ShopObservable.getInstance().notifyStepChange("ok");
                break;

        }

    }

    private int posIndex;

    @Override
    public void setIsSend(int pos, ShopModel shopModel) {
        posIndex = pos;

        if (shopModel.getShopIsSend().equals("3")) {
            updateOrderMessage(false, shopModel);
        } else {
            updateStateMessage(false, shopModel);
        }


    }
}
