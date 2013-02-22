package me.priezt.amazingblocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AddPanel extends ControlPanel {
	public static float BLOCKRADIUS = 120f;
	public static float BLOCKDISTANCE = 130f;
	
	public float currentPosition = 0f;
	public Block selectedBlock = null;
	
	public ArrayList<Block> blocks;
	
	public enum Mode {
		Normal, Create
	};
	
	public Mode mode = Mode.Normal;
	
	public void init(){
		String[] blockNames = new String[]{
			"StraightPipe",
			"Click",
			"LeftPipe",
			"RightPipe",
			"HelloWorld",
			"Echo",
			"Counter",
			"Ring",
			"Vibrator"
		};
		blocks = new ArrayList<Block>();
		for(String bn : blockNames){
			blocks.add(Block.newBlock(bn));
		}
	}
	
	public void draw(SpriteBatch batch){
		int minIndex = getIndexByX(BLOCKDISTANCE / 2);
		int maxIndex = getIndexByX(Tool.root.screenWidth - BLOCKDISTANCE / 2);
		Tool.log(minIndex + "," + maxIndex);
		for(int i = minIndex; i <= maxIndex; i++){
			Block b = blocks.get(i);
			float screenX = getXByIndex(i);
			float screenY = PANELHEIGHT / 2;
			b.drawAtWithoutArrow(batch, screenX, screenY, BLOCKRADIUS, BLOCKRADIUS);
		}
	}
	
	public float getXByIndex(int index){
		return index * BLOCKDISTANCE + BLOCKDISTANCE / 2 - currentPosition;
	}
	
	public int getIndexByX(float x){
		int index = Math.round((x - BLOCKDISTANCE / 2 + currentPosition) / BLOCKDISTANCE);
		if(index >= blocks.size()){
			index = blocks.size() - 1;
		}
		return index;
	}
	
	public void pan(float x, float y, float deltaX, float deltaY){
		if(mode == Mode.Normal){
			currentPosition -= deltaX;
			if(currentPosition <= 0)currentPosition = 0;
			float maxPosition = (blocks.size() - 1) * BLOCKDISTANCE - Tool.root.screenWidth + BLOCKDISTANCE;
			if(currentPosition >= maxPosition)currentPosition = maxPosition;
		}else{
			
		}
	}
	
	public void touchDown(float x, float y){
		mode = Mode.Normal;
	}
	
	public void longPressed(float x, float y){
		int index = getIndexByX(x);
		float centerX = getXByIndex(index);
		if(Math.abs(centerX - x) <= BLOCKRADIUS && Math.abs(y - ControlPanel.PANELHEIGHT / 2) <= BLOCKRADIUS){
			mode = Mode.Create;
			selectedBlock = blocks.get(index);
			editBoard.mainBoard.addBlockWithoutPut(selectedBlock);
			editBoard.touchDownType = EditBoard.TouchDownType.BLOCK;
			editBoard.touchDownBlock = selectedBlock;
		}else{
			mode = Mode.Normal;
			selectedBlock = null;
		}
	}
}
