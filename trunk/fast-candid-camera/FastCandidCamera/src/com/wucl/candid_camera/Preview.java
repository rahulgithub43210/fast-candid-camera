package com.wucl.candid_camera;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * ʵ�����㶯��
 * @author wuchenliang
 *
 */
class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private Camera camera;
	private ImageView ivFocus;
	private Context context;
	CameraPreview cameraPreview;
	// ����һ��PictureCallback���󣬲�ʵ�����е�onPictureTaken����
	private PictureCallback pictureCallback = new PictureCallback() {
		// �÷������ڴ�����������Ƭ����
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			// data����ֵ������Ƭ���ݣ�����Щ������key-value��ʽ���棬�Ա��������ø�Activity�ĳ����
			// �Ի����Ƭ����
			cameraPreview.getIntent().putExtra("bytes", data);
			cameraPreview.setResult(20, cameraPreview.getIntent());
			// ֹͣ��Ƭ����
			camera.stopPreview();
			camera = null;
			// �رյ�ǰ��Activity
			cameraPreview.finish();
		}
	};

	// Preview��Ĺ��췽��
	public Preview(Context context) {
		super(context);
		// ���SurfaceHolder����
		holder = getHolder();
		// ָ�����ڲ�׽�����¼���SurfaceHolder.Callback����
		holder.addCallback(this);
		// ����SurfaceHolder���������
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		
		

	}

	public Preview(Context context, CameraPreview cameraPreview, ImageView ivFocus) {
		super(context);
		// ���SurfaceHolder����
		holder = getHolder();
		// ָ�����ڲ�׽�����¼���SurfaceHolder.Callback����
		holder.addCallback(this);
		// ����SurfaceHolder���������
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.cameraPreview = cameraPreview;
		this.context = context;
		this.ivFocus = ivFocus;
		
		
	}

	// ��������ʱ���ø÷���
	public void surfaceCreated(SurfaceHolder holder) {
		// ���Camera����
		camera = Camera.open();
		try {

			// ����������ʾ���������SurfaceHolder����
			camera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			// �ͷ��ֻ�����ͷ
			camera.release();
			camera = null;
		}
	}

	// ֹͣ����ʱ���ø÷���
	public void surfaceDestroyed(SurfaceHolder holder) {
		// �ͷ��ֻ�����ͷ
		camera.stopPreview();
		camera.release();
	}

	// ����״̬�仯ʱ���ø÷���
	public void surfaceChanged(SurfaceHolder holder, int format, int w,
			int h) {
		try {
			Camera.Parameters parameters = camera.getParameters();
			// ������Ƭ��ʽ
			parameters.setPictureFormat(PixelFormat.JPEG);
			// ������Ļ��������Ԥ䯳ߴ�
			if (((Activity) context).getWindowManager().getDefaultDisplay().getOrientation() == 0)
				parameters.setPreviewSize(h, w);
			else
				parameters.setPreviewSize(w, h);
			// ����������Ƭ��ʵ�ʷֱ��ʣ��ڱ����еķֱ�����1024*768
			// ������320*240,1024*768�쳣
			parameters.setPictureSize(320, 240);
			// ���ñ����ͼ���С
			camera.setParameters(parameters);
			// ��ʼ����
			camera.startPreview();
			// ׼�����ڱ�ʾ�Խ�״̬��ͼ������ͼ14.7��ʾ�ĶԽ����ţ�
			ivFocus.setImageResource(R.drawable.focus1);
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			ivFocus.setScaleType(ScaleType.CENTER);
			((Activity) context).addContentView(ivFocus, layoutParams);
			ivFocus.setVisibility(VISIBLE);
			// �Զ��Խ�
			camera.autoFocus(new AutoFocusCallback() {
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					if (success) {
						// successΪtrue��ʾ�Խ��ɹ����ı�Խ�״̬ͼ��һ����ɫ��pngͼ��
						ivFocus.setImageResource(R.drawable.focus2);
					}
				}
			});
		} catch (Exception e) {
			camera.release();
		}
	}

	// ֹͣ���գ������������Ƭ����PictureCallback�ӿڵ�onPictureTaken����
	public void takePicture() {
		if (camera != null) {
			camera.takePicture(null, null, pictureCallback);
		} else {
			Toast.makeText(getContext(), "camera==null", Toast.LENGTH_SHORT);
		}
	}
}