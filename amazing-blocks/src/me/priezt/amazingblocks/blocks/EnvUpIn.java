package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;

public class EnvUpIn extends Pipe {
	public void init(){
		this.rotatable = false;
		loadIcon("envUpIn.png");
	}
	
	public boolean downOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		write(Direction.Down, read(Direction.Up));
	}
}
