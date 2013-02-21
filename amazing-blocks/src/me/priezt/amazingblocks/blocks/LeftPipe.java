package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Block.Direction;

public class LeftPipe extends Pipe {
	public void init(){
		this.loadIcon("pipe2left.png");
	}
	
	public boolean upIn(){return true;}
	public boolean leftOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		write(Direction.Left, read(Direction.Up));
	}
}
