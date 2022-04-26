package com.knight.wanandroid.library_widget.lottie;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 13:46
 * @descript:
 */
public class RightLottieAnimation {

    private static final String ROTATION = "rotation";
    private static final float GUILLOTINE_CLOSED_ANGLE = -90f;
    private static final float GUILLOTINE_OPENED_ANGLE = 0f;
    private static final int DEFAULT_DURATION = 625;
    private static final float ACTION_BAR_ROTATION_ANGLE = 3f;

    private final View mGuillotineView;
    private final long mDuration;
    private final ObjectAnimator mOpeningAnimation;
    private final ObjectAnimator mClosingAnimation;
    private final RightLottieListener mListener;
    private final boolean mIsRightToLeftLayout;
    private final TimeInterpolator mInterpolator;
    private final View mActionBarView;
    private final long mDelay;

    private boolean isOpening;
    private boolean isClosing;

    private BaseQuickAdapter mBaseQuickAdapter;

    private RightLottieAnimation(GuillotineBuilder builder) {
        this.mActionBarView = builder.actionBarView;
        this.mListener = builder.rightLottieListener;
        this.mGuillotineView = builder.guillotineView;
        this.mDuration = builder.duration > 0 ? builder.duration : DEFAULT_DURATION;
        this.mDelay = builder.startDelay;
        this.mIsRightToLeftLayout = builder.isRightToLeftLayout;
        this.mInterpolator = builder.interpolator == null ? new RightLottieInterpolator() : builder.interpolator;
        setUpOpeningView(builder.openingView);
        setUpClosingView(builder.closingView);
        this.mOpeningAnimation = buildOpeningAnimation();
        this.mClosingAnimation = buildClosingAnimation();
        if (builder.isClosedOnStart) {
            mGuillotineView.setRotation(GUILLOTINE_CLOSED_ANGLE);
            mGuillotineView.setVisibility(View.INVISIBLE);
        }
        this.mBaseQuickAdapter = builder.mBaseQuickAdapter;
        //TODO handle right-to-left layouts
        //TODO handle landscape orientation
    }

    public void open() {
        if (!isOpening) {
            mOpeningAnimation.start();
        }
    }

    public void close() {
        if (!isClosing) {
           // mBaseQuickAdapter.getData().clear();
           // mBaseQuickAdapter.notifyDataSetChanged();
            mClosingAnimation.start();
        }

    }

    private void setUpOpeningView(final View openingView) {
        if (mActionBarView != null) {
            mActionBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mActionBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mActionBarView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    mActionBarView.setPivotX(calculatePivotX(openingView));
                    mActionBarView.setPivotY(calculatePivotY(openingView));
                }
            });
        }
        openingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
    }

    private void setUpClosingView(final View closingView) {
        mGuillotineView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mGuillotineView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mGuillotineView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mGuillotineView.setPivotX(calculatePivotX(closingView));
                mGuillotineView.setPivotY(calculatePivotY(closingView));
            }
        });

        closingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    private ObjectAnimator buildOpeningAnimation() {
        PropertyValuesHolder p = PropertyValuesHolder.ofFloat(ROTATION, GUILLOTINE_CLOSED_ANGLE, GUILLOTINE_OPENED_ANGLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mGuillotineView, p);
        objectAnimator.setDuration((long) (mDuration * RightLottieInterpolator.ROTATION_TIME));
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mGuillotineView.setVisibility(View.VISIBLE);
                isOpening = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isOpening = false;
                if (mListener != null) {
                    mListener.onRightLottieOpened();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return objectAnimator;

    }

    private ObjectAnimator buildClosingAnimation() {
        PropertyValuesHolder p = PropertyValuesHolder.ofFloat(ROTATION, GUILLOTINE_OPENED_ANGLE, GUILLOTINE_CLOSED_ANGLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mGuillotineView, p);
        objectAnimator.setDuration((long) (mDuration * RightLottieInterpolator.ROTATION_TIME));
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBaseQuickAdapter.getData().clear();
                mBaseQuickAdapter.notifyDataSetChanged();
                isClosing = true;
                mGuillotineView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isClosing = false;
                mGuillotineView.setVisibility(View.GONE);
                //    startActionBarAnimation();

                if (mListener != null) {
                    mListener.onRightLottieClosed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return objectAnimator;



    }

    private void startActionBarAnimation() {
        ObjectAnimator actionBarAnimation = ObjectAnimator.ofFloat(mActionBarView, ROTATION, GUILLOTINE_OPENED_ANGLE, ACTION_BAR_ROTATION_ANGLE);
        actionBarAnimation.setDuration((long) (mDuration * (RightLottieInterpolator.FIRST_BOUNCE_TIME + RightLottieInterpolator.SECOND_BOUNCE_TIME)));
        actionBarAnimation.setInterpolator(new ActionBarInterpolator());
        actionBarAnimation.start();
    }

    private ObjectAnimator initAnimator(ObjectAnimator animator) {
        animator.setStartDelay(mDelay);
        return animator;
    }

    private float calculatePivotY(View burger) {
        return burger.getTop() + burger.getHeight() / 2;
    }

    private float calculatePivotX(View burger) {
        return burger.getLeft() + burger.getWidth() / 2;
    }

    public static class GuillotineBuilder {
        private final View guillotineView;
        private final View openingView;
        private final View closingView;
        private View actionBarView;
        private RightLottieListener rightLottieListener;
        private long duration;
        private long startDelay;
        private boolean isRightToLeftLayout;
        private TimeInterpolator interpolator;
        private boolean isClosedOnStart;
        private BaseQuickAdapter mBaseQuickAdapter;

        public GuillotineBuilder(View guillotineView, View closingView, View openingView,BaseQuickAdapter mBaseQuickAdapter) {
            this.guillotineView = guillotineView;
            this.openingView = openingView;
            this.closingView = closingView;
            this.mBaseQuickAdapter = mBaseQuickAdapter;
        }

        public GuillotineBuilder setActionBarViewForAnimation(View view) {
            this.actionBarView = view;
            return this;
        }

        public GuillotineBuilder setGuillotineListener(RightLottieListener rightLottieListener) {
            this.rightLottieListener = rightLottieListener;
            return this;
        }

        public GuillotineBuilder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public GuillotineBuilder setStartDelay(long startDelay) {
            this.startDelay = startDelay;
            return this;
        }

        public GuillotineBuilder setRightToLeftLayout(boolean isRightToLeftLayout) {
            this.isRightToLeftLayout = isRightToLeftLayout;
            return this;
        }

        public GuillotineBuilder setInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public GuillotineBuilder setClosedOnStart(boolean isClosedOnStart) {
            this.isClosedOnStart = isClosedOnStart;
            return this;
        }

        public RightLottieAnimation build() {
            return new RightLottieAnimation(this);
        }
    }
}
