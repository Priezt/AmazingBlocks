package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Block.Direction;

public class PipeOneToThree extends Block {
	public void init(){
		this.loadIcon("pipe123.png");
	}
	
	public boolean upIn(){return true;}
	public boolean downOut(){return true;}
	public boolean leftOut(){return true;}
	public boolean rightOut(){return true;}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		Object inputData = read(Direction.Up);
		write(Direction.Down, inputData);
		write(Direction.Left, inputData);
		write(Direction.Right, inputData);
	}
}
