package com.example.module_weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.module_weather.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 右侧的字母索引View
 */
public class SideBar extends View {

    public String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private List<String> letterList;
    private int choose = -1;
    private Paint paint = new Paint();
    private TextView mTextDialog;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //根据INDEX_STRING生成字母list
        letterList = Arrays.asList(INDEX_STRING);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();// 获取对应高度
        int width = getWidth();// 获取对应宽度
        int singleHeight = height / letterList.size();// 获取每一个字母的高度
        for (int i = 0; i < letterList.size(); i++) {
            paint.setColor(Color.parseColor("#a9a9a9"));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(22);
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#05c0ab"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(letterList.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight / 2;
            canvas.drawText(letterList.get(i), xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letterList.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.color.alpha);
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.GONE);
                }
                if (c >= 0 && c < letterList.size()) {
                    if (listener != null) {
                        listener.onTouchingLetterChanged(letterList.get(c));
                    }
                }
                break;
            default:
                setBackgroundResource(R.color.background);
                if (oldChoose != c) {
                if (c >= 0 && c < letterList.size()) {
                    if (mTextDialog != null) {
                        mTextDialog.setText(letterList.get(c));
                        mTextDialog.setVisibility(View.VISIBLE);
                    }
                    choose = c;
                    invalidate();
                    if (listener != null) {
                        listener.onTouchingLetterChanging(letterList.get(c));
                    }
                }
            }
                break;
        }
        return true;
    }

    public void setIndexText(ArrayList<String> indexStrings) {
        this.letterList = indexStrings;
        invalidate();
    }

    /**
     * 为SideBar设置显示当前按下的字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public void refresh() {
        init();
        invalidate();
    }

    /**
     * 接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
        void onTouchingLetterChanging(String s);
    }

}

