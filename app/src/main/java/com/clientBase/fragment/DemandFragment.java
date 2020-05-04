package com.clientBase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.client.R;
import com.clientBase.adapter.ShopListAdapter;
import com.clientBase.base.BaseFragment;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.observable.ShopObservable;
import com.clientUI.CreatDemandActivity;
import com.clientUI.LoginActivity;
import com.clientUI.ShopMessageActivity;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class DemandFragment extends BaseFragment implements Observer {


    // 获取view
    private View rootView;
    // 获取控件

    private Button mviTongJi;

    private ListView mListMessage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message_send, null);
        initWidget();
        initData();
        return rootView;
    }

    @Override
    public void initWidget() {

        mListMessage = (ListView) rootView.findViewById(R.id.mListMessage);

        mviTongJi = (Button) rootView.findViewById(R.id.mviTongJi);
        mviTongJi.setOnClickListener(this);
        mListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShopMessageActivity.class);
                intent.putExtra("msg", list_result.get(position));
                startActivity(intent);

            }
        });

        mviTongJi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(MemberUserUtils.getUid(getActivity()).equals("")){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{

                    Intent intent = new Intent(getActivity(), CreatDemandActivity.class);
                    startActivity(intent);
                }



            }
        });



    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initData() {
        MessageAction(false);




    }

    private void MessageAction(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listShopQiuGouPhoneMessage");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    ShopListAdapter listAdapter;
    private List<ShopModel> list_result = new ArrayList<ShopModel>();
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
                        listAdapter = new ShopListAdapter(getActivity(), list_result);
                        mListMessage.setAdapter(listAdapter);

                    }
                }
                break;


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ShopObservable.getInstance().addObserver(DemandFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShopObservable.getInstance().addObserver(DemandFragment.this);
    }

    @Override
    public void update(Observable observable, Object data) {
        MessageAction(false);
    }


}

