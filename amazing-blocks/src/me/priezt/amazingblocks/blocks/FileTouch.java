package me.priezt.amazingblocks.blocks;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import com.badlogic.gdx.Gdx;

import me.priezt.amazingblocks.Block;
import me.priezt.amazingblocks.Block.Direction;
import me.priezt.amazingblocks.JS;
import me.priezt.amazingblocks.Tool;

public class FileTouch extends Block {
	public void tapped(){
		try{
			OutputStream outputStream = Gdx.files.external("touch.txt").write(false);
			OutputStreamWriter osw = new OutputStreamWriter(outputStream);
			osw.write("test");
			osw.close();
			outputStream.close();
			Tool.portal.showToast("touch");
			Tool.portal.openURL("http://www.baidu.com");
			JS.run("var abc = 1234;");
		}catch(Exception e){
			Tool.log(e.getMessage());
		}
	}
}
