package com.windern.cleanmvp.presentation.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.windern.cleanmvp.R;

import timber.log.Timber;

/**
 * Created by wenxinlin on 2016/12/16.
 */

public class DragView extends View {
    private int fillColor = Color.BLUE;
    private int strokeColor = Color.RED;
    private float strokeWidth = 0;
    private Paint paintFill;
    private Paint paintStroke;

    public DragView(Context context) {
        super(context);
        init();
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray tr = context.obtainStyledAttributes(attrs, R.styleable.DragView, 0, 0);
        fillColor = tr.getColor(R.styleable.DragView_fillColor, Color.BLUE);
        strokeColor = tr.getColor(R.styleable.DragView_strokeColor, Color.RED);
        strokeWidth = tr.getDimension(R.styleable.DragView_strokeWidth, 0);

        tr.recycle();

        init();
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray tr = context.obtainStyledAttributes(attrs, R.styleable.DragView, 0, 0);
        fillColor = tr.getColor(R.styleable.DragView_fillColor, Color.BLUE);
        strokeColor = tr.getColor(R.styleable.DragView_strokeColor, Color.RED);
        strokeWidth = tr.getDimension(R.styleable.DragView_strokeWidth, 0);

        tr.recycle();

        init();
    }

    private void init() {
        paintFill = new Paint();
        //去掉锯齿
        paintFill.setAntiAlias(true);
        paintFill.setStyle(Paint.Style.FILL_AND_STROKE);
        paintFill.setColor(fillColor);

        paintStroke = new Paint();
        paintStroke.setAntiAlias(true);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setColor(strokeColor);
        paintStroke.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float rd = width / 2;

        canvas.drawCircle(width / 2, width / 2, rd - strokeWidth, paintFill);
        canvas.drawCircle(width / 2, width / 2, rd - strokeWidth / 2, paintStroke);
    }
}
