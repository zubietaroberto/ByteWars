package org.dyndns.zubietaroberto.tacticalactivity;

import org.dyndns.zubietaroberto.tacticalactivity.sprites.actors.BaseShip;


public class Director {
	private static final int MAX_SHIPS=5;
	
	public Director(){
		for (int i=0; i<MAX_SHIPS; i++){
			final float loc = i/MAX_SHIPS;
			new BaseShip(TacticalActivity.MAX_BATTLE_SIZE*loc, TacticalActivity.MAX_BATTLE_SIZE*loc, i);
		}
	}
}
