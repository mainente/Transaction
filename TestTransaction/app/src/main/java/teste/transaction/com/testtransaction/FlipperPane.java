package teste.transaction.com.testtransaction;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.util.Stack;

public abstract class FlipperPane {

	Activity currentActivity;
	int layoutResourceId;

	private static Stack<Integer> history = null;
	private static ViewFlipper viewFlipper = null;

	public FlipperPane() {
		if (null == history) {
			history = new Stack<Integer>();
		}
	}
		
	public static void initializeHistory() {
        if (null != viewFlipper) {
            viewFlipper.removeAllViews();
        }
        viewFlipper = null;
        if (null != history) {
            history.clear();
        }
        //history = new Stack<Integer>();
	}
	
	public static void setBaseView(ViewFlipper v) {
		viewFlipper = v;
	//	return this;
	}
	
	public Activity getCurrentActivity() {
		return currentActivity;
	}
	
	public abstract int getLayoutResourceId();
	
	public void flipToPane(Activity pCurrentActivity) {
		
		currentActivity = pCurrentActivity;
		layoutResourceId = getLayoutResourceId();

		View child = currentActivity.getLayoutInflater().inflate(layoutResourceId, viewFlipper, false);
		viewFlipper.addView(child, history.size());

	
		if (history.size() == 0) {
			onFlip();
		}
		else {
			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(currentActivity, R.anim.push_left_in));
			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(currentActivity, R.anim.push_left_out));
			onFlip();
			viewFlipper.showNext();		
		}
		
		history.add(layoutResourceId);
		

	}

	public abstract void onFlip();
	
	public void onDestroy() {
	}
	
	public static void showFirst(Activity currentActivity) {
		viewFlipper.setDisplayedChild(0);
		

	}
	
	public void showPrevious() {
		
		onDestroy();
		history.pop();
//		viewFlipper.sho
		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(currentActivity, R.anim.push_right_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(currentActivity, R.anim.push_right_out));
		viewFlipper.showPrevious();
	}
}
