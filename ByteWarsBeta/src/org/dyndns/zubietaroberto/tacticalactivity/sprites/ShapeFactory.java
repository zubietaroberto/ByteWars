package org.dyndns.zubietaroberto.tacticalactivity.sprites;

import static org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class ShapeFactory {
	/* The categories. */
	public final static short CATEGORYBIT_WALL         		=  1;
	public final static short CATEGORYBIT_WALL_AIR    		=  2;
    public final static short CATEGORYBIT_PLAYER       		=  4;
    public final static short CATEGORYBIT_ROCKET 			=  8;
    public final static short CATEGORYBIT_OBJECT       		= 32;

    /* And what should collide with what. */
    public final static short MASKBITS_WALL         = CATEGORYBIT_PLAYER + CATEGORYBIT_ROCKET + CATEGORYBIT_OBJECT;
    public final static short MASKBITS_WALL_AIR     = CATEGORYBIT_PLAYER + CATEGORYBIT_OBJECT;
    public final static short MASKBITS_PLAYER       = CATEGORYBIT_WALL + CATEGORYBIT_WALL_AIR + CATEGORYBIT_OBJECT + CATEGORYBIT_ROCKET;
    public final static short MASKBITS_ROCKET 		= CATEGORYBIT_WALL + CATEGORYBIT_OBJECT + CATEGORYBIT_PLAYER;
    public final static short MASKBITS_OBJECT       = CATEGORYBIT_WALL + CATEGORYBIT_WALL_AIR + CATEGORYBIT_PLAYER + CATEGORYBIT_ROCKET;

    public final static FixtureDef WALL_FIXTURE_DEF         = PhysicsFactory.createFixtureDef(    0, 0.0f,  0.5f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
    public final static FixtureDef WALL_AIR_FIXTURE_DEF     = PhysicsFactory.createFixtureDef(    0, 0.0f,  0.5f, false, CATEGORYBIT_WALL_AIR, MASKBITS_WALL_AIR, (short)0);
    public final static FixtureDef PLAYER_FIXTURE_DEF       = PhysicsFactory.createFixtureDef( 0.5f, 0.5f,  0.5f, false, CATEGORYBIT_PLAYER, MASKBITS_PLAYER, (short)0);
    public final static FixtureDef ROCKET_FIXTURE_DEF 		= PhysicsFactory.createFixtureDef( 300f, 1.0f,999.0f, false, CATEGORYBIT_ROCKET, MASKBITS_ROCKET, (short)0);
    public final static FixtureDef OBJECT_FIXTURE_DEF	    = PhysicsFactory.createFixtureDef( 0.1f, 0.5f,  0.5f, false, CATEGORYBIT_OBJECT, MASKBITS_OBJECT, (short)0);
    public final static FixtureDef SENSOR_UNFILTERED_DEF	= PhysicsFactory.createFixtureDef(    0,    0,     0,  true);
	
	public static Body createTriangleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		final float halfWidth = pShape.getWidthScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;
		final float halfHeight = pShape.getHeightScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;

		final float top = -halfHeight;
		final float bottom = halfHeight;
		final float left = -halfHeight;
		final float centerX = 0;
		final float right = halfWidth;

		final Vector2[] vertices = {
				new Vector2(centerX, top),
				new Vector2(right, bottom),
				new Vector2(left, bottom)
		};

		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pShape, vertices, pBodyType, pFixtureDef);
	}

}
