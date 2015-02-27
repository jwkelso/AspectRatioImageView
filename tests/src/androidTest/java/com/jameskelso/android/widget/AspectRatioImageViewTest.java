package com.jameskelso.android.widget;

import android.os.Bundle;
import android.os.Parcel;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.jameskelso.android.widget.tests.R;

/**
 * Created by jkelso on 1/15/15.
 */
public class AspectRatioImageViewTest extends AndroidTestCase {
    private static final float ASPECT_RATIO_1 = 16f / 9f;
    private static final float ASPECT_RATIO_2 = 1f;

    private static final int PARENT_WIDTH = 300;
    private static final int PARENT_HEIGHT = Math.round(PARENT_WIDTH / ASPECT_RATIO_1);

    private static final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(
            PARENT_WIDTH, View.MeasureSpec.EXACTLY);
    private static final int MATCH_PARENT_HEIGHT = View.MeasureSpec.makeMeasureSpec(
            PARENT_HEIGHT, View.MeasureSpec.EXACTLY);

    private static final int WRAP_CONTENT = 0;

    private AspectRatioImageView.SavedState mState;
    private Parcel mParcel;
    private AspectRatioImageView mImageView;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mImageView = new AspectRatioImageView(getContext());
        mState = new AspectRatioImageView.SavedState(new Bundle());
        mState.aspectRatio = ASPECT_RATIO_1;

        mParcel = Parcel.obtain();
        mState.writeToParcel(mParcel, 0);
        mParcel.setDataPosition(0);
    }

    @Override
    public void tearDown() throws Exception {
        mImageView = null;
        mState = null;
        mParcel.recycle();
        mParcel = null;
        super.tearDown();
    }

    public void testAttrsOriginalHeightOnly() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.height_only, null,
                        false);
        assertEquals(0f, imageView.getAspectRatio());
    }

    public void testAttrsOriginalWidthOnly() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_only, null,
                        false);
        assertEquals(0f, imageView.getAspectRatio());
    }

    public void testAttrsOriginalHeightAndWidthOnly() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_and_height_only, null,
                        false);
        assertEquals(ASPECT_RATIO_1, imageView.getAspectRatio());
    }

    public void testAttrsAspectRatioOnly() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.aspect_only, null,
                        false);
        assertEquals(ASPECT_RATIO_2, imageView.getAspectRatio());
    }

    public void testAttrsHeightAndAspectRatioOnly() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.height_and_aspect, null,
                        false);
        assertEquals(ASPECT_RATIO_2, imageView.getAspectRatio());
    }

    public void testAttrsWidthAndAspectRatioOnly() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_and_aspect, null,
                        false);
        assertEquals(ASPECT_RATIO_2, imageView.getAspectRatio());
    }

    public void testAttrsWidthHeightAndAspectRatio() {
        AspectRatioImageView imageView = (AspectRatioImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_height_and_aspect, null,
                        false);
        assertEquals(ASPECT_RATIO_2, imageView.getAspectRatio());
    }

    public void testSetOriginalWidthAndHeight() {
        mImageView.setOriginalImageWidthAndHeight(16, 9);
        assertEquals(ASPECT_RATIO_1, mImageView.getAspectRatio());
    }

    public void testSetAspectRatio() {
        mImageView.setAspectRatio(ASPECT_RATIO_2);
        assertEquals(ASPECT_RATIO_2, mImageView.getAspectRatio());
    }

    public void testOnMeasureNoAspectRatioWidthMeasuredWidth() {
        mImageView.measure(MATCH_PARENT_WIDTH, WRAP_CONTENT);
        assertEquals(PARENT_WIDTH, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureNoAspectRatioWidthMeasuredHeight() {
        mImageView.measure(MATCH_PARENT_WIDTH, WRAP_CONTENT);
        assertEquals(0, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureNoAspectRatioHeightMeasuredWidth() {
        mImageView.measure(WRAP_CONTENT, MATCH_PARENT_HEIGHT);
        assertEquals(0, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureNoAspectRatioHeightMeasuredHeight() {
        mImageView.measure(WRAP_CONTENT, MATCH_PARENT_HEIGHT);
        assertEquals(PARENT_HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureWidthWithAspectRatioMeasuredWidth() {
        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH, WRAP_CONTENT);
        assertEquals(PARENT_WIDTH, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureWidthWithAspectRatioMeasuredHeight() {
        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH, WRAP_CONTENT);
        assertEquals(PARENT_HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureHeightWithAspectRatioMeasuredWidth() {
        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(WRAP_CONTENT, MATCH_PARENT_HEIGHT);
        assertEquals(PARENT_WIDTH, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureHeightWithAspectRatioMeasuredHeight() {
        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(WRAP_CONTENT, MATCH_PARENT_HEIGHT);
        assertEquals(PARENT_HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureWidthAndHeightWithAspectRatioMeasuredWidth() {
        final int WIDTH_AND_HEIGHT = 500;
        final int MATCH_PARENT_WIDTH_AND_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH_AND_HEIGHT,
                View.MeasureSpec.EXACTLY);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH_AND_HEIGHT, MATCH_PARENT_WIDTH_AND_HEIGHT);
        assertEquals(WIDTH_AND_HEIGHT, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureWidthAndHeightWithAspectRatioMeasuredHeight() {
        final int WIDTH_AND_HEIGHT = 500;
        final int MATCH_PARENT_WIDTH_AND_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH_AND_HEIGHT,
                View.MeasureSpec.EXACTLY);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH_AND_HEIGHT, MATCH_PARENT_WIDTH_AND_HEIGHT);
        assertEquals(WIDTH_AND_HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureWidthAndHeightAtMostWithAspectRatioMeasuredWidth() {
        final int WIDTH = 500;
        final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(WIDTH,
                View.MeasureSpec.EXACTLY);
        final int AT_MOST_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH * 2,
                View.MeasureSpec.AT_MOST);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH, AT_MOST_HEIGHT);
        assertEquals(WIDTH, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureWidthAndHeightAtMostWithAspectRatioMeasuredHeight() {
        final int WIDTH = 500;
        final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(WIDTH,
                View.MeasureSpec.EXACTLY);
        final int AT_MOST_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH * 2,
                View.MeasureSpec.AT_MOST);
        final int ACTUAL_HEIGHT = Math.round(WIDTH / ASPECT_RATIO_1);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH, AT_MOST_HEIGHT);
        assertEquals(ACTUAL_HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureWidthAndHeightAtMostLimitedWithAspectRatioMeasuredWidth() {
        final int WIDTH = 500;
        final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(WIDTH,
                View.MeasureSpec.EXACTLY);
        final int AT_MOST_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH / 2,
                View.MeasureSpec.AT_MOST);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH, AT_MOST_HEIGHT);
        assertEquals(WIDTH, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureWidthAndHeightAtMostLimitedWithAspectRatioMeasuredHeight() {
        final int WIDTH = 500;
        final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(WIDTH,
                View.MeasureSpec.EXACTLY);
        final int AT_MOST_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH / 2,
                View.MeasureSpec.AT_MOST);
        final int ACTUAL_HEIGHT = WIDTH / 2;

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(MATCH_PARENT_WIDTH, AT_MOST_HEIGHT);
        assertEquals(ACTUAL_HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnMeasureWidthAtMostLimitedAndHeightWithAspectRatioMeasuredWidth() {
        final int HEIGHT = 500;
        final int AT_MOST_WIDTH = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                View.MeasureSpec.AT_MOST);
        final int MATCH_PARENT_HEIGHT = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                View.MeasureSpec.EXACTLY);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(AT_MOST_WIDTH, MATCH_PARENT_HEIGHT);
        assertEquals(HEIGHT, mImageView.getMeasuredWidth());
    }

    public void testOnMeasureWidthAtMostLimitedAndHeightWithAspectRatioMeasuredHeight() {
        final int HEIGHT = 500;
        final int AT_MOST_WIDTH = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                View.MeasureSpec.AT_MOST);
        final int MATCH_PARENT_HEIGHT = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                View.MeasureSpec.EXACTLY);

        mImageView.setAspectRatio(ASPECT_RATIO_1);
        mImageView.measure(AT_MOST_WIDTH, MATCH_PARENT_HEIGHT);
        assertEquals(HEIGHT, mImageView.getMeasuredHeight());
    }

    public void testOnSaveInstanceState() {
        final float aspectRatio = 3.5f;
        mImageView.setAspectRatio(aspectRatio);
        AspectRatioImageView.SavedState state = mImageView.onSaveInstanceState();
        assertEquals(aspectRatio, state.aspectRatio);
    }

    public void testOnRestoreInstanceState() {
        final float aspectRatio = 7.2f;
        AspectRatioImageView.SavedState state = mImageView.onSaveInstanceState();
        state.aspectRatio = aspectRatio;
        mImageView.onRestoreInstanceState(state);
        assertEquals(aspectRatio, mImageView.mAspectRatio);
    }

    public void testOnRestoreInstanceStateIllegal() {
        try {
            mImageView.onRestoreInstanceState(new Bundle());
            fail("onRestoreInstanceState should throw an exception when the wrong SavedState " +
                    "class is passed in.");
        } catch (IllegalArgumentException e) {
            // This exception is supposed to occur
        }
    }

    public void testSavedStateConstructoFromParcel() {
        AspectRatioImageView.SavedState state = new AspectRatioImageView.SavedState(mParcel);
        assertEquals(ASPECT_RATIO_1, state.aspectRatio);
    }

    public void testConstructorWithParcelable() {
        // Nothing to test here
    }

    public void testWriteToParcel() {
        // Test as part of testConstructorFromParcel tests
    }

    public void testCreatorCreateFromParcel() {
        AspectRatioImageView.SavedState instance = AspectRatioImageView.SavedState.CREATOR
                .createFromParcel(mParcel);
        assertNotNull(instance);
    }

    public void testCreatorNewArray() {
        final int arrayLength = 5;
        AspectRatioImageView.SavedState[] instance = AspectRatioImageView.SavedState.CREATOR
                .newArray(arrayLength);
        assertNotNull(instance);
    }

    public void testCreatorNewArrayLength() {
        final int arrayLength = 5;
        AspectRatioImageView.SavedState[] instance = AspectRatioImageView.SavedState.CREATOR
                .newArray(arrayLength);
        assertEquals(arrayLength, instance.length);
    }
}
