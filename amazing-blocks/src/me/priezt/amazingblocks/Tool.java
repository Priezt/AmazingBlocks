package me.priezt.amazingblocks;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Tool {
	public static BitmapFont defaultFont;
	public static BitmapFont fontBVSM50;
	public static AmazingBlocks root;
	public static HashMap<String, Texture> textureCache = new HashMap<String, Texture>();

	public static void init(AmazingBlocks _root){
		root = _root;
		defaultFont= new BitmapFont();
		fontBVSM50 = generateFont("BitstreamVeraSansMono.ttf", 50);
	}
	
	public static BitmapFont generateFont(String fontFilename, int fontSize){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font/" + fontFilename));
		BitmapFont newFont = generator.generateFont(fontSize);
		generator.dispose();
		return newFont;
	}
	
	public static void textAt(SpriteBatch batch, String message, float x, float y){
		defaultFont.draw(batch, message, x, y);
	}
	
	public static void textAtCenter(SpriteBatch batch, BitmapFont font, String message, float x, float y){
		TextBounds tb = font.getBounds(message);
		font.draw(batch, message, x - tb.width / 2, y + tb.height / 2);
	}
	
	public static void textAtCenter(SpriteBatch batch, String message, float x, float y){
		textAtCenter(batch, defaultFont, message, x, y);
	}
	
	public static void log(String message){
		Logger.getGlobal().log(Level.INFO, message);
	}
	
	public static void drawAt(SpriteBatch batch, Texture texture, float x, float y, float width, float height){
		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		Sprite sprite = new Sprite(region);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setSize(width, height);
		sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
		sprite.draw(batch);
	}
	
	public static Texture loadPicture(String imgFilename){
		if(textureCache.containsKey(imgFilename)){
			return textureCache.get(imgFilename);
		}
		Texture texture = new Texture(Gdx.files.internal("data/picture/" + imgFilename));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textureCache.put(imgFilename, texture);
		return texture;
	}
}
