package com.windern.cleanmvp.presentation.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.windern.cleanmvp.R;

import timber.log.Timber;

/**
 * Created by wenxinlin on 2016/12/16.
 */

public class DragLayout extends ViewGroup {
    private int lineColor = Color.RED;
    private float lineWidth = 10;
    private Paint paint;

    private int selectViewIndex = -1;
    private int selectViewLastX = 0;
    private int selectViewLastY = 0;

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray tr = context.obtainStyledAttributes(attrs, R.styleable.DragLayout, 0, 0);
        lineColor = tr.getColor(R.styleable.DragLayout_lineColor, Color.RED);
        lineWidth = tr.getDimension(R.styleable.DragLayout_lineWidth, 10);

        tr.recycle();

        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray tr = context.obtainStyledAttributes(attrs, R.styleable.DragLayout, 0, 0);
        lineColor = tr.getColor(R.styleable.DragLayout_lineColor, Color.RED);
        lineWidth = tr.getDimension(R.styleable.DragLayout_lineWidth, 10);

        tr.recycle();

        init();
    }

    private void init() {
        //设置可以调用viewgroup的ondraw，默认true
        setWillNotDraw(false);

        paint = new Paint();
        //去掉锯齿
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //让子控件计算宽高
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int sumWidth = 0;
        int sumHeight = 0;
        int itemMargin = 20;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            //计算childView的left,top,right,bottom
            int lc = l + 0 + sumWidth;
            int tc = t + 0 + sumHeight;
            int rc = lc + view.getMeasuredWidth();
            Timber.d("view.getMeasuredWidth():%s", view.getMeasuredWidth());
            int bc = tc + view.getMeasuredWidth();
            view.layout(lc, tc, rc, bc);

            sumWidth += itemMargin;
            sumHeight += view.getMeasuredHeight() + itemMargin;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startx = 0;
        float starty = 0;
        float stopx = 0;
        float stopy = 0;

        View view0 = getChildAt(0);
        View view1 = getChildAt(1);

        startx = view0.getX() + view0.getWidth() / 2;
        starty = view0.getY() + view0.getHeight() / 2;

        stopx = view1.getX() + view1.getWidth() / 2;
        stopy = view1.getY() + view1.getHeight() / 2;

        canvas.drawLine(startx, starty, stopx, stopy, paint);
    }

    /**
     * 监听内部滑动，从监听子控件到监听父控件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < getChildCount(); i++) {
                    View view = getChildAt(i);
                    if (x >= view.getLeft() && y >= view.getTop()
                            && x <= view.getRight()
                            && y <= view.getBottom()) {
                        selectViewIndex = i;
                        selectViewLastX = x;
                        selectViewLastY = y;
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int offx = x - selectViewLastX;
                int offy = y - selectViewLastY;
                if (selectViewIndex != -1) {
                    View selectView = getChildAt(selectViewIndex);
                    selectView.layout(selectView.getLeft() + offx, selectView.getTop() + offy
                            , selectView.getRight() + offx, selectView.getBottom() + offy);

                    selectViewLastX = x;
                    selectViewLastY = y;
                }
                //不主动调用的话，画的线段是断断续续的
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                selectViewIndex = -1;
                selectViewLastX = 0;
                selectViewLastY = 0;
                selectViewLastY = 0;
                break;
        }
        return true;
    }
}
