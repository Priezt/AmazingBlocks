package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;

public class Click extends Block {
	public void init(){
		loadIcon("click.png");
	}
	
	public boolean downOut(){return true;}
	
	public void tapped(){
		write(Direction.Down, true);
	}
}
