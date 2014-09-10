package com.example.murilo.myandroidsandbox.intents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Murilo on 05/09/2014.
 */
public class MyImage extends ImageView {

        private OnImageViewSizeChanged sizeCallback = null;

        public MyImage(Context context) {
            super(context);
        }

        public MyImage(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyImage(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (w == 0 || h == 0) {
                return;
            }
            else{
                if(sizeCallback != null)
                    sizeCallback.invoke(this, w, h);
            }

        }

        public void setOnImageViewSizeChanged(OnImageViewSizeChanged _callback){
            this.sizeCallback = _callback;

            if (getWidth() != 0 && getHeight() != 0) {
                _callback.invoke(this, getWidth(), getHeight());
            }
        }

        public interface OnImageViewSizeChanged{
            public void invoke(ImageView v, int w, int h);
        }
}
