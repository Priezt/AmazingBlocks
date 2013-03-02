package me.priezt.amazingblocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ControlPanel {
	public static float PANELHEIGHT = 200f;
	
	public EditBoard editBoard;
	
	public ControlPanel(){
		init();
	}
	
	public void init(){}
	
	public void draw(SpriteBatch batch){}
	
	public void touchDownInControl(float x, float y){}
	public void touchDownInEmpty(float x, float y, int gridX, int gridY){}
	public void touchDownInBlock(float x, float y, Block block){}
	
	public void touchUpFromControlToControl(float x, float y){}
	public void touchUpFromControlToOutside(float x, float y){}
	public void touchUpFromControlToEmpty(float x, float y, int gridX, int gridY){}
	public void touchUpFromControlToBlock(float x, float y, Block block){}
	public void touchUpFromEmptyToControl(float x, float y){}
	public void touchUpFromEmptyToOutside(float x, float y){}
	public void touchUpFromEmptyToEmpty(float x, float y, int gridX, int gridY){}
	public void touchUpFromEmptyToBlock(float x, float y, Block block){}
	
	public void touchUpFromBlockToControl(float x, float y, Block fromBlock){
		editBoard.touchDownBlock.put(editBoard.originX, editBoard.originY);
	}
	
	public void touchUpFromBlockToOutside(float x, float y, Block fromBlock){
		editBoard.touchDownBlock.put(editBoard.originX, editBoard.originY);
	}
	
	public void touchUpFromBlockToEmpty(float x, float y, Block fromBlock, int gridX, int gridY){
		editBoard.touchDownBlock.put(gridX, gridY);
	}
	
	public void touchUpFromBlockToBlock(float x, float y, Block fromBlock, Block block){
		editBoard.touchDownBlock.put(editBoard.originX, editBoard.originY);
	}

	public void zoom(float initialDistance, float distance){
		editBoard.mainBoard.zoom(initialDistance, distance);
	}
	
	public void panBlock(float x, float y, float deltaX, float deltaY, Block block){}
	public void panEmpty(float x, float y, float deltaX, float deltaY){
		editBoard.mainBoard.panMove(editBoard.mainBoard.centerX - deltaX / editBoard.mainBoard.zoom, editBoard.mainBoard.centerY - deltaY / editBoard.mainBoard.zoom);
	}
	public void panControl(float x, float y, float deltaX, float deltaY){}
	
	public void longPressedEmpty(float x, float y){}
	public void longPressedBlock(float x, float y, Block block){}
	public void longPressedControl(float x, float y){}
	
	public void tappedBlock(float x, float y, Block block){}
	public void tappedEmpty(float x, float y, int gridX, int gridY){}
	public void tappedControl(float x, float y){};
	
	public void backPressed(){}
}
