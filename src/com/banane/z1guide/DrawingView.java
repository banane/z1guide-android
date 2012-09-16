package com.banane.z1guide;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.banane.z1guide.ReactDrawActivity.PaintMode;

/**
 * This view implements the drawing canvas.
 *
 * It handles all of the input events and drawing functions.
 */
public class DrawingView extends View {
    private static final int FADE_ALPHA = 0x06;
    private static final int MAX_FADE_STEPS = 256 / FADE_ALPHA + 4;
    private static final int TRACKBALL_SCALE = 10;

    private static final int SPLAT_VECTORS = 40;

    private final Random mRandom = new Random();
    public Bitmap mBitmap;
    private Canvas mCanvas;
    private final Paint mPaint;
    private final Paint mFadePaint;
    private float mCurX;
    private float mCurY;
    private int mOldButtonState;
    private int mFadeSteps = MAX_FADE_STEPS;
    
    /** The index of the current color to use. */
    int mColorIndex = 1;
    
    static final int[] COLORS = new int[] {
        Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN,
        Color.CYAN, Color.BLUE, Color.MAGENTA, Color.BLACK
    };

    public DrawingView(Context c) {
        super(c);
        setFocusable(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mFadePaint = new Paint();
        mFadePaint.setColor(Color.BLACK);
        mFadePaint.setAlpha(FADE_ALPHA);
    }

    public void clear() {
        if (mCanvas != null) {
            mPaint.setColor(Color.WHITE);
            mCanvas.drawPaint(mPaint);
            invalidate();

            mFadeSteps = MAX_FADE_STEPS;
        }
    }

    public void fade() {
        if (mCanvas != null && mFadeSteps < MAX_FADE_STEPS) {
            mCanvas.drawPaint(mFadePaint);
            invalidate();

            mFadeSteps++;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int curW = mBitmap != null ? mBitmap.getWidth() : 0;
        int curH = mBitmap != null ? mBitmap.getHeight() : 0;
        if (curW >= w && curH >= h) {
            return;
        }

        if (curW < w) curW = w;
        if (curH < h) curH = h;

        Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);
        if (mBitmap != null) {
            newCanvas.drawBitmap(mBitmap, 0, 0, null);
        }
        mBitmap = newBitmap;
        mCanvas = newCanvas;
        mFadeSteps = MAX_FADE_STEPS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        final int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            // Advance color when the trackball button is pressed.
 //           advanceColor();
        }

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            final int N = event.getHistorySize();
            final float scaleX = event.getXPrecision() * TRACKBALL_SCALE;
            final float scaleY = event.getYPrecision() * TRACKBALL_SCALE;
            for (int i = 0; i < N; i++) {
                moveTrackball(event.getHistoricalX(i) * scaleX,
                        event.getHistoricalY(i) * scaleY);
            }
            moveTrackball(event.getX() * scaleX, event.getY() * scaleY);
        }
        return true;
    }

    private void moveTrackball(float deltaX, float deltaY) {
        final int curW = mBitmap != null ? mBitmap.getWidth() : 0;
        final int curH = mBitmap != null ? mBitmap.getHeight() : 0;

        mCurX = Math.max(Math.min(mCurX + deltaX, curW - 1), 0);
        mCurY = Math.max(Math.min(mCurY + deltaY, curH - 1), 0);
        paint(PaintMode.Draw, mCurX, mCurY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onTouchOrHoverEvent(event, true /*isTouch*/);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return onTouchOrHoverEvent(event, false /*isTouch*/);
    }

    private boolean onTouchOrHoverEvent(MotionEvent event, boolean isTouch) {
/*        
 * final int buttonState = event.getActionmasked
        int pressedButtons = buttonState & ~mOldButtonState;
        mOldButtonState = buttonState;

        if ((pressedButtons & MotionEvent.BUTTON_SECONDARY) != 0) {
            // Advance color when the right mouse button or first stylus button
            // is pressed.
            advanceColor();
        }

        PaintMode mode;
        if ((buttonState & MotionEvent.BUTTON_TERTIARY) != 0) {
            // Splat paint when the middle mouse button or second stylus button is pressed.
            mode = PaintMode.Splat;
        } else if (isTouch || (buttonState & MotionEvent.BUTTON_PRIMARY) != 0) {
            // Draw paint when touching or if the primary button is pressed.
            mode = PaintMode.Draw;
        } else {
            // Otherwise, do not paint anything.
            return false;
        }*/
    	PaintMode mode = PaintMode.Draw;

        final int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE
                || action == MotionEvent.ACTION_HOVER_MOVE) {
            final int N = event.getHistorySize();
            final int P = event.getPointerCount();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < P; j++) {
                    paint(mode,
                            event.getHistoricalX(j, i),
                            event.getHistoricalY(j, i),
                            event.getHistoricalPressure(j, i),
                            event.getHistoricalTouchMajor(j, i),
                            event.getHistoricalTouchMinor(j, i),
                            event.getHistoricalOrientation(j, i));
                }
            }
            for (int j = 0; j < P; j++) {
                paint(mode,
                        event.getX(j),
                        event.getY(j),
                        event.getPressure(j),
                        event.getTouchMajor(j),
                        event.getTouchMinor(j),
                        event.getOrientation(j));
            }
            mCurX = event.getX();
            mCurY = event.getY();
        }
        return true;
    }

 

    private void paint(PaintMode mode, float x, float y) {
        paint(mode, x, y, 1.0f, 0, 0, 0);
    }

    private void paint(PaintMode mode, float x, float y, float pressure,
            float major, float minor, float orientation) {
        if (mBitmap != null) {
            if (major <= 0 || minor <= 0) {
                // If size is not available, use a default value.
                major = minor = 16;
            }

            mPaint.setColor(COLORS[mColorIndex]);
            mPaint.setAlpha(Math.min((int)(pressure * 128), 255));
            drawOval(mCanvas, x, y, major, minor, orientation, mPaint);
        }
        mFadeSteps = 0;
        invalidate();
    }


    private final RectF mReusableOvalRect = new RectF();
    private void drawOval(Canvas canvas, float x, float y, float major, float minor,
            float orientation, Paint paint) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate((float) (orientation * 180 / Math.PI), x, y);
        mReusableOvalRect.left = x - minor / 2;
        mReusableOvalRect.right = x + minor / 2;
        mReusableOvalRect.top = y - major / 2;
        mReusableOvalRect.bottom = y + major / 2;
        canvas.drawOval(mReusableOvalRect, paint);
        canvas.restore();
    }
}