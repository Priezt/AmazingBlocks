package me.priezt.amazingblocks.blocks;

import me.priezt.amazingblocks.Block;

public class HelloWorld extends Block {
	public int count = 0;
	public boolean downOut(){return true;}
	
	public void tick(){
		count++;
		if((count / 50) % 2 == 0){
			write(Direction.Down, "Hello");
		}else{
			write(Direction.Down, "World");
		}
	}
}
