package me.priezt.amazingblocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlPanel {
	public static float PANELHEIGHT = 200f;
	
	public ControlPanel(){
		init();
	}
	
	public void init(){}
	
	public void draw(SpriteBatch batch){}
	
	public void touchDown(float x, float y){}
	
	public void pan(float x, float y, float deltaX, float deltaY){}
}