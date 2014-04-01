package me.priezt.amazingblocks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication implements ActionResolver {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new AmazingBlocks(this), cfg);
    }

	@Override
	public void showToast(final String toastMessage) {
		this.runOnUiThread(new Runnable(){
			public void run(){
				Context context = MainActivity.this.getApplicationContext();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, toastMessage, duration);
				toast.show();
			}
		});
	}

	@Override
	public void openURL(String url) {
		Uri myUri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MainActivity.this.getApplicationContext().startActivity(intent);
	}
}