package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.MainBoard;
import me.priezt.amazingblocks.Tool;

public class StraightPipe extends Pipe {
	public void init(){
		this.loadIcon("pipe.png");
	}
	
	public boolean upIn(){return true;}
	public boolean downOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		write(Direction.Down, read(Direction.Up));
	}
}
