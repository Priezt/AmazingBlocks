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
		setPanel(new AddPanel());
	}

	public void draw(ShapeRenderer sr, SpriteBatch batch){
		mainBoard.drawBackground(sr, batch, true);
		mainBoard.drawForeground(sr, batch);
		this.drawHoldingBlock(sr, batch);
		this.drawControlBoard(sr, batch);
		controlPanel.draw(batch);
	}
	
	public void drawHoldingBlock(ShapeRenderer sr, SpriteBatch batch){
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
	
	public void drawControlBoard(ShapeRenderer sr, SpriteBatch batch){
		Tool.drawAt(batch, controlBoard, Tool.root.screenWidth / 2, ControlPanel.PANELHEIGHT / 2, Tool.root.screenWidth, ControlPanel.PANELHEIGHT);
	}
	
	public void touchDown(float x, float y){
		mainBoard.touchDown(x, y);
		touchDownBlock = null;
		if(y <= ControlPanel.PANELHEIGHT){
			touchDownType = TouchDownType.CONTROL;
			controlPanel.touchDownInControl(x, y);
		}else{
			int cx = mainBoard.getX(x);
			int cy = mainBoard.getY(y);
			originX = cx;
			originY = cy;
			Block b = mainBoard.blockAt(cx, cy);
			if(b == null){
				touchDownType = TouchDownType.EMPTY;
				controlPanel.touchDownInEmpty(x, y, cx, cy);
			}else{
				touchDownType = TouchDownType.BLOCK;
				touchDownBlock = b;
				controlPanel.touchDownInBlock(x, y, b);
			}
		}
	}
	
	public void touchUp(float x, float y) {
		if(y <= ControlPanel.PANELHEIGHT){
			if(touchDownType == TouchDownType.CONTROL)controlPanel.touchUpFromControlToControl(x, y);
			else if(touchDownType == TouchDownType.EMPTY)controlPanel.touchUpFromEmptyToControl(x, y);
			else if(touchDownType == TouchDownType.BLOCK)controlPanel.touchUpFromBlockToControl(x, y, touchDownBlock);
		}else{
			int cx = mainBoard.getX(x);
			int cy = mainBoard.getY(y);
			Block b = mainBoard.blockAt(cx, cy);
			if(b != null){
				if(touchDownType == TouchDownType.CONTROL)controlPanel.touchUpFromControlToBlock(x, y, b);
				else if(touchDownType == TouchDownType.EMPTY)controlPanel.touchUpFromEmptyToBlock(x, y, b);
				else if(touchDownType == TouchDownType.BLOCK)controlPanel.touchUpFromBlockToBlock(x, y, touchDownBlock, b);
			}else{
				if(mainBoard.inBoard(cx, cy)){
					if(touchDownType == TouchDownType.CONTROL)controlPanel.touchUpFromControlToEmpty(x, y, cx, cy);
					else if(touchDownType == TouchDownType.EMPTY)controlPanel.touchUpFromEmptyToEmpty(x, y, cx, cy);
					else if(touchDownType == TouchDownType.BLOCK)controlPanel.touchUpFromBlockToEmpty(x, y, touchDownBlock, cx, cy);
				}else{
					if(touchDownType == TouchDownType.CONTROL)controlPanel.touchUpFromControlToOutside(x, y);
					else if(touchDownType == TouchDownType.EMPTY)controlPanel.touchUpFromEmptyToOutside(x, y);
					else if(touchDownType == TouchDownType.BLOCK)controlPanel.touchUpFromBlockToOutside(x, y, touchDownBlock);
				}
			}
		}
	}
	
	public void tick(){
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
	
	public void pan(float x, float y, float deltaX, float deltaY){
		currentX = x;
		currentY = y;
		if(touchDownType == TouchDownType.EMPTY){
			controlPanel.panEmpty(x, y, deltaX, deltaY);
		}else if(touchDownType == TouchDownType.BLOCK){
			controlPanel.panBlock(x, y, deltaX, deltaY, touchDownBlock);
		}else if(touchDownType == TouchDownType.CONTROL){
			controlPanel.panControl(x, y, deltaX, deltaY);
		}
	}
	
	public void checkBoardMove(float x, float y){
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
	
	public void zoom(float initialDistance, float distance){
		controlPanel.zoom(initialDistance, distance);
	}

	public void longPressed(float x, float y){
		if(touchDownType == TouchDownType.BLOCK){
			controlPanel.longPressedBlock(x, y, touchDownBlock);
		}else if(touchDownType == TouchDownType.EMPTY){
			controlPanel.longPressedEmpty(x, y);
		}else if(touchDownType == TouchDownType.CONTROL){
			controlPanel.longPressedControl(x, y);
		}
		/*
		if(touchDownType == TouchDownType.BLOCK){
			Tool.vibrate();
			mainBoard.selectBlock(touchDownBlock.x, touchDownBlock.y);
			ModifyPanel mp = new ModifyPanel();
			mp.modifyBlock = touchDownBlock;
			setPanel(mp);
		}else if(touchDownType == TouchDownType.EMPTY){
			Tool.vibrate();
			setPanel(new AddPanel());
		}else if(touchDownType == TouchDownType.CONTROL){
			controlPanel.longPressed(x, y);
		}
		*/
	}
	
	public void backPressed(){
		controlPanel.backPressed();
	}
	
	public void tapped(float x, float y){
		if(touchDownType == TouchDownType.CONTROL){
			controlPanel.tappedControl(x, y);
		}else if(touchDownType == TouchDownType.EMPTY){
			int cx = mainBoard.getX(x);
			int cy = mainBoard.getY(y);
			if(mainBoard.inBoard(cx, cy)){
				controlPanel.tappedEmpty(x, y, cx, cy);
			}
		}else if(touchDownType == TouchDownType.BLOCK){
			controlPanel.tappedBlock(x, y, touchDownBlock);
		}
	}
	
	public void setPanel(ControlPanel newPanel){
		Tool.log("setPanel");
		Tool.log("switch control panel to: " + newPanel.getClass().getSimpleName());
		newPanel.editBoard = this;
		controlPanel = newPanel;
	}
}
