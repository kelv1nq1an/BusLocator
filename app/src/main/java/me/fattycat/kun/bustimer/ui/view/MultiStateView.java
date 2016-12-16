package me.fattycat.kun.bustimer.ui.view;

/**
 * Author: qk329
 * Date: 2016/12/12
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.fattycat.kun.bustimer.R;


/**
 * Created by xdj on 16/2/3.
 * 多状态视图
 */
public class MultiStateView extends FrameLayout {

    private static final String TAG = MultiStateView.class.getSimpleName();

    public static final int STATE_CONTENT = 10001;
    public static final int STATE_LOADING = 10002;
    public static final int STATE_EMPTY   = 10003;
    public static final int STATE_FAIL    = 10004;

    private SparseArray<View> mStateViewArray = new SparseArray<>();
    private SparseIntArray    mLayoutIDArray  = new SparseIntArray();
    private View mContentView;
    private int mCurrentState = STATE_CONTENT;
    private OnInflateListener mOnInflateListener;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray   = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        int        resIdEmpty   = typedArray.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        int        resIdLoading = typedArray.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1);
        int        resIdFail    = typedArray.getResourceId(R.styleable.MultiStateView_msv_failView, -1);

        if (resIdEmpty != -1) {
            addViewForStatus(MultiStateView.STATE_EMPTY, resIdEmpty);
        }
        if (resIdFail != -1) {
            addViewForStatus(MultiStateView.STATE_FAIL, resIdFail);
        }
        if (resIdLoading != -1) {
            addViewForStatus(MultiStateView.STATE_LOADING, resIdLoading);
        }

        typedArray.recycle();
    }

    @Override
    public void addView(View child) {
        validContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        validContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        validContentView(child);
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        validContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        validContentView(child);
        super.addView(child, index, params);
    }

    /**
     * 改变视图状态
     *
     * @param state 状态类型
     */
    public void setViewState(int state) {
        if (state != mCurrentState) {
            View view = getView(state);
            getCurrentView().setVisibility(GONE);
            mCurrentState = state;
            if (view != null) {
                view.setVisibility(VISIBLE);
            } else {
                int resLayoutID = mLayoutIDArray.get(state);
                view = LayoutInflater.from(getContext()).inflate(resLayoutID, this, false);
                mStateViewArray.put(state, view);
                addView(view);
                view.setVisibility(VISIBLE);

                if (mOnInflateListener != null) {
                    mOnInflateListener.onInflate(state, view);
                }
            }
        }
    }

    /**
     * 获取当前状态
     *
     * @return 状态
     */
    public int getViewState() {
        return mCurrentState;
    }

    /**
     * 获取指定状态的View
     *
     * @param state 状态类型
     * @return 指定状态的View
     */
    public View getView(int state) {
        return mStateViewArray.get(state);
    }

    /**
     * 获取当前状态的View
     *
     * @return 当前状态的View
     */
    public View getCurrentView() {
        View view = getView(mCurrentState);
        if (view == null && mCurrentState == STATE_CONTENT) {
            throw new NullPointerException("content is null");
        } else if (view == null) {
            throw new NullPointerException("current state view is null, state = " + mCurrentState);
        }
        return getView(mCurrentState);
    }

    public void addViewForStatus(int status, int resLayoutID) {
        mLayoutIDArray.put(status, resLayoutID);
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        mOnInflateListener = onInflateListener;
    }

    private boolean isValidContentView(View view) {
        return mContentView == null && mStateViewArray.indexOfValue(view) == -1;
    }

    /**
     * 检查当前view是否为content
     */
    private void validContentView(View view) {
        if (isValidContentView(view)) {
            mContentView = view;
            mStateViewArray.put(STATE_CONTENT, view);
        } else if (mCurrentState != STATE_CONTENT) {
            mContentView.setVisibility(GONE);
        }
    }

    public interface OnInflateListener {
        void onInflate(int state, View view);
    }
}