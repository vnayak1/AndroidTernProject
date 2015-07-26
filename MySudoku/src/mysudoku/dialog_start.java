package mysudoku;

import com.example.mysudoku.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class dialog_start extends Dialog {

	protected static final String TAG = "Sudoku";

	private final View keys[] = new View[9];
	private View new_dialogue;
	private final int useds[];
	private final starting_screen start_game_new;

	public dialog_start(Context context, int useds[], starting_screen new_game) {
		super(context);
		this.useds = useds;
		this.start_game_new = new_game;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.keypad_title);
		setContentView(R.layout.keypad);
		findViews();
		for (int element : useds) {
			if (element != 0) {
				keys[element - 1].setVisibility(View.INVISIBLE);
			}
			find_event();
		}
		// ...
	}

	
	
	/**
	 * This method will generate the 3*3 boxes of line
	 *
	 *  
	 */
	private void find_event() {
		for (int i = 0; i < keys.length; i++) {
			final int t = i + 1;
			keys[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					done_game(t);
				}
			});
		}
		new_dialogue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				done_game(0);
			}
		});
	}
	
	/**
	 * This method will set the value of sudoku boxes
	 *
	 *  
	 */
	private void done_game(int tile) {
		start_game_new.setSelectedTile(tile);
		dismiss();
	}

	
	/**
	 * This method will create a view for the next game.
	 *
	 *  
	 */
	private void findViews() {
		new_dialogue = findViewById(R.id.keypad);
		keys[0] = findViewById(R.id.keypad_1);
		keys[1] = findViewById(R.id.keypad_2);
		keys[2] = findViewById(R.id.keypad_3);
		keys[3] = findViewById(R.id.keypad_4);
		keys[4] = findViewById(R.id.keypad_5);
		keys[5] = findViewById(R.id.keypad_6);
		keys[6] = findViewById(R.id.keypad_7);
		keys[7] = findViewById(R.id.keypad_8);
		keys[8] = findViewById(R.id.keypad_9);
	}

	
	/**
	 * This method will generate the new dialogue box when user want to set the number at a time.
	 *
	 *  
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int tile = 0;
		switch (keyCode) {
			case KeyEvent.KEYCODE_0:
			case KeyEvent.KEYCODE_SPACE:
				tile = 0;
				break;
			case KeyEvent.KEYCODE_1:
				tile = 1;
				break;
			case KeyEvent.KEYCODE_2:
				tile = 2;
				break;
			case KeyEvent.KEYCODE_3:
				tile = 3;
				break;
			case KeyEvent.KEYCODE_4:
				tile = 4;
				break;
			case KeyEvent.KEYCODE_5:
				tile = 5;
				break;
			case KeyEvent.KEYCODE_6:
				tile = 6;
				break;
			case KeyEvent.KEYCODE_7:
				tile = 7;
				break;
			case KeyEvent.KEYCODE_8:
				tile = 8;
				break;
			case KeyEvent.KEYCODE_9:
				tile = 9;
				break;
			default:
				return super.onKeyDown(keyCode, event);
		}
		if (isValid(tile)) {
			done_game(tile);
		}
		return true;
	}

	private boolean isValid(int tile) {
		for (int t : useds) {
			if (tile == t)
				return false;
		}
		return true;
	}
}