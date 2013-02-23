package me.priezt.amazingblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EditBoard extends Board {
	public static float MOVINGZONE = 50f;
	public static float EDGEMOVINGSPEED = 5f;
	
	public MainBoard mainBoard;
	public Texture addButton;
	public Texture controlBoard;
	public enum TouchDownType {
		CONTROL, EMPTY, BLOCK
	};
	public TouchDownType touchDownType;
	public Block touchDownBlock;
	public boolean hasOrigin = false;
	public int originX;
	public int originY;
	public float currentX;
	public float currentY;
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean movingUp = false;
	public boolean movingDown = false;
	public ControlPanel controlPanel;
	public boolean borderMoveEnable = false;

	public EditBoard(float _width, float _height, MainBoard _mainBoard) {
		super(_width, _height);
		this.mainBoard = _mainBoard;
		addButton = Tool.loadPicture("add.png");
		controlBoard = Tool.loadPicture("controlBoard.png");
		setPanel(new NormalPanel());
	}

	public void draw(ShapeRenderer sr, SpriteBatch batch){
		mainBoard.drawBackground(sr, batch, true);
		mainBoard.drawForeground(sr, batch);
		this.drawHoldingBlock(sr, batch);
		this.drawControlBoard(sr, batch);
		controlPanel.draw(batch);
	}
	
	public void drawHoldingBlock(ShapeRenderer sr, SpriteBatch batch){
		if(touchDownType == TouchDownType.BLOCK){
			if(touchDownBlock != null){
				if(touchDownBlock.holding){
					float dx = currentX;
					float dy = currentY;
					if(dy <= ControlPanel.PANELHEIGHT){
						dy = ControlPanel.PANELHEIGHT;
					}
					touchDownBlock.drawAt(batch, dx, dy);
				}
			}
		}
	}
	
	public void drawControlBoard(ShapeRenderer sr, SpriteBatch batch){
		Tool.drawAt(batch, controlBoard, Tool.root.screenWidth / 2, ControlPanel.PANELHEIGHT / 2, Tool.root.screenWidth, ControlPanel.PANELHEIGHT);
	}
	
	public void touchDown(float x, float y){
		mainBoard.touchDown(x, y);
		if(y <= ControlPanel.PANELHEIGHT){
			touchDownType = TouchDownType.CONTROL;
			controlPanel.touchDown(x, y);
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
	
	public void tick(){
		if(touchDownType == TouchDownType.BLOCK){
			if(touchDownBlock != null){
				if(touchDownBlock.holding){
					float vx = 0f;
					float vy = 0f;
					if(movingLeft)
						vx -= EDGEMOVINGSPEED;
					if(movingRight)
						vx += EDGEMOVINGSPEED;
					if(movingUp)
						vy += EDGEMOVINGSPEED;
					if(movingDown)
						vy -= EDGEMOVINGSPEED;
					mainBoard.panMoveBy(vx / mainBoard.zoom, vy / mainBoard.zoom);
				}
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
				borderMoveEnable = true;
				hasOrigin = true;
				originX = touchDownBlock.x;
				originY = touchDownBlock.y;
				touchDownBlock.pick();
			}else{
				movingLeft = (x <= MOVINGZONE);
				movingRight = (x >= width - MOVINGZONE);
				movingUp = (y >= height - MOVINGZONE);
				movingDown = (y <= MOVINGZONE + ControlPanel.PANELHEIGHT);
				if(!borderMoveEnable){
					if(!(movingLeft || movingRight || movingUp || movingDown)){
						borderMoveEnable = true;
					}else{
						movingLeft = false;
						movingRight = false;
						movingUp = false;
						movingDown = false;
					}
				}
			}
		}else if(touchDownType == TouchDownType.CONTROL){
			controlPanel.pan(x, y, deltaX, deltaY);
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
				if(mainBoard.inBoard(cx, cy) && mainBoard.blockAt(cx, cy) == null){
					touchDownBlock.put(cx, cy);
				}else{
					if(hasOrigin){
						touchDownBlock.put(originX, originY);
					}else{
						touchDownBlock = null;
						mainBoard.remove(touchDownBlock);
					}
				}
			}
		}else if(touchDownType == TouchDownType.CONTROL){
			controlPanel.touchUp(x, y);
		}
	}
	
	public void longPressed(float x, float y){
		if(touchDownType == TouchDownType.BLOCK){
			Tool.log("rotate");
			touchDownBlock.rotate();
		}else if(touchDownType == TouchDownType.EMPTY){
			Tool.vibrate();
			setPanel(new AddPanel());
		}else if(touchDownType == TouchDownType.CONTROL){
			controlPanel.longPressed(x, y);
		}
	}
	
	public void backPressed(){
		if(controlPanel.getClass().equals(NormalPanel.class)){
			super.backPressed();
		}else{
			setPanel(new NormalPanel());
		}
	}
	
	public void setPanel(ControlPanel newPanel){
		newPanel.editBoard = this;
		controlPanel = newPanel;
	}
}
