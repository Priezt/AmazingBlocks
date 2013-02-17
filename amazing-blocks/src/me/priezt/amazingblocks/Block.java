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
	public static float ARROWRADIUS = 30f;
	public static Texture arrow;
	
	public int x;
	public int y;
	public MainBoard container;
	public Texture icon;
	public boolean holding = false;
	public enum Direction {
		Up, Down, Left, Right
	};
	public Direction direction = Direction.Up;
	public Object[] registers = new Object[4];
	
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
		drawAt(batch, screenX, screenY);
	}
	
	public void drawAt(SpriteBatch batch, float x, float y){
		if(icon != null){
			Tool.drawAt(batch, icon, x, y, Block.RADIUS * container.zoom, Block.RADIUS * container.zoom, direction);
		}else{
			Tool.textAtCenter(batch, name(), x, y);
		}
		drawArrows(batch, x, y);
	}
	
	public class ArrowPosition{
		public float arrowX;
		public float arrowY;
		public Direction arrowDirection;
		
		public ArrowPosition(Direction relativeDirection, float x, float y, boolean directionIn){
			int currentDirectionInt = 0;
			if(direction == Direction.Up)currentDirectionInt = 0;
			else if(direction == Direction.Right)currentDirectionInt = 1;
			else if(direction == Direction.Down)currentDirectionInt = 2;
			else if(direction == Direction.Left)currentDirectionInt = 3;
			int additionDirectionInt = 0;
			if(relativeDirection == Direction.Up)additionDirectionInt = 0;
			else if(relativeDirection == Direction.Right)additionDirectionInt = 1;
			else if(relativeDirection == Direction.Down)additionDirectionInt = 2;
			else if(relativeDirection == Direction.Left)additionDirectionInt = 3;
			int absoluteDirectionInt = (currentDirectionInt + additionDirectionInt) % 4;
			Direction absoluteDirection = Direction.Up;
			if(absoluteDirectionInt == 0)absoluteDirection = Direction.Up;
			else if(absoluteDirectionInt == 1)absoluteDirection = Direction.Right;
			else if(absoluteDirectionInt == 2)absoluteDirection = Direction.Down;
			else if(absoluteDirectionInt == 3)absoluteDirection = Direction.Left;
			if(absoluteDirection == Direction.Up){
				arrowX = x;
				arrowY = y + MainBoard.RADIUS * container.zoom / 2 - ARROWRADIUS * container.zoom / 2;
				if(directionIn)arrowDirection = Direction.Down;
				else arrowDirection = Direction.Up;
			}else if(absoluteDirection == Direction.Down){
				arrowX = x;
				arrowY = y - MainBoard.RADIUS * container.zoom / 2 + ARROWRADIUS * container.zoom / 2;
				if(directionIn)arrowDirection = Direction.Up;
				else arrowDirection = Direction.Down;
			}else if(absoluteDirection == Direction.Left){
				arrowX = x - MainBoard.RADIUS * container.zoom / 2 + ARROWRADIUS * container.zoom / 2;
				arrowY = y;
				if(directionIn)arrowDirection = Direction.Right;
				else arrowDirection = Direction.Left;
			}else if(absoluteDirection == Direction.Right){
				arrowX = x + MainBoard.RADIUS * container.zoom / 2 - ARROWRADIUS * container.zoom / 2;
				arrowY = y;
				if(directionIn)arrowDirection = Direction.Left;
				else arrowDirection = Direction.Right;
			}
		}
	}
	
	public void drawArrows(SpriteBatch batch, float x, float y){
		if(leftIn())drawOneArrow(batch, new ArrowPosition(Direction.Left, x, y, true));
		if(rightIn())drawOneArrow(batch, new ArrowPosition(Direction.Right, x, y, true));
		if(upIn())drawOneArrow(batch, new ArrowPosition(Direction.Up, x, y, true));
		if(downIn())drawOneArrow(batch, new ArrowPosition(Direction.Down, x, y, true));
		if(leftOut())drawOneArrow(batch, new ArrowPosition(Direction.Left, x, y, false));
		if(rightOut())drawOneArrow(batch, new ArrowPosition(Direction.Right, x, y, false));
		if(upOut())drawOneArrow(batch, new ArrowPosition(Direction.Up, x, y, false));
		if(downOut())drawOneArrow(batch, new ArrowPosition(Direction.Down, x, y, false));
	}
	
	public void drawOneArrow(SpriteBatch batch, ArrowPosition ap){
		drawOneArrow(batch, ap.arrowX, ap.arrowY, ap.arrowDirection);
	}
	
	public void drawOneArrow(SpriteBatch batch, float x, float y, Direction d){
		if(arrow == null){
			arrow = Tool.loadPicture("arrow.png");
		}
		Tool.drawAt(batch, arrow, x, y, Block.ARROWRADIUS * container.zoom, Block.ARROWRADIUS * container.zoom, d);
	}
	
	public boolean upIn(){return false;}
	public boolean downIn(){return false;}
	public boolean leftIn(){return false;}
	public boolean rightIn(){return false;}
	
	public boolean upOut(){return false;}
	public boolean downOut(){return false;}
	public boolean leftOut(){return false;}
	public boolean rightOut(){return false;}
	
	public Object readRegister(Direction d){
		int index = directionToIndex(d);
		Object result = registers[index];
		registers[index] = null;
		return result;
	}
	
	public int directionToIndex(Direction d){
		int index = 0;
		if(d == Direction.Up)index = 0;
		else if(d == Direction.Down)index = 1;
		else if(d == Direction.Left)index = 2;
		else if(d == Direction.Right)index = 3;
		return index;
	}
	
	public boolean registerFilled(Direction d){
		int index = directionToIndex(d);
		if(registers[index] != null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean active(){
		return false;
	}
}
