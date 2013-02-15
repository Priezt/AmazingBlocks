package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;

import me.priezt.amazingblocks.Block;

public class Vibrator extends Block {
	public void init(){
		loadIcon("vibrate.png");
	}
	
	public void tapped(){
		Gdx.input.vibrate(200);
	}
}
