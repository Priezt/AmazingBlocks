package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block.Direction;

public class EnvLeftIn extends Pipe {
	public void init(){
		this.rotatable = false;
		loadIcon("envLeftIn.png");
	}
	
	public boolean rightOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Left))return;
		write(Direction.Right, read(Direction.Left));
	}
}
