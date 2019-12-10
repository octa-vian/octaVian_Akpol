package gmedia.net.id.OnTime;


import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private Camera.CameraInfo info;
	private Context context = getContext();
	int cameraId = 0;
	public static int rotasiLayar = 0;
	private Camera.Size mPreviewSize;
	private List<Camera.Size> mSupportedPreviewSizes;
	private boolean previewIsRunning;

	@SuppressWarnings("deprecation")
	public Preview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		mHolder = getHolder();
		info = new Camera.CameraInfo();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			if (mCamera != null) {
				Camera.Parameters params = mCamera.getParameters();
				mSupportedPreviewSizes = params.getSupportedPreviewSizes();
				mCamera.setPreviewDisplay(holder);
			}
		} catch (IOException exception) {
			Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//		myStartPreview();
		if (mHolder.getSurface() == null) {
			return;
		}
		try {
//				mCamera.stopPreview();
			//set the focusable true
//			this.setFocusable(true);
			//set the touch able true
//			this.setFocusableInTouchMode(true);
//			mCamera.setDisplayOrientation(90);
			setCameraDisplayOrientation(((Activity) context), cameraId, mCamera);
//			mCamera.setDisplayOrientation(getCorrectCameraOrientation(info, mCamera));
			Camera.Parameters params = mCamera.getParameters();
//			List<Camera.Size> sizes = params.getSupportedPreviewSizes();
			if (mPreviewSize != null) {
				params.setPreviewSize(mPreviewSize.width,
						mPreviewSize.height);
			}
			requestLayout();
			/*Camera.Size optimalSize = getOptimalPreviewSize(sizes, w, h);
			params.setPreviewSize(optimalSize.width, optimalSize.height);*/
			mCamera.setParameters(params);
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
			Open_front_camera.safeToTakePicture = true;

		} catch (Exception e) {
		}
        /*try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


	}

	public void surfaceDestroyed(SurfaceHolder holder) {
//		myStopPreview();
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int width = resolveSize(getSuggestedMinimumWidth(),
				widthMeasureSpec);
		final int height = resolveSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		setMeasuredDimension(width, height);

		if (mSupportedPreviewSizes == null && mCamera != null) {
			mSupportedPreviewSizes = mCamera.getParameters()
					.getSupportedPreviewSizes();
		}
		if (mSupportedPreviewSizes != null) {
			mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width,
					height);
		}

	}

	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {

		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) h / w;

		if (sizes == null) return null;

		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public static void setCameraDisplayOrientation(Activity activity,
												   int cameraId, android.hardware.Camera camera) {

		android.hardware.Camera.CameraInfo info =
				new android.hardware.Camera.CameraInfo();

		android.hardware.Camera.getCameraInfo(cameraId, info);

		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				rotasiLayar = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				rotasiLayar = 1;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				rotasiLayar = 2;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				rotasiLayar = 3;
				break;
		}
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	public int getCorrectCameraOrientation(CameraInfo info, Camera camera) {

		int rotation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break;

			case Surface.ROTATION_90:
				degrees = 90;
				break;

			case Surface.ROTATION_180:
				degrees = 180;
				break;

			case Surface.ROTATION_270:
				degrees = 270;
				break;

		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;
		} else {
			result = (info.orientation - degrees + 360) % 360;
		}
		return result;
	}

	public void myStartPreview() {
		if (!previewIsRunning && (mCamera != null)) {
			mCamera.startPreview();
			previewIsRunning = true;
		}
	}

	// same for stopping the preview
	public void myStopPreview() {
		if (previewIsRunning && (mCamera != null)) {
			mCamera.stopPreview();
			previewIsRunning = false;
		}
	}
	/*@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if (changed && getChildCount() > 0) {
			final View child = getChildAt(0);

			final int width = r - l;
			final int height = b - t;

			int previewWidth = width;
			int previewHeight = height;
			if (mPreviewSize != null) {
				previewWidth = mPreviewSize.height;
				previewHeight = mPreviewSize.width;
			}
			if (previewWidth == 0) {
				previewWidth = 1;
			}
			if (previewHeight == 0) {
				previewHeight = 1;
			}

			// Center the child SurfaceView within the parent.
			if (width * previewHeight > height * previewWidth) {
				final int scaledChildWidth = previewWidth * height
						/ previewHeight;
				child.layout((width - scaledChildWidth) / 2, 0,
						(width + scaledChildWidth) / 2, height);
			} else {
				final int scaledChildHeight = previewHeight * width
						/ previewWidth;
				child.layout(0, (height - scaledChildHeight) / 2, width,
						(height + scaledChildHeight) / 2);
			}
		}
	}*/
}

