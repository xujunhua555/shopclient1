package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.client.R;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.UserModel;
import com.clientBase.util.CustomToast;
import com.clientBase.util.LoadingDialog;
import com.clientBase.util.ToastUtil;

import net.tsz.afinal.http.AjaxParams;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * 登录页面
 *
 * @author WangXuan
 */

public class LoginActivity extends BaseActivity  {
    // title
    private TextView mTvTitle;
    // 登录用户名称
    private EditText mLoginNumber;
    // 登录密码
    private EditText mLoginPswd;
    // 登录按钮
    private Button mLogin;
    private Button mEnterpriseQuery;
    private LinearLayout mllTop;
    private UserModel userModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidget();
    }

    /**
     * 控件初始化
     */
    @Override
    public void initWidget() {



        mdialog = new LoadingDialog(this, "正在登录");
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("登录");
        mLoginNumber = (EditText) findViewById(R.id.mLoginNumber);
        mLoginPswd = (EditText) findViewById(R.id.mLoginPswd);
        mLogin = (Button) findViewById(R.id.mLogin);
        mEnterpriseQuery = (Button) findViewById(R.id.mEnterpriseQuery);
        // mLoginNumber.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        // 事件的监听
        mLogin.setOnClickListener(this);
        mEnterpriseQuery.setOnClickListener(this);
        // 给输入框设置默认的测试数据
        mLoginNumber.setSelection(mLoginNumber.getText().length());


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mLogin:
                if (TextUtils.isEmpty(mLoginNumber.getText().toString())) {

                    CustomToast.showToast(this, "请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mLoginPswd.getText().toString())) {
                    CustomToast.showToast(this, "请输入登录密码");
                    return;
                }
                LoginUserPost(true);
                break;

            case R.id.mEnterpriseQuery:
                Intent mEnterpriseQuery = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mEnterpriseQuery);
            default:
                break;
        }
    }

    @Override
    public void initData() {
    }

    /**
     * 用户的登录
     *
     * @param isShow
     */
    private void LoginUserPost(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("uphone", mLoginNumber.getText().toString());
        params.put("upswd", mLoginPswd.getText().toString());
        httpPost(Consts.URL + Consts.APP.loginUser, params, Consts.actionId.resultFlag, isShow, "正在登录...");
    }

    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {
            case Consts.actionId.resultFlag:

                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
                    userModel = mGson.fromJson(entry.getData(), UserModel.class);
                    MemberUserUtils.setToken(LoginActivity.this, userModel.getUtoken());
                    MemberUserUtils.setUid(LoginActivity.this, userModel.getUid() + "");
                    MemberUserUtils.setName(LoginActivity.this, userModel.getUname());
                    MemberUserUtils.setLoginFlag(this, userModel.getUtype());
                    MemberUserUtils.putBean(LoginActivity.this, "user_messgae", userModel);

                    if(userModel.getUflag().equals("1")){
                        connect(userModel.getUtoken());
                    }else{
                        CustomToast.showToast(this,"你的账号已经冻结");
                    }


                }
                break;

        }

    }

    @Override
    protected void callBackAllFailure(String strMsg, int actionId) {
        super.callBackAllFailure(strMsg, actionId);
        ToastUtil.show(LoginActivity.this, strMsg);

    }


    /**
     * 建立与融云服务器
     *
     * @param token
     */
    private void connect(String token) {

        Log.e("Pony_log", "--onTokenIncorrect");
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            @Override
            public void onTokenIncorrect() {

                Log.i("Pony_log", "--onTokenIncorrect");
            }

            @Override
            public void onSuccess(String userid) {
                mdialog.dismiss();
                Log.i("Pony_log", "--onSuccess" + userid);

//				if(userModel.getUtype().equals("1")){
					Intent intent = new Intent(LoginActivity.this, FrameworkActivity.class);
					startActivity(intent);
					finish();
//				}else{


//                if (userModel.getUtype().equals("2")) {
//                    Intent intent = new Intent(LoginActivity.this, TabAdminLayoutActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Intent intent = new Intent(LoginActivity.this, TabUserLayoutActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

//				}

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                mdialog.dismiss();
                Log.i("Pony_log", "--onError" + errorCode);
            }
        });
    }



}