package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.MainBoard;
import me.priezt.amazingblocks.Tool;

public class Echo extends Block {
	public String text = "Echo";
	
	public boolean upIn(){return true;}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		text = read(Direction.Up).toString();
	}
	
	public void drawAt(SpriteBatch batch, float x, float y){
		BitmapFont font = Tool.getFontBySize(container.zoom * MainBoard.RADIUS / 2);
		font.setColor(0f, 0f, 1f, 1f);
		Tool.textAtCenter(batch, font, text, x, y);
		drawArrows(batch, x, y);
	}
}
