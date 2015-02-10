package com.jameskelso.android.widget.sample;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by jkelso on 1/16/15.
 */
public class SampleAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_STANDARD = 0;
    private static final int VIEW_TYPE_DEFAULT_SIZE = 1;

    private Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public SampleAdapter(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createNewView(position, parent);
        }

        // Delay 5 seconds then load image to make the jank obvious
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadImage(imageView);
            }
        }, 5000);

        return convertView;
    }

    private View createNewView(int position, ViewGroup parent) {
        View view = null;
        switch (getItemViewType(position)) {
            case VIEW_TYPE_STANDARD:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_standard, parent, false);
                break;
            case VIEW_TYPE_DEFAULT_SIZE:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_aspect_ratio, parent,
                        false);
                break;
        }

       return view;
    }

    private void loadImage(final ImageView into) {
        int width = into.getWidth();

        if (width == 0) {
            into.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    into.removeOnLayoutChangeListener(this);
                    loadImage(into);
                }
            });
        } else {
            int desiredWidth = into.getWidth();
            float aspectRatio = 3415f / 2268f;
            int desiredHeight = Math.round(desiredWidth / aspectRatio);

            Picasso.with(mContext)
                    .load("http://upload.wikimedia.org/wikipedia/commons/1/1e/Large_Siamese_cat_tosses_a_mouse.jpg")
                    .skipMemoryCache()
                    .resize(desiredWidth, desiredHeight)
                    .into(into, new Callback() {

                                @Override
                                public void onSuccess() {
                                    Log.i(this.getClass().getSimpleName(), "Successfully loaded image");
                                }

                                @Override
                                public void onError() {
                                    Log.e(this.getClass().getSimpleName(), "Error loading image");
                                }
                            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch(position) {
            case 0:
                return VIEW_TYPE_DEFAULT_SIZE;
            case 1:
                return VIEW_TYPE_STANDARD;
        }

        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
