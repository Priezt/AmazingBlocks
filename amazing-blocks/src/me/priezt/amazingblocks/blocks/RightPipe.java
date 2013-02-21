package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Block.Direction;

public class RightPipe extends Pipe {
	public void init(){
		this.loadIcon("pipe2right.png");
	}
	
	public boolean upIn(){return true;}
	public boolean rightOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		write(Direction.Right, read(Direction.Up));
	}
}
