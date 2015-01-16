package com.jameskelso.android.widget.tests;

import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.jameskelso.android.widget.DefaultSizeImageView;

/**
 * Created by jkelso on 1/15/15.
 */
public class DefaultSizeImageViewTest extends AndroidTestCase {
    private static final float ASPECT_RATIO_1 = 16f / 9f;
    private static final float ASPECT_RATIO_2 = 1f;

    private static final int PARENT_WIDTH = 300;
    private static final int PARENT_HEIGHT = Math.round(PARENT_WIDTH / ASPECT_RATIO_1);

    private static final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(
            PARENT_WIDTH, View.MeasureSpec.EXACTLY);
    private static final int MATCH_PARENT_HEIGHT = View.MeasureSpec.makeMeasureSpec(
            PARENT_HEIGHT, View.MeasureSpec.EXACTLY);

    private static final int WRAP_CONTENT = 0;

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testAttrsOriginalHeightOnly() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.height_only, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), 0f);
    }

    public void testAttrsOriginalWidthOnly() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_only, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), 0f);
    }

    public void testAttrsOriginalHeightAndWidthOnly() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_and_height_only, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), ASPECT_RATIO_1);
    }

    public void testAttrsAspectRatioOnly() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.aspect_only, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), ASPECT_RATIO_2);
    }

    public void testAttrsHeightAndAspectRatioOnly() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.height_and_aspect, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), ASPECT_RATIO_2);
    }

    public void testAttrsWidthAndAspectRatioOnly() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_and_aspect, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), ASPECT_RATIO_2);
    }

    public void testAttrsWidthHeightAndAspectRatio() {
        DefaultSizeImageView imageView = (DefaultSizeImageView)
                LayoutInflater.from(getContext()).inflate(R.layout.width_height_and_aspect, null,
                        false);
        assertNotNull(imageView);
        assertEquals(imageView.getAspectRatio(), ASPECT_RATIO_2);
    }

    public void testSetOriginalWidthAndHeight() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        assertEquals(0f, imageView.getAspectRatio());

        imageView.setOriginalImageWidthAndHeight(16, 9);
        assertEquals(ASPECT_RATIO_1, imageView.getAspectRatio());
    }

    public void testSetAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        assertEquals(0f, imageView.getAspectRatio());

        imageView.setAspectRatio(ASPECT_RATIO_2);
        assertEquals(ASPECT_RATIO_2, imageView.getAspectRatio());
    }

    public void testOnMeasureNoAspectRatioWidth() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        imageView.measure(MATCH_PARENT_WIDTH, WRAP_CONTENT);

        assertEquals(imageView.getMeasuredWidth(), PARENT_WIDTH);
        assertEquals(imageView.getMeasuredHeight(), 0);
    }

    public void testOnMeasureNoAspectRatioHeight() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        imageView.measure(WRAP_CONTENT, MATCH_PARENT_HEIGHT);

        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), PARENT_HEIGHT);
    }

    public void testOnMeasureWidthWithAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        imageView.setAspectRatio(ASPECT_RATIO_1);
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        imageView.measure(MATCH_PARENT_WIDTH, WRAP_CONTENT);

        assertEquals(imageView.getMeasuredWidth(), PARENT_WIDTH);
        assertEquals(imageView.getMeasuredHeight(), PARENT_HEIGHT);
    }

    public void testOnMeasureHeightWithAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        imageView.setAspectRatio(ASPECT_RATIO_1);
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        imageView.measure(WRAP_CONTENT, MATCH_PARENT_HEIGHT);

        assertEquals(imageView.getMeasuredWidth(), PARENT_WIDTH);
        assertEquals(imageView.getMeasuredHeight(), PARENT_HEIGHT);
    }

    public void testOnMeasureWidthAndHeightWithAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        imageView.setAspectRatio(ASPECT_RATIO_1);
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        final int WIDTH_AND_HEIGHT = 500;
        final int MATCH_PARENT_WIDTH_AND_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH_AND_HEIGHT,
                View.MeasureSpec.EXACTLY);
        imageView.measure(MATCH_PARENT_WIDTH_AND_HEIGHT, MATCH_PARENT_WIDTH_AND_HEIGHT);

        assertEquals(imageView.getMeasuredWidth(), WIDTH_AND_HEIGHT);
        assertEquals(imageView.getMeasuredHeight(), WIDTH_AND_HEIGHT);
    }

    public void testOnMeasureWidthAndHeightAtMostWithAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        imageView.setAspectRatio(ASPECT_RATIO_1);
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        final int WIDTH = 500;
        final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(WIDTH,
                View.MeasureSpec.EXACTLY);
        final int AT_MOST_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH * 2,
                View.MeasureSpec.AT_MOST);
        final int ACTUAL_HEIGHT = Math.round(WIDTH / ASPECT_RATIO_1);
        imageView.measure(MATCH_PARENT_WIDTH, AT_MOST_HEIGHT);

        assertEquals(imageView.getMeasuredWidth(), WIDTH);
        assertEquals(imageView.getMeasuredHeight(), ACTUAL_HEIGHT);
    }

    public void testOnMeasureWidthAndHeightAtMostLimitedWithAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        imageView.setAspectRatio(ASPECT_RATIO_1);
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        final int WIDTH = 500;
        final int MATCH_PARENT_WIDTH = View.MeasureSpec.makeMeasureSpec(WIDTH,
                View.MeasureSpec.EXACTLY);
        final int AT_MOST_HEIGHT = View.MeasureSpec.makeMeasureSpec(WIDTH / 2,
                View.MeasureSpec.AT_MOST);
        final int ACTUAL_HEIGHT = WIDTH / 2;
        imageView.measure(MATCH_PARENT_WIDTH, AT_MOST_HEIGHT);

        assertEquals(imageView.getMeasuredWidth(), WIDTH);
        assertEquals(imageView.getMeasuredHeight(), ACTUAL_HEIGHT);
    }

    public void testOnMeasureWidthAtMostLimitedAndHeightWithAspectRatio() {
        DefaultSizeImageView imageView = new DefaultSizeImageView(getContext());
        imageView.setAspectRatio(ASPECT_RATIO_1);
        assertEquals(imageView.getMeasuredWidth(), 0);
        assertEquals(imageView.getMeasuredHeight(), 0);

        final int HEIGHT = 500;
        final int AT_MOST_WIDTH = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                View.MeasureSpec.AT_MOST);
        final int MATCH_PARENT_HEIGHT = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                View.MeasureSpec.EXACTLY);
        imageView.measure(AT_MOST_WIDTH, MATCH_PARENT_HEIGHT);

        assertEquals(imageView.getMeasuredWidth(), HEIGHT);
        assertEquals(imageView.getMeasuredHeight(), HEIGHT);
    }
}
