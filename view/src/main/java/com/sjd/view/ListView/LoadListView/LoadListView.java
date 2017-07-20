package com.sjd.view.ListView.LoadListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.view.BuildConfig;
import com.view.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sjd on 2017/2/19.
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener {

    public LoadListView(Context context) {
        this(context, null);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    View header;
    int headerHeight;

    /**
     * 初始化页面，添加顶部布局文件到listView
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        header = layoutInflater.inflate(R.layout.loadlistview_header, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }

    /**
     * 通知父布局 占据多少地方
     *
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
    }

    int firstVisbleItem;//当前第一个可以见item的编号
    boolean isTopRemark;
    int startY;//按下的Y坐标
    int state;//标记当前状态
    int scrollState;//当前滚动状态
    final int NoNe = 0;//正常状态
    final int PULL = 1;//提示下拉刷新
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//正在刷新

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        scrollState = i;
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisbleItem == 0) {
                    isTopRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    reflashViewByState();
                    //添加数据
                    iReflash.onReflash();
                } else if (state == PULL) {
                    state = NoNe;
                    isTopRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //移动过程
    private void onMove(MotionEvent ev) {
        if (!isTopRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state) {
            case NoNe:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > (headerHeight + 30) && (scrollState == SCROLL_STATE_TOUCH_SCROLL)) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space <= 0) {
                    state = NoNe;
                    reflashViewByState();
                    isTopRemark = false;
                    reflashViewByState();
                } else if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case REFLASHING:
                break;
        }
    }

    private void reflashViewByState() {
        TextView tipTextView = (TextView) header.findViewById(R.id.tipTextView);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar headerProgressbar = (ProgressBar) header.findViewById(R.id.header_progressbar);

        RotateAnimation roate = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.f, RotateAnimation.RELATIVE_TO_SELF, 0.f);
        roate.setDuration(500);
        roate.setFillAfter(true);
        RotateAnimation roate1 = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.f, RotateAnimation.RELATIVE_TO_SELF, 0.f);
        roate1.setDuration(500);
        roate1.setFillAfter(true);

        switch (state) {
            case NoNe:
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(VISIBLE);
                headerProgressbar.setVisibility(GONE);
                tipTextView.setText("下拉可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(roate1);
                break;
            case RELESE:
                arrow.setVisibility(VISIBLE);
                headerProgressbar.setVisibility(GONE);
                tipTextView.setText("松开可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(roate);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(GONE);
                headerProgressbar.setVisibility(VISIBLE);
                tipTextView.setText("正在刷新！");
                arrow.clearAnimation();
                break;
        }
    }

    public void reflashComplete(){
        state = NoNe;
        isTopRemark = false;
        reflashViewByState();
        TextView lastUpdateTime = (TextView) header.findViewById(R.id.lastUpdateTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date  date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastUpdateTime.setText(time);
    }

    IReflashListener iReflash;
    public  void setInterface(IReflashListener ireflash){
        iReflash = ireflash;
    }
    /**
     * 刷新数据接口
     */
    public interface  IReflashListener{
        public void onReflash();
    }
}
