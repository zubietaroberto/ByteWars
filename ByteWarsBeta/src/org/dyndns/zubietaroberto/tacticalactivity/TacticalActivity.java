package org.dyndns.zubietaroberto.tacticalactivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.weaponry.RocketPool;
import org.dyndns.zubietaroberto.tacticalactivity.world.WorldFactory;

import android.util.DisplayMetrics;

public class TacticalActivity extends BaseGameActivity {
	
	public static int CAMERA_WIDTH, CAMERA_HEIGHT;
	public static final int MAX_BATTLE_SIZE = 1500;
	private static Engine ENGINE;
	public static BoundCamera CAMERA;
	private static RocketPool ROCKET_POOL = new RocketPool();
	public static Scene ACTIVE_SCENE;

	@Override
	public void onLoadComplete() {
	}

	@Override
	public Engine onLoadEngine() {
		DisplayMetrics dm = new DisplayMetrics();
		TacticalActivity.CAMERA_WIDTH = dm.widthPixels;
		TacticalActivity.CAMERA_HEIGHT = dm.heightPixels;
		TacticalActivity.CAMERA = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 0, TacticalActivity.MAX_BATTLE_SIZE, 0, TacticalActivity.MAX_BATTLE_SIZE);
		final EngineOptions engineOptions= new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(TacticalActivity.CAMERA_WIDTH, TacticalActivity.CAMERA_HEIGHT), CAMERA);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		TacticalActivity.ENGINE = new Engine(engineOptions);
		return TacticalActivity.ENGINE;
	}

	@Override
	public void onLoadResources() {
		TextureLoader.loadTextures(this);
	}

	@Override
	public Scene onLoadScene() {
		ACTIVE_SCENE = new Scene();
		WorldFactory.loadWorld();
		this.loadMultitouch();
		new Director();
		
		return TacticalActivity.ACTIVE_SCENE;
	}
	

	private void loadMultitouch() {
		ACTIVE_SCENE.setOnSceneTouchListener(new ScrollImplementation());
		//TODO: Add Pinch-Zoom Capability
	}
	
	public static RocketPool getRocketPool(){
		return TacticalActivity.ROCKET_POOL;
	}
	
	public static Engine getStaticEngine(){
		return TacticalActivity.ENGINE;
	}

}
