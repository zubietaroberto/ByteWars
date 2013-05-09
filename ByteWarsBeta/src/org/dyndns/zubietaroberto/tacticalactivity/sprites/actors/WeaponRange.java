package org.dyndns.zubietaroberto.tacticalactivity.sprites.actors;

import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.ShapeFactory;
import org.dyndns.zubietaroberto.tacticalactivity.world.WorldFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class WeaponRange {
	private final Body mWeaponRange;
	private final BaseWeapon mParentWeapon;
	
	public WeaponRange(float pCenterX, float pCenterY, int pRange, BaseWeapon pParentWeapon){
		this.mWeaponRange = PhysicsFactory.createCircleBody(WorldFactory.getPhysicsWorld(), pCenterX, pCenterY, pParentWeapon.getWeaponRange(), 0, BodyType.DynamicBody, ShapeFactory.SENSOR_UNFILTERED_DEF);
		this.mWeaponRange.setUserData(this);
		this.mParentWeapon = pParentWeapon;
	}
	
	public Body getRange(){
		return this.mWeaponRange;
	}
	
	public BaseWeapon getParentWeapon(){
		return this.mParentWeapon;
	}

}
