package vis.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.verg.hsv.R;

/**
 * Created by Vision on 15/5/27.
 * Email:Vision.lsm.2012@gmail.com
 */
public class VHSV extends LinearLayout {
    private Context context;
    private TextView tvTitleName;
    private VHSVKer mHSV;
    private String[] titleName;

    public VHSV(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public VHSV(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public VHSV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.vhsv, this);
        LinearLayout ll = (LinearLayout) getChildAt(0);
        tvTitleName = (TextView) ll.getChildAt(0);
        mHSV = (VHSVKer) ll.getChildAt(1);
        mHSV.setOnScrollChangedListener(new VHSVKer.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                tvTitleName.setText(String.valueOf(titleName[mHSV.getCurrentItem()]));
            }

        });

    }

    public boolean setWhatINeed(String[] titleName, int[] imageSourceId, int displayLength, int delay) {
        //防止重入
        if (this.titleName != null) {
            Log.e("VHSV", "titleName is Null.");
            return false;
        }
        //检查长度
        if (titleName.length != imageSourceId.length) {      //不相等
            Log.e("VHSV", "titleName's length != imageSourceId's length");
            return false;
        }
        //上限9，下限3,单数,非零
        if (9 < displayLength || displayLength < 3 || displayLength % 2 == 0) {
            Log.e("VHSV", "displayLength overflow");
            return false;
        }

        this.titleName = titleName;
        this.mHSV.setViews(imageSourceId, displayLength, delay);
        return true;
    }


    public void setOnClickListener(VHSVKer.OnClickListener onClickListener) {
        mHSV.setOnClickListener(onClickListener);
    }
}
