package com.clientUI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.PractitionersAdapter;
import com.clientBase.adapter.SelectedImageAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.SelectImageItem;
import com.clientBase.model.TypeModel;
import com.clientBase.model.UserModel;
import com.clientBase.observable.ShopSendObservable;
import com.clientBase.photo.ui.SelectImagesActivity;
import com.clientBase.photo.ui.ShowCreatePicturesActivity;
import com.clientBase.util.LoadingDialog;
import com.clientBase.util.ToastUtil;
import com.clientBase.util.UploadUtils;
import com.clientBase.view.DialogListMsg;
import com.clientBase.view.GridLayout;
import com.clientBase.view.ImageItemClickListner;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CreatSendActivity extends BaseActivity implements ImageItemClickListner{
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;

	GridLayout grid_instructor;
	HorizontalScrollView horizontalscrollview;
	private ArrayList<SelectImageItem> imageItems = new ArrayList<SelectImageItem>();
	private SelectedImageAdapter selectedImageAdapter;
	private Button mSubmit;
	private File imgPath;
	public LoadingDialog mdialog;
	private List<String> mListImage = new ArrayList<String>();
	private int imgPosFlag = 0;
	private String picPath = null;
	// 图片上传表示位
	private int imageFlagNumber = 0;

	private EditText shopName;
	private EditText shopMoney;
	private EditText shopMessage;

	private int choiceType = 1;
	private RadioGroup mrgChoice;
	private RadioButton mrbok = null;
	private RadioButton mrbno = null;


	private TextView shopDN;
	private EditText shopQQWeiXin;

	private EditText shopPhone;


	private TextView mtvStart;
	private int choiceTime= 1;


	private List<TypeModel> mlistData = new ArrayList<TypeModel>();
	private DialogListMsg dialogListMsg;
	private PractitionersAdapter listaAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_qiugou);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;

			case R.id.mtvStart:

				dialogListMsg.Show();

				break;

			case R.id.mSubmit:

				Log.e("pony_log", imageFlagNumber + "");
				imageFlagNumber = 0;
				if (mListImage.size() == 0) {
					createTopicPost(true);
				} else {
					mdialog.show();
					UploadFileTask uploadFileTaskSend = new UploadFileTask(this);
					uploadFileTaskSend.execute(mListImage.get(imageFlagNumber));
				}
				break;

		}
	}


	@Override
	public void initWidget() {

		dialogListMsg = new DialogListMsg(this);
		dialogListMsg.setTitle().setText("请选择类别");

		listaAdapter = new PractitionersAdapter(this);
		mtvStart = (TextView) findViewById(R.id.mtvStart);
		mtvStart.setOnClickListener(this);

		shopDN = (TextView) findViewById(R.id.shopDN);
		shopQQWeiXin = (EditText) findViewById(R.id.shopQQWeiXin);
		shopPhone = (EditText) findViewById(R.id.shopPhone);



		mrgChoice = (RadioGroup) findViewById(R.id.mrgChoice);
		mrbok = (RadioButton) findViewById(R.id.mrbok);
		mrbno = (RadioButton) findViewById(R.id.mrbno);


		shopName = (EditText) findViewById(R.id.shopName);
		shopMoney = (EditText) findViewById(R.id.shopMoney);
		shopMessage = (EditText) findViewById(R.id.shopMessage);

		mdialog = new LoadingDialog(this, "上传图片...");
		mSubmit = (Button) findViewById(R.id.mSubmit);
		grid_instructor = (GridLayout) findViewById(R.id.grid_instructor);
		horizontalscrollview = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("发布图书");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mSubmit.setOnClickListener(this);

//		shopName.setText("考研资料");
//		shopMoney.setText("66");
//		shopMessage.setText("睁开眼睛，看着自己的双腿，它们陪你走了一生的路，可是，你们什么时候注意过它们的存在？");
		listPay(false);
	}

	@Override
	public void initData() {

		UserModel userModel = (UserModel) MemberUserUtils.getBean(this, "user_messgae");
		shopDN.setText(userModel.getUdpName());

		mrgChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mrbok.getId()) {
					choiceType = 1;
				} else if (checkedId == mrbno.getId()) {
					choiceType = 2;
				}
			}
		});


		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		initSelectedGridView();


		dialogListMsg.show_listview().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				dialogListMsg.Close();
				posIndex = pos;
				mtvStart.setText(mlistData.get(pos).getTypeName());
			}
		});

	}
	private int posIndex = 0;
	private void initSelectedGridView() {
		selectedImageAdapter = new SelectedImageAdapter(this, imageItems);
		SelectImageItem addItem = new SelectImageItem();
		addItem.setSid(100);// 添加的图标
		imageItems.add(addItem);
		selectedImageAdapter.notifyDataSetChanged();
		grid_instructor.setGridAdapter(selectedImageAdapter, CreatSendActivity.this);

	}


	@Override
	public void imageItemClick(int position, SelectImageItem imageItem) {
		if (imageItem != null) {
			int sid = imageItem.getSid();
			if (sid == 100) {

				if (CreatSendActivity.this.getIntent().getIntExtra("photo_message", 0) == 1) {
					goCameraActivity();
				} else {
					// 添加图片
					Intent intentImages = new Intent(CreatSendActivity.this, SelectImagesActivity.class);
					intentImages.putExtra("image_count", imageItems.size());
					intentImages.putExtra("max_count", "1");
					startActivityForResult(intentImages, 1);
				}
			} else {
				Intent intentPicture = new Intent(CreatSendActivity.this, ShowCreatePicturesActivity.class);
				intentPicture.putExtra("position", position);
				intentPicture.putExtra("piction_path", imageItems);
				startActivityForResult(intentPicture, 3);
			}
		}
	}

	private void addNewItemWithPre(String cameraPath) {

		int count = selectedImageAdapter.getCount();
		if (count > 0) {
			int selectCount = count - 1;
			SelectImageItem item = new SelectImageItem();
			item.setUrl(cameraPath);
			imageItems.add(selectCount, item);
		}
	}

	private void scrollgridView() {
		final int count = selectedImageAdapter.getCount();
		if (count > 1) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					horizontalscrollview.smoothScrollTo(2000, 0);
				}
			}, 500);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		if (requestCode == 5) {
			Log.d("camera", "" + (data == null));
			if (mCameraFile == null || !mCameraFile.exists()) {
				return;
			}
			imgPath = mCameraFile;
			mListImage.add(mCameraFile.getAbsolutePath());
			addNewItemWithPre(mCameraFile.getAbsolutePath());
		}

		if (requestCode == 1) {
			if (data != null) {

				if (0 == data.getIntExtra("action", -1)) {
					String cameraPath = data.getStringExtra("camera_path");
					addNewItemWithPre(cameraPath);

				} else if (1 == data.getIntExtra("action", -1)) {
					ArrayList<CharSequence> charSequences = data.getCharSequenceArrayListExtra("images");
					for (CharSequence ss : charSequences) {
						Log.e("pony_log", "image:" + ss.toString());
						picPath = ss.toString();
						mListImage.add(ss.toString());
						addNewItemWithPre(ss.toString());
					}
				}
				selectedImageAdapter.notifyDataSetChanged();
				scrollgridView();
			}
		}

		if (requestCode == 3) {
			if (data != null) {
				@SuppressWarnings("unchecked")
				ArrayList<SelectImageItem> imgUrls = (ArrayList<SelectImageItem>) data.getSerializableExtra("data");
				if (imgUrls != null && imgUrls.size() > 0) {
					imageItems.clear();
					imageItems.addAll(imgUrls);
					selectedImageAdapter.notifyDataSetChanged();
					scrollgridView();
				}
			}
		}
		grid_instructor.setGridAdapter(selectedImageAdapter, CreatSendActivity.this);
	}

	private Uri mOutPutFileUri;
	private File mCameraFile;

	// 去拍照
	private void goCameraActivity() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// dailyyoga_camera文件夹
		File parentFile = new File(Environment.getExternalStorageDirectory().toString() + "/dailyyoga_camera");
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		mCameraFile = new File(parentFile + "/" + System.currentTimeMillis() + ".jpg");
		mOutPutFileUri = Uri.fromFile(mCameraFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
		startActivityForResult(intent, 5);
	}

	private void createTopicPost(boolean isShow) {

		// 对图片路径的处理
		String imagePath = "";
		for (int i = 0; i < mListImage.size(); i++) {
			String[] arrPath = mListImage.get(i).split("\\/");
			imagePath = arrPath[arrPath.length - 1] + "," + imagePath;
		}





		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addShop");
		params.put("shopName", shopName.getText().toString());
		params.put("shopMoney", shopMoney.getText().toString());
		params.put("shopMessage", shopMessage.getText().toString());
		params.put("shopIsIm", choiceType+"");

		params.put("shopTypeId", mlistData.get(posIndex).getTypeId()+"");
		params.put("shopTypeName",mlistData.get(posIndex).getTypeName()+"");

		if (mListImage.size() != 0) {
			params.put("shopImg", imagePath.substring(0, imagePath.length() - 1));
		}
		params.put("shopUserId", MemberUserUtils.getUid(this));
		params.put("shopUserName", MemberUserUtils.getName(this));


		params.put("shopDPName", shopDN.getText().toString());
		params.put("shopQQorWX", shopQQWeiXin.getText().toString());
		params.put("shopPhone", shopPhone.getText().toString());
		params.put("shopFlag", "1");
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在上传...");

	}


	private void listPay(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listTypePhoneMessage");
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在注册...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
			case Consts.actionId.resultCode:
				ShopSendObservable.getInstance().notifyStepChange("ok");
				ToastUtil.ShowToast(CreatSendActivity.this, entry.getRepMsg());
				new Handler().postDelayed(new Runnable() {
					//
					@Override
					public void run() {
						finish();
					}
				}, 2000);
				break;
			case Consts.actionId.resultFlag:
				if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

					String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);


					if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
						mlistData = mGson.fromJson(entry.getData(), new TypeToken<List<TypeModel>>() {
						}.getType());
						listaAdapter.setData(mlistData);
						dialogListMsg.show_listview().setAdapter(listaAdapter);
						listaAdapter.notifyDataSetChanged();

					}

				}

				break;
		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		mdialog.dismiss();
		ToastUtil.show(CreatSendActivity.this, strMsg);

	}

	public class UploadFileTask extends AsyncTask<String, Void, String> {

		/**
		 * 可变长的输入参数，与AsyncTask.exucute()对应
		 */
		private Activity context = null;

		public UploadFileTask(Activity ctx) {
			this.context = ctx;

		}

		@Override
		protected void onPostExecute(String result) {
			imageFlagNumber++;
			if (UploadUtils.SUCCESS.equalsIgnoreCase(result)) {
				if (imageFlagNumber < mListImage.size()) {
					UploadFileTask uploadFileTask = new UploadFileTask(CreatSendActivity.this);
					uploadFileTask.execute(mListImage.get(imageFlagNumber));
				} else if (imageFlagNumber == mListImage.size()) {

					createTopicPost(true);
					// 返回HTML页面的内容

				}
			} else {
				ToastUtil.show(CreatSendActivity.this, " 图片上传失败");
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(String... params) {
			File file = new File(params[0]);
			return UploadUtils.uploadFile(file, Consts.URL + Consts.APP.MessageAction + "?action_flag=addShop");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

}
