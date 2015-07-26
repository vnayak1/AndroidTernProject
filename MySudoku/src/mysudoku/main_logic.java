package mysudoku;

import com.example.mysudoku.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class main_logic extends Activity {
	private static final String TAG = "Sudoku";

	public static final String KEY_DIFFICULTY = "org.example.mysudoku.difficulty";
	public static final int start_Up = 0;
	
	private static final String PREF_PUZZLE = "puzzle";
	protected static final int DIFFICULTY_CONTINUE = -1;

	private final String game_string = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";

	private int puzzle[];
	
	private starting_screen new_view;
	
	private final int used[][][] = new int[9][9][];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, 0);

		puzzle = sudoku_logic_game(diff);
		selected_number();
		new_view = new starting_screen(this);
		setContentView(new_view);
		new_view.requestFocus();
		
	}

	
	
	/**
	 * This method will return the direction 
	 *
	 *  
	 */
	private void selected_number() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = numer_tiles(x, y);
				// Log.d(TAG, "used[" + x + "][" + y + "] = "
				// + toPuzzleString(used[x][y]));
			}
		}
	}

	
	/**
	 * This method will return the number of boxes enterd 
	 *
	 *  
	 */
	private int[] numer_tiles(int x, int y) {
		int c[] = new int[9];
		
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y);
			if (t != 0)
				c[t - 1] = t;
		}
		
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i);
			if (t != 0)
				c[t - 1] = t;
		}

		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j);
				if (t != 0)
					c[t - 1] = t;
			}
		}
		
		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}
		return c1;
	}

	
	
	/**
	 * This method will start new screen when it aplies.
	 *
	 *  
	 */
	private int[] sudoku_logic_game(int diff) {
		String puz;
		switch (diff) {
			
			case start_Up:
			default:
				puz = game_string;
				break;
			}
		return fromPuzzleString(puz);
	}

	
	/**
	 * This method will show the boxes with numbers
	 *
	 *  
	 */
	protected String getTileString(int x, int y) {
		int v = getTile(x, y);
		if (v == 0)
			return "";
		else
			return String.valueOf(v);
	}

	
	/**
	 * This method will generate dialogue box 
	 *
	 *  
	 */
	protected void clickDialogue(int x, int y) {
		int tiles[] = getUsedTiles(x, y);
		if (tiles.length == 9) {
			Toast toast = Toast.makeText(this, R.string.no_moves_label,
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			Log.d(TAG, "showKeypad: used=" + toPuzzleString(tiles));
			Dialog v = new dialog_start(this, tiles, new_view);
			v.show();
		}
	}

		
	/**
	 * This method  will set valid numer for box
	 *
	 *  
	 */
	protected boolean setTileIfValid(int x, int y, int value) {
		int tiles[] = getUsedTiles(x, y);
		if (value != 0) {
			for (int tile : tiles) {
				if (tile == value)
					return false;
			}
		}
		setTile(x, y, value);
		selected_number();
		return true;
	}

	static private String toPuzzleString(int[] puz) {
		StringBuilder buf = new StringBuilder();
		for (int element : puz) {
			buf.append(element);
		}
		return buf.toString();
	}

	static protected int[] fromPuzzleString(String string) {
		int[] puz = new int[string.length()];
		for (int i = 0; i < puz.length; i++) {
			puz[i] = string.charAt(i) - '0';
		}
		return puz;
	}

	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

	private int getTile(int x, int y) {
		return puzzle[y * 9 + x];
	}

	private void setTile(int x, int y, int value) {
		puzzle[y * 9 + x] = value;
	}


}
