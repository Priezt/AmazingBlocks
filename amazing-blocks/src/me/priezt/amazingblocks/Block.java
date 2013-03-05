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
	
	public boolean rotatable = true;
	public boolean hasPosition = false;
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
	
	public static Block newBlock(String blockName){
		try{
			Class blockClass = Class.forName("me.priezt.amazingblocks.blocks." + blockName);
			Block newBlock = (Block)blockClass.newInstance();
			return newBlock;
		}catch(Exception e){
			Tool.log("cannot create block: " + blockName);
			Tool.log("Error: " + e.getMessage());
			return null;
		}
	}
	
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
		if(!rotatable)return;
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
	
	/* DEPRECATED, USE PICK and PUT INSTEAD 
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
	*/
	
	public void pick(){
		int oldX = x;
		int oldY = y;
		if(hasPosition){
			container.blockHash.remove(container.xyToHashKey(oldX, oldY));
		}
		holding = true;
		Tool.log("pick: " + this.name() + "(" + oldX + "," + oldY + ")");
	}
	
	public void put(int newX, int newY){
		container.blockHash.put(container.xyToHashKey(newX, newY), this);
		x = newX;
		y = newY;
		holding = false;
		hasPosition = true;
		Tool.log("put: " + this.name() + "(" + x + "," + y + ")");
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
	
	public void drawAtWithoutArrow(SpriteBatch batch, float x, float y, float width, float height){
		if(icon != null){
			Tool.drawAt(batch, icon, x, y, width, height);
		}else{
			Tool.textAtCenter(batch, name(), x, y);
		}
	}
	
	public void drawAt(SpriteBatch batch, float x, float y){
		if(icon != null){
			Tool.drawAt(batch, icon, x, y, Block.RADIUS * container.zoom, Block.RADIUS * container.zoom, direction);
		}else{
			Tool.textAtCenter(batch, name(), x, y);
		}
		drawArrows(batch, x, y);
	}
	
	public Direction getArrowRelativeDirection(Direction arrowDirection){
		int currentDirectionInt = 0;
		if(direction == Direction.Up)currentDirectionInt = 0;
		else if(direction == Direction.Right)currentDirectionInt = 1;
		else if(direction == Direction.Down)currentDirectionInt = 2;
		else if(direction == Direction.Left)currentDirectionInt = 3;
		int absoluteDirectionInt = 0;
		if(arrowDirection == Direction.Up)absoluteDirectionInt = 0;
		else if(arrowDirection == Direction.Right)absoluteDirectionInt = 1;
		else if(arrowDirection == Direction.Down)absoluteDirectionInt = 2;
		else if(arrowDirection == Direction.Left)absoluteDirectionInt = 3;
		int relativeDirectionInt = (absoluteDirectionInt - currentDirectionInt + 4) % 4;
		Direction relativeDirection = Direction.Up;
		if(relativeDirectionInt == 0)relativeDirection = Direction.Up;
		else if(relativeDirectionInt == 1)relativeDirection = Direction.Right;
		else if(relativeDirectionInt == 2)relativeDirection = Direction.Down;
		else if(relativeDirectionInt == 3)relativeDirection = Direction.Left;
		return relativeDirection;
	}
	
	public Direction getArrowAbsoluteDirection(Direction arrowDirection){
		int currentDirectionInt = 0;
		if(direction == Direction.Up)currentDirectionInt = 0;
		else if(direction == Direction.Right)currentDirectionInt = 1;
		else if(direction == Direction.Down)currentDirectionInt = 2;
		else if(direction == Direction.Left)currentDirectionInt = 3;
		int additionDirectionInt = 0;
		if(arrowDirection == Direction.Up)additionDirectionInt = 0;
		else if(arrowDirection == Direction.Right)additionDirectionInt = 1;
		else if(arrowDirection == Direction.Down)additionDirectionInt = 2;
		else if(arrowDirection == Direction.Left)additionDirectionInt = 3;
		int absoluteDirectionInt = (currentDirectionInt + additionDirectionInt) % 4;
		Direction absoluteDirection = Direction.Up;
		if(absoluteDirectionInt == 0)absoluteDirection = Direction.Up;
		else if(absoluteDirectionInt == 1)absoluteDirection = Direction.Right;
		else if(absoluteDirectionInt == 2)absoluteDirection = Direction.Down;
		else if(absoluteDirectionInt == 3)absoluteDirection = Direction.Left;
		return absoluteDirection;
	}
	
	public class ArrowPosition{
		public float arrowX;
		public float arrowY;
		public Direction arrowDirection;
		
		public ArrowPosition(Direction relativeDirection, float x, float y, boolean directionIn){
			Direction absoluteDirection = getArrowAbsoluteDirection(relativeDirection);
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
		Tool.drawAt(batch, arrow, x, y, Block.ARROWRADIUS * container.zoom, Block.ARROWRADIUS * container.zoom, d, 0.5f);
	}
	
	public boolean upIn(){return false;}
	public boolean downIn(){return false;}
	public boolean leftIn(){return false;}
	public boolean rightIn(){return false;}
	
	public boolean upOut(){return false;}
	public boolean downOut(){return false;}
	public boolean leftOut(){return false;}
	public boolean rightOut(){return false;}
	
	public boolean hasIn(Direction d){
		if(d == Direction.Up)return upIn();
		else if(d == Direction.Down)return downIn();
		else if(d == Direction.Left)return leftIn();
		else if(d == Direction.Right)return rightIn();
		return false;
	}
	
	public boolean hasOut(Direction d){
		if(d == Direction.Up)return upOut();
		else if(d == Direction.Down)return downOut();
		else if(d == Direction.Left)return leftOut();
		else if(d == Direction.Right)return rightOut();
		return false;
	}
	
	public Object read(Direction d){
		int index = directionToIndex(d);
		Object result = registers[index];
		registers[index] = null;
		return result;
	}

	
	public static int directionToIndex(Direction d){
		int index = 0;
		if(d == Direction.Up)index = 0;
		else if(d == Direction.Down)index = 1;
		else if(d == Direction.Left)index = 2;
		else if(d == Direction.Right)index = 3;
		return index;
	}
	
	public boolean ready(Direction d){
		int index = directionToIndex(d);
		if(registers[index] != null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean write(Direction d, Object obj){
		if(!hasOut(d))return false;
		Direction targetDirection = getArrowAbsoluteDirection(d);
		Block targetBlock = getNeighbourBlock(targetDirection);
		if(targetBlock == null){
			return false;
		}
		Direction targetArrowDirection = targetBlock.getArrowRelativeDirection(Block.getOppositeDirection(targetDirection));
		if(!targetBlock.hasIn(targetArrowDirection)){
			return false;
		}
		int index = directionToIndex(targetArrowDirection);
		if(targetBlock.registers[index] != null){
			return false;
		}
		targetBlock.registers[index] = obj;
//		Tool.log("write: " + name() + "(" + d + ") -> " + targetBlock.name() + "(" + targetArrowDirection + ")");
		return true;
	}
	
	public static Direction getOppositeDirection(Direction d){
		if(d == Direction.Up)return Direction.Down;
		else if(d == Direction.Down)return Direction.Up;
		else if(d == Direction.Left)return Direction.Right;
		else if(d == Direction.Right)return Direction.Left;
		return null;
	}
	
	public Block getNeighbourBlock(Direction d){
		int neighbourX = x;
		int neighbourY = y;
		if(d == Direction.Up)neighbourY++;
		else if(d == Direction.Down)neighbourY--;
		else if(d == Direction.Left)neighbourX--;
		else if(d == Direction.Right)neighbourX++;
		return container.blockAt(neighbourX, neighbourY);
	}
	
}
