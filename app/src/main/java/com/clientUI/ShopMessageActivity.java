package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.ChoiceShopAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.observable.ShopObservable;
import com.clientBase.observable.ShopSendObservable;
import com.clientBase.photo.ui.ShowPictureActivity;
import com.clientBase.util.CustomToast;
import com.clientBase.util.ToastUtil;
import com.clientBase.view.DialogListMsg;
import com.clientBase.view.DialogMsg;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

public class ShopMessageActivity extends BaseActivity {
    // title
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    // 查询按钮
    private TextView mtvtitle;
    private TextView mtvcontent;
    ShopModel noticeModel;

    private TextView Name;
    private TextView phone;

    private Button mbtnPay, mbtnChat,mbtnCollect,mbtnReview;
    private TextView mtvShopPrice;

    private int choiceTime = 1;
    private ImageView guide_image;
    private TextView mIvStu;

    private DialogMsg dialogMsg;


    private List<ShopModel> mlistData = new ArrayList<ShopModel>();
    private DialogListMsg dialogListMsg;
    private ChoiceShopAdapter listaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmsg);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {

        mbtnCollect = (Button) findViewById(R.id.mbtnCollect);
        mbtnCollect.setOnClickListener(this);


        mbtnReview = (Button) findViewById(R.id.mbtnReview);
        mbtnReview.setOnClickListener(this);


        dialogListMsg = new DialogListMsg(this);
        dialogListMsg.setTitle().setText("请选择交易商品");
        listaAdapter = new ChoiceShopAdapter(this);


        dialogMsg = new DialogMsg(this);
        dialogMsg.Set_Msg("你选择处理类型");
        dialogMsg.submit_ok().setText("物钱交易");
        dialogMsg.submit_no().setText("物物交易");

        mIvStu = (TextView) findViewById(R.id.mIvStu);
        mIvStu.setOnClickListener(this);
        mIvStu.setText("添加购物车");
        mIvStu.setVisibility(View.VISIBLE);


        mbtnChat = (Button) findViewById(R.id.mbtnChat);
        mbtnChat.setOnClickListener(this);

        guide_image = (ImageView) findViewById(R.id.guide_image);
        guide_image.setOnClickListener(this);
        mbtnPay = (Button) findViewById(R.id.mbtnPay);
        mbtnPay.setOnClickListener(this);
        mtvShopPrice = (TextView) findViewById(R.id.mtvShopPrice);

        Name = (TextView) findViewById(R.id.Name);
        phone = (TextView) findViewById(R.id.phone);

        mtvtitle = (TextView) findViewById(R.id.mtvtitle);
        mtvcontent = (TextView) findViewById(R.id.mtvcontent);

        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("书籍详情");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mIvBack:
                ShopMessageActivity.this.finish();
                break;

            case R.id.mbtnCollect:

                if(MemberUserUtils.getUid(this).equals("")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    if (noticeModel.isCollectState()) {
                        collectNoMessage(false);
                    } else {
                        collectOkMessage(false);
                    }
                }






                break;

            case R.id.mbtnChat:


                if(MemberUserUtils.getUid(this).equals("")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    RongIM.getInstance().startPrivateChat(this, noticeModel.getShopUserId(), noticeModel.getShopUserName());
                }


                break;

            case R.id.mbtnPay:


                if(MemberUserUtils.getUid(this).equals("")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent mbtnPay = new Intent(ShopMessageActivity.this, PayMessageActivity.class);
                    mbtnPay.putExtra("msg", noticeModel);
                    startActivity(mbtnPay);
                }




                break;
            case R.id.mbtnReview:


                if(MemberUserUtils.getUid(this).equals("")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent mbtnPay = new Intent(ShopMessageActivity.this, ReviewShareMessageActivity.class);
                    mbtnPay.putExtra("msg", noticeModel);
                    startActivity(mbtnPay);
                }









                break;

            case R.id.mIvStu:


                if(MemberUserUtils.getUid(this).equals("")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    addCar(false);
                }


                break;
            case R.id.guide_image:

                Intent intent = new Intent(this, ShowPictureActivity.class);
                intent.putExtra("piction_path", Consts.URL_IMAGE + noticeModel.getShopImg());
                startActivity(intent);

                break;

        }
    }

    @Override
    public void initData() {

        noticeModel = (ShopModel) this.getIntent().getSerializableExtra("msg");

        if(noticeModel.isCollectState()){
            mbtnCollect.setText("已收藏");
        }else{
            mbtnCollect.setText("未收藏");
        }
        mtvtitle.setText(noticeModel.getShopName());
        Name.setText("商家：" + noticeModel.getShopUserName()+"-"+noticeModel.getShopQQorWX());
        phone.setText("类型：" + noticeModel.getShopTypeName());
        mtvcontent.setText("        " + noticeModel.getShopMessage());

        mtvShopPrice.setText(noticeModel.getShopMoney() + "元");


        if (!TextUtils.isEmpty(noticeModel.getShopImg())) {
            Picasso.with(this).load(Consts.URL_IMAGE + noticeModel.getShopImg()).placeholder(R.drawable.default_drawable_show_pictrue)
                    .into(guide_image);
        }

        dialogMsg.submit_no().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMsg.Close();
                dialogListMsg.Show();
            }
        });
        dialogMsg.submit_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMsg.Close();

            }
        });


        dialogListMsg.show_listview().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                dialogListMsg.Close();
                posIndex = pos;

            }
        });

        dialogListMsg.submit_no().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListMsg.Close();
            }
        });
        listShopPhoneUserMessage(false);

    }

    private int posIndex = 0;


    private void listShopPhoneUserMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listShopPhoneUserMessage");
        params.put("shopUserId", MemberUserUtils.getUid(this));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    private void updateShopStateMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "updateShopStateMessage");
        params.put("shopRecycling","2");
        params.put("shopId",noticeModel.getShopId());
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }


    private void addCar(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "addCar");
        params.put("carShopId", noticeModel.getShopId());
        params.put("carUserId", MemberUserUtils.getUid(this)+"");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultState, isShow, "正在加载...");
    }




    private void collectNoMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "deleteCollectMessage");
        params.put("collectUserId", MemberUserUtils.getUid(this));
        params.put("collectMessageId",noticeModel.getShopId()+"");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultSix, isShow, "正在加载...");
    }

    private void collectOkMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "addCollectMessage");
        params.put("collectUserId", MemberUserUtils.getUid(this));
        params.put("collectMessageId", noticeModel.getShopId()+"");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultSeven, isShow, "正在加载...");
    }





    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {

            case Consts.actionId.resultState:
                ToastUtil.show(this,entry.getRepMsg());

                break;
            case Consts.actionId.resultFlag:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        mlistData.clear();
                        mlistData = mGson.fromJson(entry.getData(), new TypeToken<List<ShopModel>>() {
                        }.getType());
                        listaAdapter.setData(mlistData);
                        dialogListMsg.show_listview().setAdapter(listaAdapter);
                        listaAdapter.notifyDataSetChanged();
                    }
                }
                break;

            case Consts.actionId.resultCode:
                CustomToast.showToast(this, entry.getRepMsg());
                ShopObservable.getInstance().notifyStepChange("ok");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
                break;
            case Consts.actionId.resultSeven:
                noticeModel.setCollectState(true);
                mbtnCollect.setText("已收藏");
                ShopSendObservable.getInstance().notifyStepChange("ok");
                break;
            case Consts.actionId.resultSix:
                noticeModel.setCollectState(false);
                mbtnCollect.setText("未收藏");
                ShopSendObservable.getInstance().notifyStepChange("ok");
                break;

        }

    }
}
