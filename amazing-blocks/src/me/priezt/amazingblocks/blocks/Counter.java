package me.priezt.amazingblocks.blocks;

import java.util.Date;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Board;
import me.priezt.amazingblocks.MainBoard;
import me.priezt.amazingblocks.Tool;

public class Counter extends Block {
	public long currentCount = 0;
	
	public void init(){
		
	}
	
	public void tick(){
		long currentTimeStamp = (new Date()).getTime();
		currentCount = (currentTimeStamp / 1000) % 10;
	}
	
	public void draw(SpriteBatch batch){
		BitmapFont font = Tool.getFontBySize(container.zoom * MainBoard.RADIUS);
		font.setColor(0f, 0f, 1f, 1f);
		Tool.textAtCenter(batch, font, "" + currentCount, getScreenX(), getScreenY());
	}
}
