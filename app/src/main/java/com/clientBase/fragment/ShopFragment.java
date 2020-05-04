package com.clientBase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.client.R;
import com.clientBase.adapter.ShopListAdapter;
import com.clientBase.adapter.TypeHotAdapter;
import com.clientBase.base.BaseFragment;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.MainModel;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.model.TypeModel;
import com.clientBase.observable.ShopSendObservable;
import com.clientBase.view.GridviewForScrollView;
import com.clientBase.view.ListviewForScrollView;
import com.clientUI.CreatSendActivity;
import com.clientUI.LoginActivity;
import com.clientUI.MyShopTypeListActivity;
import com.clientUI.ShopMessageActivity;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ShopFragment extends BaseFragment implements Observer {




    // 获取view
    private View rootView;
    // 获取控件

    private Button mviTongJi;
    private List<TypeModel> listType = new ArrayList<TypeModel>();
        private List<ShopModel> list_resultsearch = new ArrayList<ShopModel>();
    private EditText metName;
    private GridviewForScrollView mBookType;

    MainModel mainModel;
    private ListviewForScrollView mNewsListMessage;

    private ListView msearchListMessage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message_shop, null);
        initWidget();
        initData();
        return rootView;
    }

    @Override
    public void initWidget() {

        msearchListMessage = (ListView) rootView.findViewById(R.id.msearchListMessage);
        mNewsListMessage = (ListviewForScrollView) rootView.findViewById(R.id.mNewsListMessage);


        mviTongJi = (Button) rootView.findViewById(R.id.mviTongJi);
        mviTongJi.setOnClickListener(this);
        metName = (EditText) rootView.findViewById(R.id.metName);
        mBookType = (GridviewForScrollView) rootView.findViewById(R.id.mBookType);

            mviTongJi.setVisibility(View.VISIBLE);


        msearchListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShopMessageActivity.class);
                intent.putExtra("msg", list_resultsearch.get(position));
                startActivity(intent);

            }
        });


        mNewsListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                    Intent intent = new Intent(getActivity(), CreatSendActivity.class);
                    startActivity(intent);
                }





            }
        });

        mBookType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MyShopTypeListActivity.class);
                intent.putExtra("msg", listType.get(i));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initData() {
        MessageAction(false);
        listTypePhoneMessage(false);


        metName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() > 0) {
                    listSearchBookMessage(false, editable.toString());
                    msearchListMessage.setVisibility(View.VISIBLE);
                } else {
                    msearchListMessage.setVisibility(View.GONE);
                    MessageAction(false);
                }
            }
        });


    }


    private void listSearchBookMessage(boolean isShow, String searchMsg) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listSearchMessage");
        params.put("searchMsg", searchMsg);
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }


    private void listTypePhoneMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listTypePhoneMessage");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultSeven, isShow, "正在加载...");
    }


    private void MessageAction(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listShopPhoneMessage");
        params.put("userId", MemberUserUtils.getUid(getActivity()));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    ShopListAdapter listAdapter;
    private List<ShopModel> list_result = new ArrayList<ShopModel>();
    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {

            case Consts.actionId.resultCode:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);


                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        list_resultsearch = mGson.fromJson(entry.getData(), new TypeToken<List<ShopModel>>() {
                        }.getType());
                        ShopListAdapter lookListAdapter = new ShopListAdapter(getActivity(), list_resultsearch);
                        msearchListMessage.setAdapter(lookListAdapter);

                    }

                }

                break;

            case Consts.actionId.resultSeven:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);


                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        listType = mGson.fromJson(entry.getData(), new TypeToken<List<TypeModel>>() {
                        }.getType());
                        TypeHotAdapter lookListAdapter = new TypeHotAdapter(getActivity(), listType);
                        mBookType.setAdapter(lookListAdapter);

                    }

                }

                break;
            case Consts.actionId.resultFlag:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        list_result.clear();
                        list_result = mGson.fromJson(entry.getData(), new TypeToken<List<ShopModel>>() {
                        }.getType());
                        listAdapter = new ShopListAdapter(getActivity(), list_result);
                        mNewsListMessage.setAdapter(listAdapter);


                    }
                }
                break;


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ShopSendObservable.getInstance().addObserver(ShopFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShopSendObservable.getInstance().addObserver(ShopFragment.this);
    }

    @Override
    public void update(Observable observable, Object data) {
        MessageAction(false);
    }


}

