package org.dyndns.zubietaroberto.tacticalactivity.sprites.actors;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.input.touch.TouchEvent;
import org.dyndns.zubietaroberto.tacticalactivity.TacticalActivity;
import org.dyndns.zubietaroberto.tacticalactivity.TextureLoader;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.ShapeFactory;
import org.dyndns.zubietaroberto.tacticalactivity.world.WorldFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class BaseShip extends Sprite {
	private ArrayList<BaseWeapon> mLoadedModules = new ArrayList<BaseWeapon>();
	private int mPlayerId = 0;

	public BaseShip(float pX, float pY, int pPlayerID) {
		super(pX, pY, TextureLoader.BASE_SHIP_TEXTURE_REGION);
		this.mPlayerId = pPlayerID;
		Body body = ShapeFactory.createTriangleBody(WorldFactory.getPhysicsWorld(), this, BodyType.DynamicBody, ShapeFactory.PLAYER_FIXTURE_DEF);
		WorldFactory.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this, body));
		final RevoluteJointDef jointDef = new RevoluteJointDef();
		
		//Replace Code with actual initialization
		for (int i=5; i<5; i++){
			final float positionX=this.mX+this.getWidthScaled()/2;
			final float positionY=this.mY+this.getHeightScaled()*i/5;
			BaseWeapon newWeapon = new BaseWeapon(positionX, positionY, pPlayerID, this);
			this.mLoadedModules.add(newWeapon);
			jointDef.initialize(body, newWeapon.getBody(), new Vector2(positionX, positionY));
			WorldFactory.getPhysicsWorld().createJoint(jointDef);
		}
		
		//Register on Scene for touch and display
		TacticalActivity.ACTIVE_SCENE.attachChild(this);
		TacticalActivity.ACTIVE_SCENE.registerTouchArea(this);
	}

	public void destroyModule(BaseWeapon pModule){
		this.mLoadedModules.remove(pModule);
	}
	
	public int getPlayerID(){
		return this.mPlayerId;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		this.setPosition(pSceneTouchEvent.getX() - this.mWidth/2, pSceneTouchEvent.getY() - this.mHeight/2);
		return true;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (this.mLoadedModules.isEmpty()){
			TacticalActivity.getStaticEngine().runOnUpdateThread(new ShipDestroyerRunnable());
		}
	}
	
	public void receiveDamage(int pDamageDealt, Vector2 pPointOfContact) {
		//ADD Implement Armor
		Vector2 currentLoc = Vector2Pool.obtain();
		BaseWeapon currentWeapon = null;
		for (BaseWeapon weapon: this.mLoadedModules){
			if (weapon.getBody().getWorldCenter().dst(pPointOfContact) < currentLoc.dst(pPointOfContact)){
				Vector2Pool.recycle(currentLoc);
				currentWeapon = weapon;
				currentLoc = Vector2Pool.obtain(weapon.getBody().getWorldCenter());
			}			
		}
		//Implement Here the capability to reduce damage according to distance from impact
		Vector2Pool.recycle(currentLoc);
		if (currentWeapon != null){
			currentWeapon.mCurrentHP -= pDamageDealt;
		}
	}

	private class ShipDestroyerRunnable implements Runnable{

		@Override
		public void run() {
			TacticalActivity.ACTIVE_SCENE.unregisterTouchArea(BaseShip.this);
			TacticalActivity.ACTIVE_SCENE.detachChild(BaseShip.this);
		}
		
	}

}
