package me.priezt.amazingblocks;

import java.util.ArrayList;
import java.util.HashMap;

import me.priezt.amazingblocks.blocks.Vibrator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainBoard extends Board {
	public static float RADIUS = 120f;
	public static float DRAWRADIUSRATIO = 1.0f;
	public static float MINZOOM = 0.2f;
	public static float MAXZOOM = 3f;
	
	public float zoom = 1f;
	public float centerX = 0f;// which point on the board the center of the screen aims to
	public float centerY = 0f;
	public int columns = 0;
	public int rows = 0;
	public HashMap<String, Block> blockHash;
	public ArrayList<Block> blockList;
	public float startZoomingZoom;
	public Texture brick;
	public Texture brickWithBorder;
	
	public MainBoard(float w, float h){
		super(w, h);
		rows = (int)(((height / RADIUS) / MINZOOM) / 2) - 1;
		columns = (int)(((width/ RADIUS) / MINZOOM) / 2) - 1;
		blockHash = new HashMap<String, Block>();
		blockList = new ArrayList<Block>();
		brick = Tool.loadPicture("brick.png");
		brickWithBorder = Tool.loadPicture("brickWithBorder.png");
	}
	
	public String xyToHashKey(int x, int y){
		return x + "," + y;
	}
	
	public Block blockAt(int x, int y){
		String hashKey = xyToHashKey(x, y);
		if(blockHash.containsKey(hashKey)){
			return blockHash.get(hashKey);
		}else{
			return null;
		}
	}
	
	public void addBlock(Block newBlock, int x, int y){
		blockList.add(newBlock);
		newBlock.x = x;
		newBlock.y = y;
		newBlock.container = this;
		blockHash.put(xyToHashKey(x, y), newBlock);
		newBlock.holding = false;
	}
	
	public void panMoveBy(float px, float py){
		panMove(centerX + px, centerY + py);
	}
	
	public void panMove(float newCenterX, float newCenterY){
		centerX = newCenterX;
		centerY = newCenterY;
		if(centerX <= -columns * RADIUS){
			centerX = -columns * RADIUS;
		}
		if(centerX >= columns * RADIUS){
			centerX = columns * RADIUS;
		}
		if(centerY <= -rows* RADIUS){
			centerY = -rows* RADIUS;
		}
		if(centerY >= rows* RADIUS){
			centerY = rows* RADIUS;
		}
	}
	
	public void doZoom(float newZoom){
		zoom = newZoom;
		if(zoom <= MINZOOM){
			zoom = MINZOOM;
		}
		if(zoom >= MAXZOOM){
			zoom = MAXZOOM;
		}
	}

	public void draw(ShapeRenderer sr, SpriteBatch batch){
		drawBackground(sr, batch);
		drawForeground(sr, batch);
	}
	
	public void drawBackground(ShapeRenderer sr, SpriteBatch batch){
		drawBackground(sr, batch, false);
	}
	
	public void tick(){
		 for(Block block : blockList){
			 if(block.active()){
				 block.tick();
			 }
		 }
	}
	
	public void drawBackground(ShapeRenderer sr, SpriteBatch batch, boolean needBorder){
		int minX = getX(0);
		int maxX = getX(width);
		int minY = getY(0);
		int maxY = getY(height);
		if(minX <= -columns){
			minX = -columns;
		}
		if(maxX >= columns){
			maxX = columns;
		}
		if(minY <= -rows){
			minY = -rows;
		}
		if(maxY >= rows){
			maxY = rows;
		}
		for(int x=minX;x<=maxX;x++){
			for(int y=minY;y<=maxY;y++){
				drawOneGridBackground(sr, batch, x, y, needBorder);
			}
		}
	}
	
	public void drawForeground(ShapeRenderer sr, SpriteBatch batch){
		int minX = getX(0);
		int maxX = getX(width);
		int minY = getY(0);
		int maxY = getY(height);
		if(minX <= -columns){
			minX = -columns;
		}
		if(maxX >= columns){
			maxX = columns;
		}
		if(minY <= -rows){
			minY = -rows;
		}
		if(maxY >= rows){
			maxY = rows;
		}

		for(int x=minX;x<=maxX;x++){
			for(int y=minY;y<=maxY;y++){
				drawOneGridForeground(sr, batch, x, y);
			}
		}
	}
	
	public void drawOneGridBackground(ShapeRenderer sr, SpriteBatch batch, int x, int y){
		drawOneGridBackground(sr, batch, x, y, false);
	}
	
	public void drawOneGridBackground(ShapeRenderer sr, SpriteBatch batch, int x, int y, boolean needBorder){
		float trueCenterX = -centerX * zoom + width / 2;
		float trueCenterY = -centerY * zoom + height / 2;
		float absoluteX = trueCenterX + x * RADIUS * zoom;
		float absoluteY = trueCenterY + y * RADIUS * zoom;
//		sr.rect(absoluteX - RADIUS * DRAWRADIUSRATIO * zoom / 2, absoluteY - RADIUS * DRAWRADIUSRATIO * zoom / 2, RADIUS * DRAWRADIUSRATIO * zoom, RADIUS * DRAWRADIUSRATIO * zoom);
		if(needBorder){
			Tool.drawAt(batch, brickWithBorder, absoluteX, absoluteY, RADIUS * DRAWRADIUSRATIO * zoom, RADIUS * DRAWRADIUSRATIO * zoom);
		}else{
			Tool.drawAt(batch, brick, absoluteX, absoluteY, RADIUS * DRAWRADIUSRATIO * zoom, RADIUS * DRAWRADIUSRATIO * zoom);
		}
	}
	
	public void drawOneGridForeground(ShapeRenderer sr, SpriteBatch batch, int x, int y){
		Block block = blockAt(x, y);
		if(block != null){
			block.draw(batch);
		}
	}
	
	public int getX(float x){
		float trueCenterX = -centerX * zoom + width / 2;
		int rx = (int) Math.floor(((x - trueCenterX) / zoom) / RADIUS  + 0.5f);
		return rx;
	}
	
	public int getY(float y){
		float trueCenterY = -centerY * zoom + height/ 2;
		int ry = (int) Math.floor(((y - trueCenterY) / zoom) / RADIUS  + 0.5f);
		return ry;
	}
	
	public boolean inBoard(float x, float y){
		if(x >= -columns && x <= columns && y >= -rows && y <= rows){
			return true;
		}else{
			return false;
		}
	}
	
	public void tapped(float x, float y){
		int gridX = getX(x);
		int gridY = getY(y);
		Block block = blockAt(gridX, gridY);
		if(block != null){
			block.tapped();
		}
	}
	
	public void longPressed(float x, float y){
		int gridX = getX(x);
		int gridY = getY(y);
		Block block = blockAt(gridX, gridY);
		if(block != null){
			block.longPressed();
		}else{
			this.addBlock(new Vibrator(), gridX, gridY);
		}
	}
	
	public void touchDown(float x, float y){
		startZoomingZoom = zoom;
	}
	
	public void pan(float x, float y, float deltaX, float deltaY){
		panMove(centerX - deltaX / zoom, centerY - deltaY / zoom);
	}
	
	public void zoom(float initialDistance, float distance){
		if(initialDistance != distance){
			doZoom(startZoomingZoom * distance / initialDistance);
		}
	}
	
	public void menuPressed(){
		EditBoard eb = new EditBoard(width, height, this);
		this.changeBoard(eb);
	}
}
