package com.example.quickeepoc.recyclerview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quickeepoc.R;

public class PhotoStreamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView icon;
    //private boolean isExpanded;
    NotifyChange changer;
    private int mOriginalHeight = 0;
    private boolean mIsViewExpanded = false;
    
    public PhotoStreamViewHolder(View itemView, NotifyChange change) {
        super(itemView);
        changer = change;
        icon = (ImageView) itemView.findViewById(R.id.icon);
        itemView.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		
		if (!mIsViewExpanded) {
          mIsViewExpanded = true; 
		 ValueAnimator pivotY = ObjectAnimator.ofFloat(v, "scaleY", 2);
		 pivotY.setDuration(200);
		 ValueAnimator pivotX = ObjectAnimator.ofFloat(v, "scaleX", 1.2F);
		 pivotX.setDuration(200);
		 
		 AnimatorSet animatorSet = new AnimatorSet();
	        animatorSet.playTogether(pivotX, pivotY);
	        animatorSet.start();
	      
		}else {
			  mIsViewExpanded = false;
			  ValueAnimator pivotY = ObjectAnimator.ofFloat(v, "scaleY", 1);
				 pivotY.setDuration(200);
				 ValueAnimator pivotX = ObjectAnimator.ofFloat(v, "scaleX", 1);
				 pivotX.setDuration(200);
				 AnimatorSet animatorSet = new AnimatorSet();
			        animatorSet.playTogether(pivotX, pivotY);
			        animatorSet.start();
		}
		 
		// pivotY.start();
		 
//		 ScaleAnimation scaleAnimation = new ScaleAnimation(0.2f, 2f, 0.2f, 2f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
//			        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//			scaleAnimation.setDuration(600);
//			v.startAnimation(scaleAnimation);
//			
//		 Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
		
	}
	
	
//	@Override
//    public void onClick(final View view) {
//        if (mOriginalHeight == 0) {
//            mOriginalHeight = view.getHeight();
//        }
//        ValueAnimator valueAnimator;
//        if (!mIsViewExpanded) {
//            mIsViewExpanded = true;
//            valueAnimator = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight + (int) (mOriginalHeight * 3));
//        } else {
//            mIsViewExpanded = false;
//            valueAnimator = ValueAnimator.ofInt(mOriginalHeight + (int) (mOriginalHeight * 3), mOriginalHeight);
//        }
//        valueAnimator.setDuration(1000);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Integer value = (Integer) animation.getAnimatedValue();
//                view.getLayoutParams().height = value.intValue();
//                view.requestLayout();
//            }
//        });
//        valueAnimator.start();
//
//    }
	
	public interface NotifyChange {
		public void notfiyMe();
	}

}
