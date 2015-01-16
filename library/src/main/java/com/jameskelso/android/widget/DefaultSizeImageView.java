package com.jameskelso.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An {@link android.widget.ImageView} with a height or width dimension set to
 * WRAP_CONTENT will experience jank when loading an image from the network or after a long-running
 * background task.
 *
 * Common solutions to this include setting the height and width to explicit values or setting a
 * minimum height or width on the {@link android.widget.ImageView}. Unfortunately, sometimes the
 * images that are loaded from the network may be much larger than the size of the target view. In
 * this case, the image must be resized to the bounds of the {@link android.widget.ImageView}. This
 * is simple when either the height or width of the {@link android.widget.ImageView} is known.
 *
 * If the constraint is MATCH_PARENT, however, the only way we can know the missing value of the
 * image is to attach an {@link android.view.ViewTreeObserver.OnGlobalLayoutListener} or
 * {@link android.view.View.OnLayoutChangeListener} and update the height after the first layout.
 * Since the values are only known after the layout occurs, jank can still occur because of updating
 * the layout.
 *
 * The {@link DefaultSizeImageView} eliminates the jank. It requires that the desired aspect ratio
 * or the height and width of the original image to be set before measurement occurs. It will
 * calculate an aspect ratio and update the measured dimension of the view to match what it will be
 * when the image load is complete.
 */
public class DefaultSizeImageView extends ImageView {
    // Aspect ratio is width / height
    protected float mAspectRatio;

    public DefaultSizeImageView(Context context) {
        super(context);
    }

    public DefaultSizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttrs(attrs, 0, 0);
    }

    public DefaultSizeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DefaultSizeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        resolveAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void resolveAttrs(AttributeSet attrs, int defStyle, int defStyleRes) {
        //Retrieve styles attributes
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.com_jameskelso_android_widget_DefaultSizeImageView,
                defStyle, defStyleRes);

        int originalWidth = a.getDimensionPixelSize(
                R.styleable.com_jameskelso_android_widget_DefaultSizeImageView_originalImageWidth,
                0);
        int originalHeight = a.getDimensionPixelSize(
                R.styleable.com_jameskelso_android_widget_DefaultSizeImageView_originalImageHeight,
                0);

        if (originalWidth > 0 && originalHeight > 0) {
            calculateAspectRatio(originalWidth, originalHeight);
        }

        float aspectRatio = a.getFloat(R.styleable.com_jameskelso_android_widget_DefaultSizeImageView_aspectRatio,
                0);
        if (aspectRatio > 0) {
            this.mAspectRatio = aspectRatio;
        }

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        // We don't want to override default functionality if the width and height are already set
        // or if both the width and height are unknown
        if (measuredWidth != 0 && measuredHeight != 0
                || (measuredWidth == 0 && measuredHeight == 0)) {
            return;
        }

        int desiredWidth;
        int desiredHeight;

        // If the width is the unknown dimension
        if (measuredWidth == 0) {
            desiredWidth = calculateDesiredWidthForHeight(measuredHeight, widthMeasureSpec);
            desiredHeight = measuredHeight;
        }
        // Otherwise the height is the unknown dimension
        else {
            desiredWidth = measuredWidth;
            desiredHeight = calculateDesiredHeightForWidth(measuredWidth, heightMeasureSpec);
        }

        // If we were able to resolve a size for the missing dimension, let's update the measured
        // dimension.
        if (desiredWidth != 0 && desiredHeight != 0) {
            int desiredWidthMeasureSpec =
                    MeasureSpec.makeMeasureSpec(desiredWidth, MeasureSpec.EXACTLY);

            int desiredHeightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY);

            setMeasuredDimension(desiredWidthMeasureSpec, desiredHeightMeasureSpec);
        }
    }

    /**
     * Calculate how wide this view wants to be based on the measured height and aspect ratio. This
     * method will respect the {@link android.view.View.MeasureSpec} passed in by the parent if
     * necessary.
     *
     * @param  measuredHeight the height of this view as measured by the parent class
     * @param  widthMeasureSpec the {@link android.view.View.MeasureSpec} passed in by the parent
     *                          {@link android.view.ViewGroup} as part of the layout cycle.
     * @return      the desired width of this view with respect to its parent
     */
    private int calculateDesiredWidthForHeight(int measuredHeight, int widthMeasureSpec) {
        // Desired width = height * aspect ratio
        // For example,  9 * (16 / 9) = 16
        int desiredWidth = Math.round(measuredHeight * mAspectRatio);
        return resolveSize(desiredWidth, widthMeasureSpec);
    }

    /**
     * Calculate how tall this view wants to be based on the measured width and aspect ratio. This
     * method will respect the {@link android.view.View.MeasureSpec} passed in by the parent if
     * necessary.
     *
     * @param  measuredWidth the width of this view as measured by the parent class
     * @param  heightMeasureSpec the {@link android.view.View.MeasureSpec} passed in by the parent
     *                          {@link android.view.ViewGroup} as part of the layout cycle.
     * @return      the desired height of this view with respect to its parent
     */
    private int calculateDesiredHeightForWidth(int measuredWidth, int heightMeasureSpec) {
        int desiredHeight = 0;
        if (mAspectRatio != 0) {
            // Desired height = width * aspect ratio
            // For example,  16 / (16 / 9) = 9
            desiredHeight = Math.round(measuredWidth / mAspectRatio);
        }
        return resolveSize(desiredHeight, heightMeasureSpec);
    }

    /**
     * Set the target width and height of the image that will be loaded into this view
     *
     * @param  originalWidth the original width of the image that will be loaded into this view
     *                       before resizing
     * @param  originalHeight the original height of the image that will be loaded into this view
     *                       before resizing
     */
    public void setOriginalImageWidthAndHeight(int originalWidth, int originalHeight) {
        calculateAspectRatio(originalWidth, originalHeight);
        requestLayout();
    }

    private void calculateAspectRatio(int originalWidth, int originalHeight) {
        this.mAspectRatio = ((float) originalWidth / (float) originalHeight);
    }

    /**
     * Set the aspect ratio (width / height) of the image that will be loaded into this view
     *
     * @param  aspectRatio the aspect ratio (width / height) of the image that will be loaded into
     *                     this view
     */
    public void setAspectRatio(float aspectRatio) {
        this.mAspectRatio = aspectRatio;
        requestLayout();
    }

    /**
     * Get the aspect ratio (width / height) of the image that will be loaded into this view
     */
    public float getAspectRatio() {
        return mAspectRatio;
    }
}
