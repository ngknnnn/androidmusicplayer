package vn.edu.usth.musicplayer.searchBar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import androidx.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import vn.edu.usth.musicplayer.searchBar.LogHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * <p/>
 * Created by khanh nam on 2019/9/12.
 */
public abstract class Controller {
    public static final int STATE_ANIM_NONE = 0;
    public static final int STATE_ANIM_START = 1;
    public static final int STATE_ANIM_STOP = 2;
    public static final int DEFAULT_ANIM_TIME = 500;
    public static final float DEFAULT_ANIM_STARTF = 0;
    public static final float DEFAULT_ANIM_ENDF = 1;

    protected float mPro = -1;
    private WeakReference<View> mSearchView;
    protected float[] mPos = new float[2];

//    public Controller(Context context) {
//    }

    @IntDef({STATE_ANIM_NONE,STATE_ANIM_START, STATE_ANIM_STOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @State
    protected int mState = STATE_ANIM_NONE;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public void setSearchView(View searchView) {
        this.mSearchView = new WeakReference<>(searchView);
    }

    public View getSearchView() {
        return mSearchView != null ? mSearchView.get() : null;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return getSearchView() != null ? getSearchView().getWidth() : 0;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return getSearchView() != null ? getSearchView().getHeight() : 0;
    }

    /**
     *
     * @param canvas
     * @param paint
     */
    public abstract void draw(Canvas canvas, Paint paint);


    public void startAnim() {
    }

    public void resetAnim() {
    }


    public ValueAnimator startSearchViewAnim() {
        ValueAnimator valueAnimator = startSearchViewAnim(DEFAULT_ANIM_STARTF, DEFAULT_ANIM_ENDF,
                DEFAULT_ANIM_TIME);
        return valueAnimator;
    }

    public ValueAnimator startSearchViewAnim(float startF, float endF, long time) {
        ValueAnimator valueAnimator =startSearchViewAnim(startF, endF, time, null);
        return valueAnimator;
    }

    public ValueAnimator startSearchViewAnim(float startF, float endF, long time, final PathMeasure pathMeasure) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPro = (float) valueAnimator.getAnimatedValue();
                if (null != pathMeasure)
                    pathMeasure.getPosTan(mPro, mPos, null);
                getSearchView().invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        mPro = 0;
        return valueAnimator;
    }

}
