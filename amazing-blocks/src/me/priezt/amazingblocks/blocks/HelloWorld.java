package me.priezt.amazingblocks.blocks;

import java.util.Date;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Tool;

public class HelloWorld extends Block {
	public int lastMod = 1;
	public boolean downOut(){return true;}

	public void init(){
		this.loadIcon("helloworld.png");
	}
	
	public void tick(){
		long currentTimeStamp = (new Date()).getTime();
		int currentMod = (int)(currentTimeStamp / 1000) % 2;
		if(currentMod != lastMod){
			if(currentMod == 1){
				write(Direction.Down, "Hello");
//				Tool.log("Hello");
			}else{
				write(Direction.Down, "World");
//				Tool.log("World");
			}
		}
		lastMod = currentMod;
	}
}
