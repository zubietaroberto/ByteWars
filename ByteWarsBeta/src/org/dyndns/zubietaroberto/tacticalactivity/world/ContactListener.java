package org.dyndns.zubietaroberto.tacticalactivity.world;

import org.dyndns.zubietaroberto.tacticalactivity.TacticalActivity;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.actors.BaseShip;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.actors.BaseWeapon;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.actors.WeaponRange;
import org.dyndns.zubietaroberto.tacticalactivity.sprites.weaponry.Rocket;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactListener implements
		com.badlogic.gdx.physics.box2d.ContactListener {
	
	protected void beginContactLogic(Body body1, Body body2){
		if (body1.getUserData().getClass().equals(WeaponRange.class)){
			if (body2.getUserData().getClass().equals(BaseShip.class)){
				if (((WeaponRange) body1.getUserData()).getParentWeapon().getPlayerID() != ((BaseShip) body2.getUserData()).getPlayerID())
					((WeaponRange) body1.getUserData()).getParentWeapon().newTarget(body2);
			}
		}
		else if (body1.getUserData().getClass().equals(Rocket.class)){
			if (body2.getUserData().getClass().equals(WeaponRange.class)){
				return;
			}
			final Rocket attacker = (Rocket) body1.getUserData();
			if (body2.getUserData().getClass().equals(BaseShip.class)){
				((BaseShip) body2.getUserData() ).receiveDamage(attacker.getDamageDealt(), body2.getWorldCenter());
			}
			TacticalActivity.getRocketPool().recyclePoolItem(attacker);
		}
	}
	
	protected void endContactLogic(Body body1, Body body2) {
		if (body1.getUserData().getClass().equals(WeaponRange.class)){
			if (body2.getUserData().getClass().equals(BaseShip.class)){
				((BaseWeapon) body1.getUserData()).targetLost(body2);
			}
		}
	}

	@Override
	public void beginContact(final Contact contact) {
		final Body body1 = contact.getFixtureA().getBody();
		final Body body2 = contact.getFixtureB().getBody();
		
		if (body1.getUserData() == null && body2.getUserData() == null){
			return;
		}
		this.beginContactLogic(body1, body2);
		this.beginContactLogic(body2, body1);
	}

	@Override
	public void endContact(Contact contact) {
		final Body body1 = contact.getFixtureA().getBody();
		final Body body2 = contact.getFixtureB().getBody();
		
		if (body1.getUserData() == null && body2.getUserData() == null){
			return;
		}
		this.endContactLogic(body1, body2);
		this.endContactLogic(body2, body1);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

}
