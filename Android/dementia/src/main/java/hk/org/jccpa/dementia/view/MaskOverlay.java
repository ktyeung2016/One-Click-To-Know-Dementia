package hk.org.jccpa.dementia.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by thomas.wan on 5/6/2015.
 */
public class MaskOverlay {
    Bitmap mask = null;

    Paint paint = null;

    public MaskOverlay() {
        super();
    }
    public Paint getPaint(){
        if(paint == null){
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        }
        return paint;
    }

    public Bitmap getMask(){
        if(mask == null){
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL_AND_STROKE);
            p.setAntiAlias(true);
            p.setFilterBitmap(true);
            p.setDither(true);
            p.setARGB(178,0,0,0);
            mask = Bitmap.createBitmap(10, 10,
                    Bitmap.Config.ALPHA_8);
            Canvas canvas = new Canvas(mask);
            canvas.drawRect(new RectF(0, 0, 10, 10), p);
        }
        return mask;
    }

    /**
     * return canvas for further set mask
     * @return canvas
     */
    public Bitmap getOverlayBitmap(int w, int h){
        if(w<1 || h<1){
            return null;
        }
        Bitmap overlayBitmap = null;
        if(null == overlayBitmap){
            overlayBitmap = Bitmap.createBitmap(w,h,
                    Bitmap.Config.ALPHA_8);
        }else{
            overlayBitmap = Bitmap.createScaledBitmap(overlayBitmap,w,h, true);
            //overlayBitmap.setWidth(w);
            //overlayBitmap.setHeight(h);
        }
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        p.setARGB(178,0,0,0);

        Canvas mCanvas = new Canvas(overlayBitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mCanvas.drawPaint(p);

        return overlayBitmap;
    }
    public void drawMask(Bitmap dest, Rect r){
        drawMask(dest, r.top, r.left, r.width(), r.height());
    }
    public void drawMask(Bitmap dest, int top, int left, int width, int height){
        drawMask(dest, getMask(), top, left, width, height);
    }
    public void drawMask(Bitmap dest, Bitmap mask, Rect r){
        drawMask(dest, mask, r.top, r.left, r.width(), r.height());
    }
    public void drawMask(Bitmap dest, Bitmap mask, int top, int left, int width, int height){
        if(null == dest){
            return;
        }
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(Bitmap.createScaledBitmap(mask, width, height, true), left, top, getPaint());
    }
}
