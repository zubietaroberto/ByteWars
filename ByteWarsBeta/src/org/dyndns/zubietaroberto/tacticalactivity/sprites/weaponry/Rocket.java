package org.dyndns.zubietaroberto.tacticalactivity.sprites.weaponry;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;
import org.dyndns.zubietaroberto.tacticalactivity.TacticalActivity;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.ShapeFactory;
import org.dyndns.zubietaroberto.tacticalactivity.world.WorldFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Rocket extends Sprite {
	private static int DAMAGE_DEALT = 10;
	private static int STARTING_VELOCITY = 15;
	private int mPlayerID = 0;

	/**
	 * @param pX
	 * @param pY
	 * @param pTextureRegion
	 */
	public Rocket(float pX, float pY, TextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		TacticalActivity.ACTIVE_SCENE.attachChild(this);
	}
	
	public void init(){
		Body body = PhysicsFactory.createBoxBody(WorldFactory.getPhysicsWorld(), this, BodyType.DynamicBody, ShapeFactory.ROCKET_FIXTURE_DEF);
		body.setLinearDamping(0.2f);
		body.setBullet(true);
		body.setLinearDamping(0.1f);
		body.setActive(false);
		body.setUserData(this);
		WorldFactory.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		this.setUserData(body);
		this.setIgnoreUpdate(true);
		this.setVisible(false);
	}
	
	public void set (final Vector2 pLaunchLocation, final float pRotation, final int pPlayerID){
		Body body = (Body) this.getUserData();
		body.setTransform(pLaunchLocation, pRotation);
		final double vX = Rocket.STARTING_VELOCITY*Math.sin(-MathUtils.degToRad(pRotation));
		final double vY = Rocket.STARTING_VELOCITY*Math.cos(MathUtils.degToRad(pRotation));
		body.setLinearVelocity((float) vX, (float) vY);
		this.mPlayerID = pPlayerID;
	}
	
	public int getDamageDealt(){
		return Rocket.DAMAGE_DEALT;
	}
	
	public int getPlayerID(){
		return this.mPlayerID;
	}

}
