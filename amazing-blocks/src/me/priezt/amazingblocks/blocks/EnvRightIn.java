package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block.Direction;

public class EnvRightIn extends Pipe {
	public void init(){
		this.rotatable = false;
		loadIcon("envRightIn.png");
	}
	
	public boolean leftOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Right))return;
		write(Direction.Left, read(Direction.Right));
	}
}
