package com.clientBase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridviewForScrollView extends GridView {
    public GridviewForScrollView(Context context) {
        super(context);
    }

    public GridviewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridviewForScrollView(Context context, AttributeSet attrs,
        int defStyle) {
        super(context, attrs, defStyle);
    }
        
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}