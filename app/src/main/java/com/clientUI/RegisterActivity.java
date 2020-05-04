package com.clientUI;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;

import com.clientBase.adapter.DepartmentsAdapter;
import com.clientBase.adapter.PractitionersAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ApplyModel;
import com.clientBase.model.DepartmentModel;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.TypeModel;
import com.clientBase.time.JudgeDate;
import com.clientBase.time.MyAlertDialog;
import com.clientBase.time.ScreenInfo;
import com.clientBase.time.WheelMain;
import com.clientBase.util.CustomToast;
import com.clientBase.util.FaceUtil;
import com.clientBase.util.LoadingDialog;
import com.clientBase.util.ToastUtil;
import com.clientBase.view.CircleImageView;
import com.clientBase.view.DialogListMsg;
import com.clientBase.view.DialogMsg;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class RegisterActivity extends BaseActivity {
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private Button mSubmit;

	private EditText metName;
	private EditText metPhone;
	private EditText metPswd;

	private int choiceType = 1;
	private RadioGroup mrgChoice;
	private RadioButton mrbTea = null;
	private RadioButton mrbParents = null;

	private EditText metNo;



	private List<DepartmentModel> mlistData = new ArrayList<DepartmentModel>();
	private DialogListMsg dialogListMsg;
	private DepartmentsAdapter listaAdapter;
	private TextView mtvStart;
	private int posIndex = 0;


	private ImageView infoMusicOperating;
	private final int REQUEST_PICTURE_CHOOSE = 1;
	private final int REQUEST_CAMERA_IMAGE = 2;

	private Bitmap mImage = null;
	private byte[] mImageData = null;
	// authid为6-18个字符长度，用于唯一标识用户
	private Toast mToast;
	// 进度对话框
	private EditText online_authid;
	// 拍照得到的照片文件
	private File mPictureFile;
	// 采用身份识别接口进行在线人脸识别
	private String userImage;
	private LinearLayout mllImgae;
	private DialogMsg dialogMsg;
	private CircleImageView mivUserImg;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_register);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;
			case R.id.mSubmit:
				createTopicPost(true);
				break;
			case R.id.mivUserImg:
				dialogMsg.Show();
				break;


			case R.id.mtvStart:

				dialogListMsg.Show();

				break;
		}
	}

	@Override
	public void initWidget() {

		dialogListMsg = new DialogListMsg(this);
		dialogListMsg.setTitle().setText("请选择院系");

		listaAdapter = new DepartmentsAdapter(this);
		mtvStart = (TextView) findViewById(R.id.mtvStart);
		mtvStart.setOnClickListener(this);


		dialogListMsg.show_listview().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				dialogListMsg.Close();
				posIndex = pos;
				mtvStart.setText(mlistData.get(pos).getDepartmentsName());
			}
		});

		dialogListMsg.submit_no().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogListMsg.Close();
			}
		});


		metNo = (EditText) findViewById(R.id.metNo);
		mivUserImg = (CircleImageView) findViewById(R.id.mivUserImg);
		mrgChoice = (RadioGroup) findViewById(R.id.mrgChoice);
		mrbTea = (RadioButton) findViewById(R.id.mrbTea);
		mrbParents = (RadioButton) findViewById(R.id.mrbParents);
		metName = (EditText) findViewById(R.id.metName);
		metPhone = (EditText) findViewById(R.id.metPhone);
		metPswd = (EditText) findViewById(R.id.metPswd);

		mSubmit = (Button) findViewById(R.id.mSubmit);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("注册");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
		mivUserImg.setOnClickListener(this);
		listPhoneResumeMessage(false);

	}

	@Override
	public void initData() {

		dialogMsg = new DialogMsg(this);
		dialogMsg.Set_Msg("请选择图像获取方式");
		dialogMsg.submit_no().setText("相册上传");
		dialogMsg.submit_ok().setText("拍照上传");

		dialogMsg.submit_no().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogMsg.Close();
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_PICK);
				startActivityForResult(intent, REQUEST_PICTURE_CHOOSE);
			}
		});
		dialogMsg.submit_ok().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogMsg.Close();
				mPictureFile = new File(Environment.getExternalStorageDirectory(), "picture" + System.currentTimeMillis() / 1000 + ".jpg");
				// 启动拍照,并保存到临时文件
				Intent mIntent = new Intent();
				mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPictureFile));
				mIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				startActivityForResult(mIntent, REQUEST_CAMERA_IMAGE);

			}
		});


		mrgChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mrbTea.getId()) {
					choiceType = 1;
				} else if (checkedId == mrbParents.getId()) {
					choiceType = 2;
				}
			}
		});

		listDepartments(false);
	}

	String imgMsg;
	private void createTopicPost(boolean isShow) {


		AjaxParams params = new AjaxParams();

		params.put("uno", metNo.getText().toString());
		params.put("udpId", mlistData.get(posIndex).getDepartmentsId()+"");
		params.put("udpName",mlistData.get(posIndex).getDepartmentsName()+"");
		params.put("uname", metName.getText().toString());
		params.put("uphone", metPhone.getText().toString());
		params.put("upswd", metPswd.getText().toString());
		params.put("uImg", userImage);

		httpPost(Consts.URL + Consts.APP.RegAction, params, Consts.actionId.resultCode, isShow, "正在注册...");
	}

	private void listPhoneResumeMessage(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listTypePhoneMessage");
		params.put("resumeUserId", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

	private void listDepartments(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listDepartments");
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在注册...");
	}
	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);


		switch (actionId) {
			case Consts.actionId.resultCode:
				CustomToast.showToast(this, entry.getRepMsg());
				new Handler().postDelayed(new Runnable() {
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
						mlistData = mGson.fromJson(entry.getData(), new TypeToken<List<DepartmentModel>>() {
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
		ToastUtil.show(this, strMsg);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String fileSrc = null;



		if (resultCode == 10) {

		} else if (requestCode == REQUEST_PICTURE_CHOOSE) {
			if ("file".equals(data.getData().getScheme())) {
				// 有些低版本机型返回的Uri模式为file
				fileSrc = data.getData().getPath();
			} else {
				// Uri模型为content
				String[] proj = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);
				cursor.moveToFirst();
				int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				fileSrc = cursor.getString(idx);
				cursor.close();
			}
			// 跳转到图片裁剪页面
			FaceUtil.cropPicture(this, Uri.fromFile(new File(fileSrc)));
		} else if (requestCode == REQUEST_CAMERA_IMAGE) {
			if (null == mPictureFile) {
				return;
			}

			fileSrc = mPictureFile.getAbsolutePath();
			updateGallery(fileSrc);
			// 跳转到图片裁剪页面
			FaceUtil.cropPicture(this, Uri.fromFile(new File(fileSrc)));
		} else if (requestCode == FaceUtil.REQUEST_CROP_IMAGE) {


			// 获取返回数据
			Bitmap bmp = data.getParcelableExtra("data");
			// 若返回数据不为null，保存至本地，防止裁剪时未能正常保存
			if (null != bmp) {
				FaceUtil.saveBitmapToFile(RegisterActivity.this, bmp);
			}
			// 获取图片保存路径
			fileSrc = FaceUtil.getImagePath(RegisterActivity.this);
			String[] arrPath = fileSrc.split("\\/");
			userImage = arrPath[arrPath.length - 1];
			Log.i("pony_log", userImage);
			Log.i("pony_log", arrPath[arrPath.length - 1]);
			// 获取图片的宽和高
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			mImage = BitmapFactory.decodeFile(fileSrc, options);

			// 压缩图片
			options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max((double) options.outWidth / 1024f, (double) options.outHeight / 1024f)));
			options.inJustDecodeBounds = false;
			mImage = BitmapFactory.decodeFile(fileSrc, options);

			// 若mImageBitmap为空则图片信息不能正常获取
			if (null == mImage) {
				return;
			}

			// 部分手机会对图片做旋转，这里检测旋转角度
			int degree = FaceUtil.readPictureDegree(fileSrc);
			if (degree != 0) {
				// 把图片旋转为正的方向
				mImage = FaceUtil.rotateImage(degree, mImage);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 可根据流量及网络状况对图片进行压缩
			mImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			mImageData = baos.toByteArray();

			((ImageView) findViewById(R.id.mivUserImg)).setImageBitmap(mImage);

			FinalHttp finalHttp = new FinalHttp();
			// 文件上传到服务器的位置

			AjaxParams params = new AjaxParams();
			// 获取要上传的本地资源
			try {
				params.put("userImage", new File(fileSrc));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			finalHttp.post(Consts.URL + Consts.APP.RegisterAction + "?action_flag=updateUserImage", params, new AjaxCallBack<Object>() {
				@Override
				public void onStart() {
					// mbtnAdd.setText("开始上传");
					super.onStart();
				}

				@Override
				public void onSuccess(Object o) {
					// mbtnAdd.setText("上传成功");
					ToastUtil.show(RegisterActivity.this, "上传成功");
					// updateUser(false);
					super.onSuccess(o);
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// mbtnAdd.setText("上传失败");
					super.onFailure(t, errorNo, strMsg);
				}
			});

		}

	}

	private void updateGallery(String filename) {
		MediaScannerConnection.scanFile(this, new String[]{filename}, null, new MediaScannerConnection.OnScanCompletedListener() {

			@Override
			public void onScanCompleted(String path, Uri uri) {

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
}
