package me.priezt.amazingblocks;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureDict;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import me.priezt.amazingblocks.blocks.*;

public class AmazingBlocks implements ApplicationListener{
	public static int TOUCHDRAGDISTANCETHRESHOLD = 20;
	public static int TOUCHHOLDMILLISECONDTHRESHOLD = 1000;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private ShapeRenderer shapeRenderer;
	
	public float screenWidth;
	public float screenHeight;
	
	public Board board;
	
	@Override
	public void create() {
		Tool.init(this);
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		screenWidth = w;
		screenHeight = h;
		MainBoard mainBoard = new MainBoard(w, h);
		mainBoard.addBlock(new Ring(), 0, 0);
		mainBoard.addBlock(new Vibrator(), 2, 0);
		mainBoard.addBlock(new OpenWhiteBoard(), 0, 2);
		board = mainBoard;
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new MyInputProcessor());
		multiplexer.addProcessor(new GestureDetector(new MyGestureListener()));
		Gdx.input.setInputProcessor(multiplexer);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void dispose() {
		batch.dispose();
//		texture.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.3f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		shapeRenderer.begin(ShapeType.Rectangle);
		shapeRenderer.setColor(0f, 0f, 1f, 0.6f);
		board.draw(shapeRenderer, batch);
		Tool.textAt(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, board.height);

		batch.end();
		shapeRenderer.end();// This order is necessary!! Do not reverse this
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public class MyGestureListener implements GestureListener{
		@Override
		public boolean tap(float x, float y, int count, int button) {
			// TODO Auto-generated method stub
			board.tapped(x, screenHeight - y);
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			// TODO Auto-generated method stub
//			Tool.log("long press: " + x + "," + y);
			board.longPressed(x, screenHeight - y);
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			// TODO Auto-generated method stub
//			Tool.log("fling: " + velocityX + "," + velocityY);
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			// TODO Auto-generated method stub
//			Tool.log("pan: " + x + "," + y + " - " + deltaX + "," + deltaY);
			board.pan(x, screenHeight - y, deltaX, -deltaY);
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			// TODO Auto-generated method stub
//			Tool.log("zoom: " + initialDistance + "," + distance);
			board.zoom(initialDistance, distance);
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			// TODO Auto-generated method stub
			board.touchDown(x, screenHeight - y);
			return false;
		}
	}
	
	public class MyInputProcessor implements InputProcessor{

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			if(keycode == Keys.BACK){
				board.backPressed();
			}else if(keycode == Keys.MENU){
				board.menuPressed();
			}
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			board.touchUp(screenX, screenHeight - screenY);
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
