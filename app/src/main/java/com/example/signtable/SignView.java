package com.example.signtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

//畫圖版
public class SignView extends View {
    private String TAG = this.getClass().getName();
    private Path mPath = new Path();
    private Paint mPaint = new Paint();
    //點陣圖的繪製內容輸出者：畫布
    private Canvas mCanvas;

    //用來存放繪製內容的點陣圖
    private Bitmap mCacheBitmap;

    //上下文
    private Context mContext;

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint.setStyle(Paint.Style.STROKE);
//        Log.e(TAG, " mPaint.getStrokeWidth() = " + mPaint.getStrokeWidth());//預設描邊寬度是0，但是真正繪製時依然有一個畫素的寬度
        mPaint.setStrokeWidth(10);//設定描邊寬度，也就是筆跡的粗細
        Log.e(TAG, " mPaint.getStyle() = " + mPaint.getStyle());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCacheBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mCacheBitmap);
        //mCanvas.drawColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                Log.e(TAG, "ACTION_DOWN getX = " + event.getX() + " getY = " + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                Log.e(TAG, "ACTION_MOVE getX = " + event.getX() + " getY = " + event.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP getX = " + event.getX() + " getY = " + event.getY());
                break;
        }
        //真正負責繪製簽名筆跡的畫布，在這裡接收路徑mPath，以及事先定義好的顏料
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw canvas = " + canvas);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 清空
     */
    public void clear() {
        Log.e(TAG, "clear()");
        if (mPath != null) {
            Log.e(TAG, "before clear mPath isEmpty => " + mPath.isEmpty());
            mPath.reset();
            //mCanvas.drawColor(Color.WHITE);
            Log.e(TAG, "after clear mPath isEmpty => " + mPath.isEmpty());
            invalidate();
        }
    }

    public void save() {
        try {
            //建立一個檔案用於存放圖片
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/321.png");
            if (file.exists()) {
                file.delete();
            }
            OutputStream outputStream = null;

            //輸出到這個檔案
            outputStream = new FileOutputStream(file);

            //壓縮形成輸出流
            mCacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            Toast.makeText(mContext, "儲存成功!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "儲存異常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

