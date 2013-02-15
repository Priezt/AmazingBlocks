package me.priezt.amazingblocks.blocks;

import com.badlogic.gdx.Gdx;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.*;

public class OpenWhiteBoard extends Block {
	public Board whiteBoard;
	
	public void init(){
		loadIcon("whiteboard.png");
		float w = Tool.root.screenWidth;
		float h = Tool.root.screenHeight;
		whiteBoard = new WhiteBoard(w, h);
	}
	
	public void tapped(){
		Tool.root.board.changeBoard(whiteBoard);
	}
}
