package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import me.priezt.amazingblocks.*;
import me.priezt.amazingblocks.Block.Direction;

public class Ring extends Block {
	Sound sound;
	
	public void init(){
		this.loadIcon("ringbell.png");
		sound = Gdx.audio.newSound(Gdx.files.internal("data/music/ring.mp3"));
	}
	
	public boolean upIn(){return true;}
	
	public void tapped(){
//		Tool.log("Ring Ring Ring");
		sound.play(0.5f);
	}
	
	public void tick(){
		if(!ready(Direction.Up))return;
		read(Direction.Up);
		tapped();
	}
}
