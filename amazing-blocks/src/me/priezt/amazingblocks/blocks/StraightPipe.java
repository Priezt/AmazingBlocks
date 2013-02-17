package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.MainBoard;
import me.priezt.amazingblocks.Tool;

public class StraightPipe extends Block {
	public void init(){
		this.loadIcon("pipe.png");
	}
	
	public void drawAt(SpriteBatch batch, float x, float y){
		if(icon != null){
			Tool.drawAt(batch, icon, x, y, MainBoard.RADIUS * container.zoom, MainBoard.RADIUS * container.zoom, direction);
		}else{
			Tool.textAtCenter(batch, name(), x, y);
		}
		drawArrows(batch, x, y);
	}
	
	public boolean upIn(){return true;}
	public boolean downOut(){return true;}
}
