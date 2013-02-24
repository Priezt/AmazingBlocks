package me.priezt.amazingblocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ModifyPanel extends ControlPanel {
	public ArrayList<Button> buttons;
	public Block modifyBlock;
	
	public class Button{
		public static final float WIDTH = 100f;
		public static final float HEIGHT = 100f;
		
		public String name;
		public Texture icon;
		public float width;
		public float height;
		public float xPercent;
		public float yPercent;
		
		public Button(String n, String iconFile, float xp, float yp){
			name = n;
			icon = Tool.loadPicture(iconFile);
			xPercent = xp;
			yPercent = yp;
			width = WIDTH;
			height = HEIGHT;
		}
		
		public void draw(SpriteBatch batch){
			Tool.drawAt(batch, icon, Tool.root.screenWidth * xPercent, ControlPanel.PANELHEIGHT * yPercent, width, height);
		}
		
		public boolean pointIn(float _x, float _y){
			if(Math.abs(xPercent * Tool.root.screenWidth - _x) <= width / 2 && Math.abs(yPercent * ControlPanel.PANELHEIGHT - _y) <= height / 2){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void init(){
		buttons = new ArrayList<Button>();
		buttons.add(new Button("delete", "delete.png", 0.5f, 0.5f));
		buttons.add(new Button("rotateLeft", "rotateLeft.png", 0.25f, 0.5f));
		buttons.add(new Button("rotateRight", "rotateRight.png", 0.75f, 0.5f));
	}
	
	public void tapped(float x, float y){
		for(Button b : buttons){
			if(b.pointIn(x, y)){
				buttonAction(b);
			}
		}
	}
	
	public void buttonAction(Button b){
		if(b.name.equals("delete")){
//			Tool.log("remove block");
//			Tool.log(modifyBlock.toString());
			editBoard.mainBoard.remove(modifyBlock);
//			Tool.log("after remove");
			editBoard.mainBoard.unselectBlock();
			editBoard.setPanel(new NormalPanel());
		}else if(b.name.equals("rotateLeft")){
			modifyBlock.rotate(false);
		}else if(b.name.equals("rotateRight")){
			modifyBlock.rotate(true);
		}
	}
	
	public void draw(SpriteBatch batch){
		for(Button b : buttons){
			b.draw(batch);
		}
	}
}
