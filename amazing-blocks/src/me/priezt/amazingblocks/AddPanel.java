package me.priezt.amazingblocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AddPanel extends ControlPanel {
	public static float BLOCKRADIUS = 120f;
	public static float BLOCKDISTANCE = 130f;
	
	public float currentPosition = 0f;
	public Block selectedBlock = null;
	
	public ArrayList<Block> blocks;
	
	public enum Mode {
		Scroll, Create
	};
	
	public Mode mode = Mode.Scroll;
	
	public void init(){
		String[] blockNames = new String[]{
			"StraightPipe",
			"Click",
			"LeftPipe",
			"RightPipe",
			"PipeOneToThree",
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
		//Tool.log(minIndex + "," + maxIndex);
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

	public void panBlock(float x, float y, float deltaX, float deltaY, Block block){
		if(!editBoard.touchDownBlock.holding){
			editBoard.borderMoveEnable = true;
			editBoard.originX = editBoard.touchDownBlock.x;
			editBoard.originY = editBoard.touchDownBlock.y;
			editBoard.touchDownBlock.pick();
		}else{
			editBoard.checkBoardMove(x, y);
		}
	}

	public void panControl(float x, float y, float deltaX, float deltaY){
		if(mode == Mode.Scroll){
			currentPosition -= deltaX;
			if(currentPosition <= 0)currentPosition = 0;
			float maxPosition = (blocks.size() - 1) * BLOCKDISTANCE - Tool.root.screenWidth + BLOCKDISTANCE;
			if(currentPosition >= maxPosition)currentPosition = maxPosition;
			if(selectedBlock != null && y >= ControlPanel.PANELHEIGHT){
				Tool.log("create block");
				editBoard.currentX = editBoard.mainBoard.screenXtoBoardX(x);
				editBoard.currentY = editBoard.mainBoard.screenYtoBoardY(y);
				Block newBlock = Block.newBlock(selectedBlock.getClass().getSimpleName());
				editBoard.mainBoard.addBlockWithoutPut(newBlock);
				editBoard.touchDownBlock = newBlock;
				newBlock.holding = true;
				editBoard.borderMoveEnable = false;
				mode = Mode.Create;
			}
		}else if(mode == Mode.Create){
			editBoard.checkBoardMove(x, y);
		}
	}
	
	public void touchDownInControl(float x, float y){
		selectedBlock = null;
		mode = Mode.Scroll;
		int index = getIndexByX(x);
		float centerX = getXByIndex(index);
		if(Math.abs(centerX - x) <= BLOCKRADIUS && Math.abs(y - ControlPanel.PANELHEIGHT / 2) <= BLOCKRADIUS){
			selectedBlock = Block.newBlock((blocks.get(index).getClass().getSimpleName()));
		}
	}
	
	public void touchUpFromControlToEmpty(float x, float y, int gridX, int gridY){
		editBoard.touchDownBlock.put(gridX, gridY);
	}

	public void touchUpFromControlToOutside(float x, float y){
		if(mode == Mode.Create){
			editBoard.mainBoard.remove(editBoard.touchDownBlock);
		}
	}
	
	public void touchUpFromControlToControl(float x, float y){
		if(mode == Mode.Create){
			editBoard.mainBoard.remove(editBoard.touchDownBlock);
		}
	}
	
	public void touchUpFromControlToBlock(float x, float y, Block block){
		if(mode == Mode.Create){
			editBoard.mainBoard.remove(editBoard.touchDownBlock);
		}
	}
	
	public void backPressed(){
		editBoard.quitBoard();
	}
	
	public void tappedBlock(float x, float y, Block block){
		ModifyPanel mp = new ModifyPanel();
		editBoard.setPanel(mp);
		mp.selectModifyBlock(block);
	};
}
