package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Block.Direction;

public class EnvDownIn extends Pipe{
	public void init(){
		this.rotatable = false;
		loadIcon("envDownIn.png");
	}
	
	public boolean upOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Down))return;
		write(Direction.Up, read(Direction.Down));
	}
}
