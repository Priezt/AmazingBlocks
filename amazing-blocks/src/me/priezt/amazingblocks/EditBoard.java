package me.priezt.amazingblocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EditBoard extends Board {
	public static float CONTROLBOARDHEIGHT = 200f;
	
	public MainBoard mainBoard;
	public Texture addButton;
	public Texture controlBoard;
	public enum TouchDownType {
		CONTROL, EMPTY, BLOCK
	};
	public TouchDownType touchDownType;
	public Block touchDownBlock;
	public int originX;
	public int originY;
	public float currentX;
	public float currentY;

	public EditBoard(float _width, float _height, MainBoard _mainBoard) {
		super(_width, _height);
		this.mainBoard = _mainBoard;
		addButton = Tool.loadPicture("add.png");
		controlBoard = Tool.loadPicture("controlBoard.png");
	}

	public void draw(ShapeRenderer sr, SpriteBatch batch){
		mainBoard.drawBackground(sr, batch, true);
		mainBoard.drawForeground(sr, batch);
		this.drawHoldingBlock(sr, batch);
		this.drawControlBoard(sr, batch);
	}
	
	public void drawHoldingBlock(ShapeRenderer sr, SpriteBatch batch){
		if(touchDownType == TouchDownType.BLOCK){
			if(touchDownBlock != null){
				if(touchDownBlock.holding){
					float dx = currentX;
					float dy = currentY;
					if(dy <= CONTROLBOARDHEIGHT){
						dy = CONTROLBOARDHEIGHT;
					}
					touchDownBlock.drawAt(batch, dx, dy);
				}
			}
		}
	}
	
	public void drawControlBoard(ShapeRenderer sr, SpriteBatch batch){
		Tool.drawAt(batch, controlBoard, Tool.root.screenWidth / 2, CONTROLBOARDHEIGHT / 2, Tool.root.screenWidth, CONTROLBOARDHEIGHT);
	}
	
	public void touchDown(float x, float y){
		mainBoard.touchDown(x, y);
		if(y <= CONTROLBOARDHEIGHT){
			touchDownType = TouchDownType.CONTROL;
		}else{
			int cx = mainBoard.getX(x);
			int cy = mainBoard.getY(y);
			Block b = mainBoard.blockAt(cx, cy);
			if(b == null){
				touchDownType = TouchDownType.EMPTY;
			}else{
				touchDownType = TouchDownType.BLOCK;
				touchDownBlock = b;
			}
		}
	}
	
	public void pan(float x, float y, float deltaX, float deltaY){
		currentX = x;
		currentY = y;
		if(touchDownType == TouchDownType.EMPTY){
			mainBoard.panMove(mainBoard.centerX - deltaX / mainBoard.zoom, mainBoard.centerY - deltaY / mainBoard.zoom);
		}else if(touchDownType == TouchDownType.BLOCK){
			if(!touchDownBlock.holding){
				originX = touchDownBlock.x;
				originY = touchDownBlock.y;
				touchDownBlock.pick();
			}else{
				
			}
		}
	}
	
	public void zoom(float initialDistance, float distance){
		mainBoard.zoom(initialDistance, distance);
	}
	
	public void touchUp(float x, float y) {
		if(touchDownType == TouchDownType.BLOCK){
			if(touchDownBlock.holding){
				int cx = mainBoard.getX(x);
				int cy = mainBoard.getY(y);
				touchDownBlock.put(cx, cy);
			}
		}
	}
}