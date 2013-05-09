package org.dyndns.zubietaroberto.tacticalactivity;

import org.anddev.andengine.extension.svg.adt.ISVGColorMapper;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureBuilder;
import org.anddev.andengine.opengl.texture.atlas.buildable.builder.ITextureBuilder.TextureAtlasSourcePackingException;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.Debug;

import android.content.Context;
import android.graphics.Color;

public class TextureLoader implements ISVGColorMapper {
	
	private static BuildableBitmapTextureAtlas BITMAP_TEXTURE_ATLAS;
	public static TextureRegion PAUSE_SCENE_TEXTURE_REGION;
	public static TextureRegion BASE_SHIP_TEXTURE_REGION;
	public static TextureRegion LASER_TURRET_TEXTURE_REGION;
	public static TextureRegion ENEMY_TURRET_TEXTURE_REGION;
	public static TextureRegion PARTICLE_TEXTURE_REGION;

	public static void loadTextures(Context pContext){
		TextureLoader.BITMAP_TEXTURE_ATLAS = new BuildableBitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		TextureLoader.PAUSE_SCENE_TEXTURE_REGION = BitmapTextureAtlasTextureRegionFactory.createFromAsset(TextureLoader.BITMAP_TEXTURE_ATLAS, pContext, "paused.png");
		TextureLoader.BASE_SHIP_TEXTURE_REGION = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(TextureLoader.BITMAP_TEXTURE_ATLAS, pContext,"BaseShip.svg", 150, 150);
		TextureLoader.LASER_TURRET_TEXTURE_REGION = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(TextureLoader.BITMAP_TEXTURE_ATLAS, pContext,"LaserTurret.svg", 20, 20);
		TextureLoader.ENEMY_TURRET_TEXTURE_REGION = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(TextureLoader.BITMAP_TEXTURE_ATLAS, pContext,"LaserTurret.svg", 20, 20, new TextureLoader());
		TextureLoader.PARTICLE_TEXTURE_REGION = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(TextureLoader.BITMAP_TEXTURE_ATLAS, pContext, "Particle.svg", 5, 5);
		
		try {
			TextureLoader.BITMAP_TEXTURE_ATLAS.build(new BlackPawnTextureBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1));			
		}catch(final TextureAtlasSourcePackingException e){
			Debug.e(e);
		}
	}

	@Override
	public Integer mapColor(Integer pColor) {
		if (pColor == null){
			return null;
		}
		else{
			return Color.argb(Color.alpha(pColor), Color.green(pColor), Color.red(pColor), Color.blue(pColor));
		}
	}

}
