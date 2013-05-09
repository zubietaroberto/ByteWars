package org.dyndns.zubietaroberto.tacticalactivity.sprites.weaponry;

import org.anddev.andengine.util.pool.GenericPool;
import org.dyndns.zubietaroberto.tacticalactivity.TextureLoader;

import com.badlogic.gdx.physics.box2d.Body;

public class RocketPool extends GenericPool<Rocket> {

	@Override
	protected Rocket onAllocatePoolItem() {
		final Rocket newRocket = new Rocket(-100, -100, TextureLoader.PARTICLE_TEXTURE_REGION);
		newRocket.init();
		return newRocket;
	}

	@Override
	protected void onHandleObtainItem(Rocket pItem) {
		pItem.setVisible(false);
		pItem.setIgnoreUpdate(true);
		final Body body = (Body) pItem.getUserData();
		body.setLinearVelocity(0, 0);
		body.setAngularVelocity(0);
		body.setActive(false);
	}

	@Override
	protected void onHandleRecycleItem(Rocket pItem) {
		pItem.setIgnoreUpdate(false);
		pItem.setVisible(true);
		final Body body = (Body) pItem.getUserData();
		body.setActive(true);
	}

}
