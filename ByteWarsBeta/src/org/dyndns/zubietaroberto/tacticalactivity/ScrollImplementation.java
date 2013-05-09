package org.dyndns.zubietaroberto.tacticalactivity;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;

public class ScrollImplementation implements IOnSceneTouchListener, IScrollDetectorListener {
	private final SurfaceScrollDetector mScrollDetector;


	/**
	 * @param mScrollDetector
	 */
	public ScrollImplementation() {
		this.mScrollDetector = new SurfaceScrollDetector(this) ;
	}
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		return true;
	}


	@Override
	public void onScroll(ScrollDetector pScollDetector, TouchEvent pTouchEvent,
			float pDistanceX, float pDistanceY) {
		TacticalActivity.CAMERA.offsetCenter(-pDistanceX, -pDistanceY);
	}
	
}
