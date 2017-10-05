package com.popland.pop.imageprocesser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BitmapResizer extends AppCompatActivity {
ImageView originalIV;
    LinearLayout LL;
    EditText edtWidth, edtHeight;
    Button btnResize;
    Bitmap originalBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_resizer);
        originalIV = (ImageView)findViewById(R.id.originalIV);
        edtWidth = (EditText)findViewById(R.id.edtRong);
        edtHeight = (EditText)findViewById(R.id.edtCao);
        btnResize = (Button)findViewById(R.id.btnResize);
        LL = (LinearLayout)findViewById(R.id.linearLayout);
        originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.talk1515);
        originalIV.setImageBitmap(originalBitmap);

        btnResize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = Integer.parseInt(edtWidth.getText().toString());
                int height = Integer.parseInt(edtHeight.getText().toString());
                ImageView iv = new ImageView(BitmapResizer.this);
                iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                iv.setImageBitmap(resizeBitmap2(originalBitmap,width,height));
                LL.addView(iv);
            }
        });

        //        for(int i=2;i<10;++i)
//            addScaledBitmap(i);
//  addScaledBitmap1(1,200,200);
    }

    public void addScaledBitmap1(int inSampleSize,int desiredWidth,int desiredHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// query input Bitmap without memory allocation
        Bitmap firstBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.god,options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        Log.i("ORIGINALbitmap",srcWidth+"--"+srcHeight);
//        if(srcWidth/2 > desiredWidth){
//            srcWidth /= 2;
//            srcHeight /= 2;
//            inSampleSize *=2;
//        }
        float scaleWidth = (float) desiredWidth/srcWidth;
        float scaleHeight = (float) desiredHeight/srcHeight;

        options.inSampleSize = inSampleSize;
        options.inDither = false;
        options.inScaled = false;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap sampleBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.god,options);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap scaledBitmap = Bitmap.createBitmap(sampleBitmap,0,0,sampleBitmap.getWidth(),sampleBitmap.getHeight(),matrix,true);
        ImageView iv = new ImageView(BitmapResizer.this);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                         LinearLayout.LayoutParams.WRAP_CONTENT));
        iv.setImageBitmap(scaledBitmap);
        LL.addView(iv);
    }

    public Bitmap resizeBitmap2(Bitmap bitmap,int newWidth,int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth,newHeight, Bitmap.Config.ARGB_8888);
        Toast.makeText(BitmapResizer.this,bitmap.getWidth()+"--"+bitmap.getHeight(),Toast.LENGTH_LONG).show();
        float ratioX = newWidth/ (float)bitmap.getWidth();
        float ratioY = newHeight/ (float)bitmap.getHeight();
        float middleX = newWidth/ 2.0f;
        float middleY = newHeight/ 2.0f;

        Matrix matrix = new Matrix();
        matrix.setScale(ratioX,ratioY,middleX,middleY);//(ratioXY;PivotXY) pivot: fixed point

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(matrix);
        //drawBitmap(bitmap,determine top left corner of bitmap, Paint)
        canvas.drawBitmap(bitmap,middleX - bitmap.getWidth()/2,middleY - bitmap.getHeight()/2,new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
}
