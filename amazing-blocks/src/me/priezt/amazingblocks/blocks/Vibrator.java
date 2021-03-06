package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Block.Direction;

public class Vibrator extends Block {
	public void init(){
		loadIcon("vibrate.png");
	}
	
	public boolean upIn(){return true;}
	
	public void tapped(){
		Gdx.input.vibrate(200);
	}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		read(Direction.Up);
		tapped();
	}
}
