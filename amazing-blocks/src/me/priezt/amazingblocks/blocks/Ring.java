package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import me.priezt.amazingblocks.*;

public class Ring extends Block {
	Sound sound;
	
	public void init(){
		this.loadIcon("ringbell.png");
		sound = Gdx.audio.newSound(Gdx.files.internal("data/music/ring.mp3"));
	}
	
	public void tapped(){
//		Tool.log("Ring Ring Ring");
		sound.play(0.5f);
	}
}
