package vis.custom.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Vision on 15/5/23.
 * Email:Vision.lsm.2012@gmail.com
 */
public class VHSVKer extends HorizontalScrollView {
    private Context context;
    private OnScrollChangedListener mOnScrollChangedListener;
    private OnClickListener mOnClickListener;
    private ImageView[] imageViews;

    /**
     * HorizontalScrollView 一半宽度
     */
//    private int hphsv;
    /**
     * ImageView 宽度
     */
    private int ivw;
    /**
     * ImageView 一半宽度
     */
//    private int hpivw;
    /**
     * current item 当前元素下标
     */
    private int num;
    //    private LinearLayout mLinearlayout;
    /**
     * 暂停自动滚动
     */
    private boolean isPause = false;
    /**
     * 图片资源
     */
    private int[] imageRid;

    /**
     * 元素个数
     */
    private int count;
    /**
     * 显示长度
     */
    private int displayLength;
    /**
     * 缩放步进
     */
    private float zoomStepper;
    /**
     * 延迟时间
     */
    private int delay;

    private Timer timer;
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            goNext();
        }
    };

    public VHSVKer(Context context) {
        super(context);
        this.context = context;
//        initView();
    }

    public VHSVKer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
//        initView();
    }

    public VHSVKer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
//        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (ivw != 0)
            return;
        /**
         * HorizontalScrollView 宽度
         */
        int hsv = getWidth();
//        hphsv = hsv / 2;
        ivw = hsv / displayLength;
//        hpivw = ivw / 2 - 1;

        LinearLayout mLinearlayout = (LinearLayout) getChildAt(0);
        if (mLinearlayout.getChildCount() > 0)
            mLinearlayout.removeAllViews();
        imageViews = new ImageView[count + displayLength];
        for (int i = 0; i < count + displayLength; i++) {
            imageViews[i] = new ImageView(context);
            imageViews[i].setImageResource(imageRid[i % count]);
            imageViews[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews[i]
                    .setLayoutParams(new LinearLayout.LayoutParams(ivw,
                            ivw));
            mLinearlayout.addView(imageViews[i]);
        }
        if (timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new ChangeTasker(), 0, this.delay);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int current = getScrollX() % ivw;
        float tar = current * zoomStepper / (ivw - 1);//0~zoomStepper
        int j = 0, i = 0;           //用默认5个来做
        for (; i < displayLength / 2; i++, j++) {
            if (0 != tar) {
                if (num == 0) {
                    setImageScale(imageViews[count + j], 0.7f + ((i + 1) * zoomStepper) - tar);
                }
                //第二个   0.85    0.7     tar  0.
                setImageScale(imageViews[num + 0 + j], 0.7f + ((i + 1) * zoomStepper) - tar);
//            Log.d("-----", (num + 1 + j) + "," + tar + "," + (0.7f + ((i + 1) * zoomStepper) - tar));
            } else
                setImageScale(imageViews[num + 1 + j], 0.7f + ((i + 1) * zoomStepper) - tar);
        }
        for (; i > 0; i--, j++) {
            if (0 != tar) {
                if (num == 0) {
                    setImageScale(imageViews[count + j], 0.7f + ((i - 1) * zoomStepper) + tar);
                }
                setImageScale(imageViews[num + 0 + j], 0.7f + ((i - 1) * zoomStepper) + tar);
            } else
                setImageScale(imageViews[num + 1 + j], 0.7f + ((i - 1) * zoomStepper) + tar);
        }
        setImageScale(imageViews[num + 1 + j], 0.7f);
        if (mOnScrollChangedListener != null)
            mOnScrollChangedListener.onScrollChanged();
    }

    public void setOnClickListener(VHSVKer.OnClickListener l) {
        this.mOnClickListener = l;
    }

    public void setOnScrollChangedListener(OnScrollChangedListener l) {
        this.mOnScrollChangedListener = l;
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isPause = true;
                break;
            }
            case MotionEvent.ACTION_UP: {
                int realValue;// = num + (int) motionEvent.getX() / ivw;
                realValue = num + (int) motionEvent.getX() / ivw;
                if (mOnClickListener != null)
                    mOnClickListener.onClick(realValue % count);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setViews(int[] imageRid, int displayLength, int delay) {
        this.imageRid = imageRid;
        this.count = imageRid.length;
        this.displayLength = displayLength;
        this.zoomStepper = (float) (0.3 / (displayLength / 2));
        this.delay = delay;
    }

    public int getCurrentItem() {
        return (num + (displayLength / 2)) % count;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged();
    }

    public interface OnClickListener {
        void onClick(int SelectedItem);
    }

    protected void setImageScale(ImageView iv, float f) {
        iv.setScaleX(f);
        iv.setScaleY(f);
    }

    public void goNext() {
        if (0 == num) {
            scrollTo(0, 0);
        }
        num++;
        smoothScrollTo(num * ivw, 0);
        if (count == num)
            num = 0;
//        Log.d("-----num:", num + "");
    }

    private class ChangeTasker extends TimerTask {
        private Message message;

        @Override
        public void run() {
//            if (isPause) {
//                isPause = false;
//                return;
//            }
            message = myHandler.obtainMessage();
            myHandler.sendMessage(message);
        }
    }


}
