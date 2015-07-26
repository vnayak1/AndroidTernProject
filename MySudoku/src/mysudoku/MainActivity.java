package mysudoku;

import com.example.mysudoku.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "Sudoku";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View startgame = findViewById(R.id.startgame);
		startgame.setOnClickListener(this);
		
		}

	
	/**
	 * This method will call when button is clicked start game. 
	 *
	 *  
	 */
	public void onClick(View v) {
		switch (v.getId()) {
			
			case R.id.startgame:
				startGame(0);
				break;
			
							}}
							

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * This method will call the view next of the game.
	 *
	 *  
	 */
	private void startGame(int i) {
		Log.d(TAG, "clicked on " + i);
		Intent intent = new Intent(this, main_logic.class);
		intent.putExtra(main_logic.KEY_DIFFICULTY, i);
		
		System.out.println("hiiiiiiiiiiiiiiiiiiii123");
		
		startActivity(intent);
		
		System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
	}


}
