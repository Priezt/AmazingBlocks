package me.priezt.amazingblocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WhiteBoard extends Board {
	public Texture background; 
	
	public WhiteBoard(float _width, float _height) {
		super(_width, _height);
		background = Tool.loadPicture("whiteBoardBackground.png");
	}

	public void draw(ShapeRenderer sr, SpriteBatch batch){
		Tool.drawAt(batch, background, width / 2, height / 2, width, height);
	}
}
