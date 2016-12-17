package com.windern.cleanmvp.presentation.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.windern.cleanmvp.R;

import java.util.Iterator;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by wenxinlin on 2016/12/16.
 */

public class DragLayout extends ViewGroup {
    private int lineColor = Color.RED;
    private float lineWidth = 10;
    private Paint paint;

    private String relations;

    private int selectViewIndex = -1;
    private int selectViewLastX = 0;
    private int selectViewLastY = 0;

    public void setRelations(String relations) {
        this.relations = relations;
    }

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
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            DragLayoutParams params = (DragLayoutParams) (view.getLayoutParams());
            int posX = (int) params.posX;
            int posY = (int) params.posY;

            //计算childView的left,top,right,bottom
            int lc = l + posX;
            int tc = t + posY;
            int rc = lc + view.getMeasuredWidth();
            int bc = tc + view.getMeasuredWidth();
            view.layout(lc, tc, rc, bc);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startx = 0;
        float starty = 0;
        float stopx = 0;
        float stopy = 0;

        if (!TextUtils.isEmpty(relations)) {
            String[] lines = relations.split(";");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                String[] keyValue = line.split("-");
                if (keyValue.length != 2) {
                    continue;
                }
                String keyStr = keyValue[0];
                String valueStr = keyValue[1];
                if (TextUtils.isEmpty(keyStr)) {
                    continue;
                }
                if (TextUtils.isEmpty(valueStr)) {
                    continue;
                }
                int key = Integer.valueOf(keyStr);
                int value = Integer.valueOf(valueStr);
                if (key < 0 || key >= getChildCount()) {
                    continue;
                }
                if (value < 0 || value >= getChildCount()) {
                    continue;
                }

                View startView = getChildAt(key);
                View endView = getChildAt(value);
                startx = startView.getX() + startView.getWidth() / 2;
                starty = startView.getY() + startView.getHeight() / 2;

                stopx = endView.getX() + endView.getWidth() / 2;
                stopy = endView.getY() + endView.getHeight() / 2;

                canvas.drawLine(startx, starty, stopx, stopy, paint);
            }
        }
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

    /**
     * 覆盖重新获取
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DragLayoutParams(getContext(), attrs);
    }

    /**
     * 自定义的layoutparams
     */
    public static class DragLayoutParams extends ViewGroup.MarginLayoutParams {
        public float posX;
        public float posY;

        public DragLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray tr = c.obtainStyledAttributes(attrs, R.styleable.DragLayoutParams);
            posX = tr.getDimension(R.styleable.DragLayoutParams_posX, 0);
            posY = tr.getDimension(R.styleable.DragLayoutParams_posY, 0);

            tr.recycle();
        }
    }
}
