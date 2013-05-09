package org.dyndns.zubietaroberto.tacticalactivity.sprites.actors;

import static org.anddev.andengine.util.constants.Constants.VERTEX_INDEX_X;
import static org.anddev.andengine.util.constants.Constants.VERTEX_INDEX_Y;

import java.util.ArrayList;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.util.MathUtils;
import org.dyndns.zubietaroberto.tacticalactivity.TacticalActivity;
import org.dyndns.zubietaroberto.tacticalactivity.TextureLoader;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.ShapeFactory;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.weaponry.Rocket;
import org.dyndns.zubietaroberto.tacticalactivity.world.WorldFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

/**
 * @author Roberto E. Zubieta
 *
 */
public class BaseWeapon extends Sprite {
	
	private class CooldownTimerCallback implements ITimerCallback{

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (BaseWeapon.this.mTargetLocked){
				BaseWeapon.this.fireWeapon();
			}			
		}
		
	}
	private class WeaponDestroyerRunnable implements Runnable{

		@Override
		public void run() {
			TacticalActivity.ACTIVE_SCENE.detachChild(BaseWeapon.this);
		}
		
	}
	private static final int HIT_POINTS_GIVEN = 10;
	private static final int RELOAD_TIME = 100;
	private static final int WEAPON_RANGE = 100;
	private final ArrayList<Body> mActiveTargets = new ArrayList<Body>();
	private Vector2 mCannonCoordinates = new Vector2();
	public int mCurrentHP = BaseWeapon.HIT_POINTS_GIVEN;
	private final BaseShip mParentShip;
	private int mPlayerID = 0;

	private boolean mTargetLocked = false;

	private final WeaponRange mWeaponRange;
	
	public BaseWeapon(float pX, float pY, int pPlayerID, BaseShip pParentShip) {
		super(pX, pY, TextureLoader.LASER_TURRET_TEXTURE_REGION);
		this.mPlayerID = pPlayerID;
		Body body = PhysicsFactory.createBoxBody(WorldFactory.getPhysicsWorld(), this, BodyType.DynamicBody, ShapeFactory.OBJECT_FIXTURE_DEF);
		this.mWeaponRange = new WeaponRange(pX, pY, BaseWeapon.WEAPON_RANGE, this);
		final WeldJointDef jointDef = new WeldJointDef();
		jointDef.initialize(body, this.mWeaponRange.getRange(), body.getWorldCenter());
		WorldFactory.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this, body));
		WorldFactory.getPhysicsWorld().createJoint(jointDef);
		this.setUserData(body);
		body.setUserData(this);
		
		TacticalActivity.ACTIVE_SCENE.registerUpdateHandler(new TimerHandler(BaseWeapon.RELOAD_TIME, true, new CooldownTimerCallback()));
		
		if (pParentShip != null){
			this.mParentShip = pParentShip;
		}
		else
			this.mParentShip = null;
		
		TacticalActivity.ACTIVE_SCENE.attachChild(this);
	}
	
	public void destroy(){
		this.mParentShip.destroyModule(this);
		for (JointEdge jointEdge: this.getBody().getJointList()){
			WorldFactory.getPhysicsWorld().destroyJoint(jointEdge.joint);
		}
		final PhysicsConnector thisPhysicsConnector = WorldFactory.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(this);
		final PhysicsConnector rangePhysicsConnector = WorldFactory.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(this);
		
		WorldFactory.getPhysicsWorld().unregisterPhysicsConnector(thisPhysicsConnector);
		WorldFactory.getPhysicsWorld().unregisterPhysicsConnector(rangePhysicsConnector);
		
		WorldFactory.getPhysicsWorld().destroyBody(this.mWeaponRange.getRange());
		WorldFactory.getPhysicsWorld().destroyBody(this.getBody());
		TacticalActivity.getStaticEngine().runOnUpdateThread(new WeaponDestroyerRunnable());
		
	}
	
	private void fireWeapon(){
		final Rocket ammunition = TacticalActivity.getRocketPool().obtainPoolItem();
		ammunition.set(this.mCannonCoordinates, this.mRotation, this.mPlayerID);
	}
	
	public Body getBody(){
		return (Body) this.getUserData();
	}
	
	public int getPlayerID(){
		return this.mPlayerID;
	}
	
	public int getWeaponRange(){
		return BaseWeapon.WEAPON_RANGE;
	}
	
	public void newTarget(Body pNewTarget){
		this.mActiveTargets.add(pNewTarget);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		
		//Get Current Coordinates
		float[] coordinates = this.convertLocalToSceneCoordinates(10, 2);
		Vector2 newCannonCoordinates = Vector2Pool.obtain(coordinates[VERTEX_INDEX_X], coordinates[VERTEX_INDEX_Y]);
		if (newCannonCoordinates != this.mCannonCoordinates){
			this.mCannonCoordinates.set(newCannonCoordinates);
		}
		Vector2Pool.recycle(newCannonCoordinates);
		
		//Adjust turret rotation
		if (this.mActiveTargets.isEmpty()){
			this.setRotation(0);
			this.mTargetLocked = false;
		} else{
			final Vector2 possibleTarget = Vector2Pool.obtain(this.mActiveTargets.get(0).getWorldCenter());
			this.setRotation(MathUtils.radToDeg((float) Math.tan(possibleTarget.y/possibleTarget.x)));
			Vector2Pool.recycle(possibleTarget);
			this.mTargetLocked = true;
		}
		
		//Automatically destroy if it loses all HP
		if (this.mCurrentHP <= 0){
			this.destroy();
		}
	}
	
	public void targetLost(Body pOldTarget){
		this.mActiveTargets.remove(pOldTarget);
	}
	

}
