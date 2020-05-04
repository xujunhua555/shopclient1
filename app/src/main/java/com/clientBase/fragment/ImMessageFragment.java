package com.clientBase.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.client.R;

import com.clientBase.base.BaseFragment;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;

import com.clientBase.model.Friend;

import com.clientBase.model.ResponseEntry;
import com.clientBase.model.UserModel;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.GroupInfoProvider;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;


public class ImMessageFragment extends BaseFragment implements UserInfoProvider, GroupInfoProvider {


	public static Fragment newInstance(){
		ImMessageFragment fragment = new ImMessageFragment();
		return fragment;
	}
	private View view;
	private LinearLayout mllService;

	List<Group> gourpList;
	private ListView mListMessage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_immessage, null);
		initWidget();
		initData();
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void initWidget() {

		mListMessage = (ListView) view.findViewById(R.id.mListMessage);


	}

	@Override
	public void initData() {
		listPhoneMessage(false);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void listPhoneMessage(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listPhoneUser");
		httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

//	private void MessageAction(boolean isShow) {
//		AjaxParams params = new AjaxParams();
//		params.put("action_flag", "ListGroup");
//		params.put("userId", MemberUserUtils.getUid(getActivity()));
//		httpPost(Consts.URL + Consts.APP.ImAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
//	}

	private List<Friend> userIdList;

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
			case Consts.actionId.resultFlag:
				if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

					userIdList = new ArrayList<Friend>();
					List<UserModel> list_result = mGson.fromJson(entry.getData(), new TypeToken<List<UserModel>>() {
					}.getType());

					for (int i = 0; i < list_result.size(); i++) {
						userIdList.add(new Friend(list_result.get(i).getUid()+"", list_result.get(i).getUname(), Consts.URL_IMAGE+list_result.get(i).getUImg()));
										}
					RongIM.setUserInfoProvider(ImMessageFragment.this, true);

					/**
					 * 刷新用户缓存数据。
					 *
					 * @param userInfo
					 *            需要更新的用户缓存数据。
					 */

					for (int i = 0; i < list_result.size(); i++) {
							RongIM.getInstance().refreshUserInfoCache(new UserInfo(list_result.get(i).getUid()+"", list_result.get(i).getUname(), Uri.parse(Consts.URL_IMAGE+list_result.get(i).getUImg())));
					}

					ConversationListFragment fragment = new ConversationListFragment();
					Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
							.appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") // 设置私聊会话非聚合显示
//						.appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")// 设置群组会话聚合显示
							.appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")// 设置讨论组会话非聚合显示
							.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")// 设置系统会话非聚合显示
							.build();
					fragment.setUri(uri);

					FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
					transaction.add(R.id.rong_content, fragment);
					transaction.commit();

				}


				break;

			case Consts.actionId.resultCode:
//
//				if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
//
//					String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
//					if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
//
//						list_result = mGson.fromJson(entry.getData(), new TypeToken<List<GroupModel>>() {
//						}.getType());
//
//						GroupListAdapter groupAdapter = new GroupListAdapter(getActivity(),list_result);
//						mListMessage.setAdapter(groupAdapter);
//
//
////						gourpList = new ArrayList<Group>();
////
////						for (int i = 0; i < list_result.size(); i++) {
////							gourpList.add(new Group(list_result.get(i).getGroupId(),list_result.get(i).getGroupName(),
////											Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536600372555&di=ac44542a9a943978f417955f3ea43b9e&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3671503453%2C3471909192%26fm%3D214%26gp%3D0.jpg")));
////						}
////
////						RongIM.setGroupInfoProvider(this, true);
////
////						for (int i = 0; i < list_result.size(); i++) {
////							RongIM.getInstance()
////									.refreshGroupInfoCache(
////											new Group(
////													list_result.get(i).getGroupId(),
////													list_result.get(i).getGroupName(),
////													Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536600372555&di=ac44542a9a943978f417955f3ea43b9e&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3671503453%2C3471909192%26fm%3D214%26gp%3D0.jpg")));
////						}
////
////
////
//					}
//				}

				ConversationListFragment fragment = new ConversationListFragment();
				Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
						.appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") // 设置私聊会话非聚合显示
//						.appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")// 设置群组会话聚合显示
						.appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")// 设置讨论组会话非聚合显示
						.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")// 设置系统会话非聚合显示
						.build();
				fragment.setUri(uri);

				FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.rong_content, fragment);
				transaction.commit();
				break;
		}

	}

	@Override
	public UserInfo getUserInfo(String userId) {
		for (Friend i : userIdList) {
			if (i.getUserId().equals(userId)) {
				Log.i("pony_log", "getUserName-----------------------------------------------:"+i.getUserName());
				return new UserInfo(i.getUserId(), i.getUserName(), Uri.parse(i.getPortraitUri()));
			}
		}
		return null;
	}



	@Override
	public Group getGroupInfo(String groupId) {
		for (Group i : gourpList) {
			if (i.getId().equals(groupId)) {
				Log.i("pony_log", "name-----------------------------------------------:"+i.getName());
				return new Group(i.getId(), i.getName(), Uri.parse(i.getPortraitUri() + ""));
			}
		}
		return null;
	}

}