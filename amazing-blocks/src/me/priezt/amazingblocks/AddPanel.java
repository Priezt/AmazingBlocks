package me.priezt.amazingblocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AddPanel extends ControlPanel {
	public static float BLOCKRADIUS = 120f;
	public static float BLOCKDISTANCE = 130f;
	
	public float currentPosition = 0f;
	
	public ArrayList<Block> blocks;
	
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
		if(maxIndex >= blocks.size()){
			maxIndex = blocks.size() - 1;
		}
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
		return Math.round((x - BLOCKDISTANCE / 2 + currentPosition) / BLOCKDISTANCE);
	}
	
	public void pan(float x, float y, float deltaX, float deltaY){
		currentPosition -= deltaX;
		if(currentPosition <= 0)currentPosition = 0;
		float maxPosition = (blocks.size() - 1) * BLOCKDISTANCE - Tool.root.screenWidth + BLOCKDISTANCE;
		if(currentPosition >= maxPosition)currentPosition = maxPosition;
	}
}
