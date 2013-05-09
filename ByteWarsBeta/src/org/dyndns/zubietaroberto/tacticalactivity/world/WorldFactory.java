package org.dyndns.zubietaroberto.tacticalactivity.world;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.dyndns.zubietaroberto.tacticalactivity.TacticalActivity;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.ShapeFactory;

import com.badlogic.gdx.math.Vector2;

public class WorldFactory {
	private static PhysicsWorld PHYSICS_WORLD;
	public static PhysicsWorld getPhysicsWorld(){ return PHYSICS_WORLD; }
	
	public static void loadWorld(){
		Scene pScene = TacticalActivity.ACTIVE_SCENE;
		PHYSICS_WORLD = new PhysicsWorld(new Vector2(0,0), true);
		ContactListener listener = new ContactListener();
		pScene.registerUpdateHandler(PHYSICS_WORLD);
		PHYSICS_WORLD.setContactListener(listener);
		
		pScene.setBackground(new ColorBackground(0,0,0));

		final int MaxSize = TacticalActivity.MAX_BATTLE_SIZE;
		final Line ground = new Line(-1, MaxSize + 1, MaxSize + 1, TacticalActivity.MAX_BATTLE_SIZE+1);
		final Line roof   = new Line(-1, -1, MaxSize + 1, -1);
		final Line left   = new Line(-1, -1, -1, MaxSize + 1);
		final Line right  = new Line(MaxSize + 1, MaxSize + 1, MaxSize + 1, -1);

		PhysicsFactory.createLineBody(PHYSICS_WORLD, ground, ShapeFactory.WALL_FIXTURE_DEF);
		PhysicsFactory.createLineBody(PHYSICS_WORLD, roof,   ShapeFactory.WALL_AIR_FIXTURE_DEF);
		PhysicsFactory.createLineBody(PHYSICS_WORLD, left,   ShapeFactory.WALL_AIR_FIXTURE_DEF);
		PhysicsFactory.createLineBody(PHYSICS_WORLD, right,  ShapeFactory.WALL_AIR_FIXTURE_DEF);

		pScene.attachChild(ground);
		pScene.attachChild(roof);
		pScene.attachChild(left);
		pScene.attachChild(right);

		TacticalActivity.CAMERA.setBoundsEnabled(true);
		
	}

}
