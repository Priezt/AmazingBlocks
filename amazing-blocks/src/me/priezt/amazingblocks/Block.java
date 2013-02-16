package me.priezt.amazingblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Block {
	public static float RADIUS = 100f;
	
	public int x;
	public int y;
	public MainBoard container;
	public Texture icon;
	public boolean holding = false;
	public enum Direction {
		Up, Down, Left, Right
	};
	public Direction direction = Direction.Up;
	
	public Block(){
		init();
	}
	
	public void init(){
		
	}
	
	public void loadIcon(String pictureName){
		icon = Tool.loadPicture(pictureName);
	}
	
	public void longPressed(){
		
	}
	
	public String name(){
		return this.getClass().getSimpleName();
	}
	
	public void tick(){}
	
	public void rotate(){
		rotate(true);
	}
	
	public void rotate(boolean clockwise){
		Direction oldDirection = direction;
		if(clockwise){
			if(direction == Direction.Up)direction = Direction.Right;
			else if(direction == Direction.Right)direction = Direction.Down;
			else if(direction == Direction.Down)direction = Direction.Left;
			else if(direction == Direction.Left)direction = Direction.Up;
		}else{
			if(direction == Direction.Up)direction = Direction.Left;
			else if(direction == Direction.Right)direction = Direction.Up;
			else if(direction == Direction.Down)direction = Direction.Right;
			else if(direction == Direction.Left)direction = Direction.Down;
		}
		Tool.log("rotate: " + oldDirection + "->" + direction);
	}
	
	public void moveTo(int newX, int newY){
		if(x == newX && y == newY){
			return;
		}
		if(container.blockAt(newX, newY) != null){
			return;
		}
		int oldX = x;
		int oldY = y;
		container.blockHash.remove(container.xyToHashKey(oldX, oldY));
		container.blockHash.put(container.xyToHashKey(newX, newY), this);
		x = newX;
		y = newY;
		holding = false;
	}
	
	public void pick(){
		int oldX = x;
		int oldY = y;
		container.blockHash.remove(container.xyToHashKey(oldX, oldY));
		holding = true;
	}
	
	public void put(int newX, int newY){
		container.blockHash.put(container.xyToHashKey(newX, newY), this);
		x = newX;
		y = newY;
		holding = false;
	}
	
	public void tapped(){
		Tool.log(name() + " tapped");
	}
	
	public float getScreenX(){
		float trueCenterX = -container.centerX * container.zoom + container.width / 2;
		return trueCenterX + x * MainBoard.RADIUS * container.zoom;
	}
	
	public float getScreenY(){
		float trueCenterY = -container.centerY * container.zoom + container.height/ 2;
		return trueCenterY + y * MainBoard.RADIUS * container.zoom;
	}
	
	public void draw(SpriteBatch batch){
		float screenX = getScreenX();
		float screenY = getScreenY();
		if(icon != null){
			Tool.drawAt(batch, icon, screenX, screenY, Block.RADIUS * container.zoom, Block.RADIUS * container.zoom, direction);
		}else{
			Tool.textAtCenter(batch, name(), screenX, screenY);
		}
	}
	
	public void drawAt(SpriteBatch batch, float x, float y){
		if(icon != null){
			Tool.drawAt(batch, icon, x, y, Block.RADIUS * container.zoom, Block.RADIUS * container.zoom, direction);
		}else{
			Tool.textAtCenter(batch, name(), x, y);
		}
	}
}
