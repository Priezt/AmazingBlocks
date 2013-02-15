package me.priezt.amazingblocks;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import me.priezt.amazingblocks.blocks.*;

public class Board {
	public float width;
	public float height;
	public Board parent = null;
	
	public Board(float _width, float _height){
		width = _width;
		height = _height;
	}
	
	public void changeBoard(Board newBoard){
		newBoard.parent = this;
		Tool.root.board = newBoard;
	}
	
	public void quitBoard(){
		if(parent != null){
			Tool.root.board = parent;
		}
	}

	public void draw(ShapeRenderer sr, SpriteBatch batch){}
	
	public void tapped(float x, float y){// left bottom: 0, 0
	}
	
	public void longPressed(float x, float y){// left bottom: 0, 0
	}
	
	public void touchDown(float x, float y){// left bottom: 0, 0
	}
	
	public void touchUp(float x, float y) {// left bottom: 0, 0
	}
	
	public void pan(float x, float y, float deltaX, float deltaY){// left bottom: 0, 0
	}
	
	public void zoom(float initialDistance, float distance){
	}
	
	public void backPressed(){
		if(parent != null){
			Tool.log("quit board");
			quitBoard();
		}else{
			Tool.log("exit");
			Gdx.app.exit();
		}
	}
	
	public void menuPressed(){
	}
}
