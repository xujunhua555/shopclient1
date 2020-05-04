package com.clientUI;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.LocationProvider;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.util.AndroidBug5497Workaround;
import com.clientBase.util.SystemBarTintManager;


public class ImChatActivity extends FragmentActivity implements View.OnClickListener, LocationProvider {
	// // 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;

	/**
	 * 目标 Id
	 */
	private String mTargetId;

	/**
	 * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
	 */
	private String mTargetIds;

	String title;

	/**
	 * 会话类型
	 */
	private Conversation.ConversationType mConversationType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_im_chat);


		Log.i("pony_log","---------------");
		AndroidBug5497Workaround.assistActivity(this);
		initTiltBar();
		initWidget();
	}

	public void initWidget() {

		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);

		// 扩展功能自定义
//		InputProvider.ExtendProvider[] provider = { new PhotoCollectionsProvider(RongContext.getInstance()), // 图片
//				new CameraInputProvider(RongContext.getInstance()) // 地理位置
//		};
//
//		// 扩展功能自定义
//		// RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE,
//		// provider);
//		/**
//		 * 设置会话界面操作的监听器。
//		 */
//		RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
//		RongIM.resetInputExtensionProvider(Conversation.ConversationType.GROUP, provider);
//		RongIM.resetInputExtensionProvider(Conversation.ConversationType.DISCUSSION, provider);
//		RongIM.resetInputExtensionProvider(Conversation.ConversationType.SYSTEM, provider);
		Intent intent = getIntent();
		getIntentDate(intent);

		// 设置会话界面操作的监听器。

		RongIM.setConversationBehaviorListener(new MyConversationBehaviorListener());
		RongIM.setLocationProvider(this);// 设置地理位置提供者,不用位置的同学可以注掉此行代码

		/**
		 * 设置接收消息的监听器。
		 */
		RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

	}

	private class MyReceiveMessageListener implements OnReceiveMessageListener {

		/**
		 * 收到消息的处理。
		 *
		 * @param message
		 *            收到的消息实体。
		 * @param left
		 *            剩余未拉取消息数目。
		 * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
		 */
		@Override
		public boolean onReceived(Message message, int left) {
			// 开发者根据自己需求自行处理

			return false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;

		}
	}

	public void initData() {

	}

	/**
	 * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
	 */
	private void getIntentDate(Intent intent) {

		mTargetId = intent.getData().getQueryParameter("targetId");
		// mTargetIds = intent.getData().getQueryParameter("targetIds");
		title = getIntent().getData().getQueryParameter("title");
		// intent.getData().getLastPathSegment();//获得当前会话类型
		mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
		mTvTitle.setText(title);
		enterFragment(mConversationType, mTargetId);
	}

	/**
	 * 加载会话页面 ConversationFragment
	 *
	 * @param mConversationType
	 *            会话类型
	 * @param mTargetId
	 *            目标 Id
	 */
	private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

		ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
		Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversation")
				.appendPath(mConversationType.getName().toLowerCase()).appendQueryParameter("targetId", mTargetId).build();

		fragment.setUri(uri);
	}

	private class MyConversationBehaviorListener implements RongIM.ConversationBehaviorListener {

		/**
		 * 当点击用户头像后执行。
		 *
		 * @param context
		 *            上下文。
		 * @param conversationType
		 *            会话类型。
		 * @param userInfo
		 *            被点击的用户的信息。
		 * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
		 */
		@Override
		public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {

//			Intent intent = new Intent(ImChatActivity.this, UserImDetailActivity.class);
//			intent.putExtra("userId", userInfo.getUserId());
//			intent.putExtra("userName", userInfo.getName());
//			intent.putExtra("userImage", userInfo.getPortraitUri().toString());
//			ImChatActivity.this.startActivity(intent);
			return false;
		}

		/**
		 * 当长按用户头像后执行。
		 *
		 * @param context
		 *            上下文。
		 * @param conversationType
		 *            会话类型。
		 * @param userInfo
		 *            被点击的用户的信息。
		 * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
		 */
		@Override
		public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
			return false;
		}

		/**
		 * 当点击消息时执行。
		 *
		 * @param context
		 *            上下文。
		 * @param view
		 *            触发点击的 View。
		 * @param message
		 *            被点击的消息的实体信息。
		 * @return 如果用户自己处理了点击后的逻辑，则返回 true， 否则返回 false, false 走融云默认处理方式。
		 */
		@Override
		public boolean onMessageClick(Context context, View view, Message message) {

			if (message.getContent() instanceof ImageMessage) {
//				ImageMessage imageMessage = (ImageMessage) message.getContent();
//				Intent intent = new Intent(context, ImPhotoActivity.class);
//				intent.putExtra("photo", imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri());
//				if (imageMessage.getThumUri() != null)
//					intent.putExtra("thumbnail", imageMessage.getThumUri());
//				context.startActivity(intent);
			}

			return false;
		}

		/**
		 * 当长按消息时执行。
		 *
		 * @param context
		 *            上下文。
		 * @param view
		 *            触发点击的 View。
		 * @param message
		 *            被长按的消息的实体信息。
		 * @return 如果用户自己处理了长按后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
		 */
		@Override
		public boolean onMessageLongClick(Context context, View view, Message message) {
			return false;
		}

		/**
		 * 当点击链接消息时执行。
		 *
		 * @param context
		 *            上下文。
		 * @param link
		 *            被点击的链接。
		 * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
		 */
		@Override
		public boolean onMessageLinkClick(Context context, String link) {
			return false;
		}
	}

	@Override
	public void onStartLocation(Context context, LocationCallback callback) {
		/**
		 * demo 代码 开发者需替换成自己的代码。
		 */
//		ImCityContext.getInstance().setLastLocationCallback(callback);
	}

	/**
	 * titlebar变颜色
	 */
	public void initTiltBar() {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				// 透明状态栏
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				// 透明导航栏
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				// 设置状态栏颜色
				getWindow().setBackgroundDrawableResource(R.color.main_color);
			}
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			// 使用颜色资源
			tintManager.setStatusBarTintResource(R.color.main_color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
