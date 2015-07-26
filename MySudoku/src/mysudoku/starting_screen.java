package mysudoku;

import com.example.mysudoku.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class starting_screen extends View {
	private static final String TAG = "Sudoku";
	private static final String SELX = "selX";
	private static final String SELY = "selY";
	private static final String VIEW_STATE = "viewState";
	private static final int ID = 42;
	
	private final main_logic play_it;
	private float width; 
	private float height;
	private int selX; 
	private int selY; 
	private final Rect selRect = new Rect();

	public starting_screen(Context context) {
		super(context);
		this.play_it = (main_logic) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setId(ID);
	}
	
	/**
	 * This method will generate the 9*9 boxes
	 *
	 *  
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w / 9f;
		height = h / 9f;
		
		System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		
		
		getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		super.onSizeChanged(w, h, oldw, oldh);
			}

	
	/**
	 * This method will return the x and y direction of box.
	 *
	 *  
	 */
	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * width), (int) (y * height),
				(int) (x * width + width), (int) (y * height + height));
			}

	
	/**
	 * This method will generate the black line to make 3*3 boxes distinct
	 *
	 *  
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		
		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.puzzle_light));

		
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}
		
		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}

		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);
		
		FontMetrics fm = foreground.getFontMetrics();
		
		float x = width / 2;
		
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(this.play_it.getTileString(i, j), i * width + x, j
						* height + y, foreground);
			}
		}
		
		
		Log.d(TAG, "selRect=" + selRect);
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);
	}

	

	/**
	 * This method will call after selectiong the number.
	 *
	 *  
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_UP:
				select(selX, selY - 1);
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				select(selX, selY + 1);
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				select(selX - 1, selY);
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				select(selX + 1, selY);
				break;
			case KeyEvent.KEYCODE_0:
			case KeyEvent.KEYCODE_SPACE:
				setSelectedTile(0);
				break;
			case KeyEvent.KEYCODE_1:
				setSelectedTile(1);
				break;
			case KeyEvent.KEYCODE_2:
				setSelectedTile(2);
				break;
			case KeyEvent.KEYCODE_3:
				setSelectedTile(3);
				break;
			case KeyEvent.KEYCODE_4:
				setSelectedTile(4);
				break;
			case KeyEvent.KEYCODE_5:
				setSelectedTile(5);
				break;
			case KeyEvent.KEYCODE_6:
				setSelectedTile(6);
				break;
			case KeyEvent.KEYCODE_7:
				setSelectedTile(7);
				break;
			case KeyEvent.KEYCODE_8:
				setSelectedTile(8);
				break;
			case KeyEvent.KEYCODE_9:
				setSelectedTile(9);
				break;
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				play_it.clickDialogue(selX, selY);
				break;
			default:
				return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	

	/**
	 * This method will return true when user touches the box.
	 *
	 *  
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		select((int) (event.getX() / width), (int) (event.getY() / height));
		play_it.clickDialogue(selX, selY);
		Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);
		return true;
	}

	/**
	 * This method will return true when user touches the box.
	 *
	 *  
	 */
	public void setSelectedTile(int tile) {
		if (play_it.setTileIfValid(selX, selY, tile)) {
			invalidate();
					} else {
			 
			Log.d(TAG, "setSelectedTile: invalid: " + tile);
						}
		}
	
	
	/**
	 * This method will return the direction 
	 *
	 *  
	 */
	private void select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Log.d(TAG, "onRestoreInstanceState");
		Bundle bundle = (Bundle) state;
		select(bundle.getInt(SELX), bundle.getInt(SELY));
		super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
	}

	
	/**
	 * This method will save the numer when erite 
	 *
	 *  
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable p = super.onSaveInstanceState();
		Log.d(TAG, "onSaveInstanceState");
		Bundle bundle = new Bundle();
		bundle.putInt(SELX, selX);
		bundle.putInt(SELY, selY);
		bundle.putParcelable(VIEW_STATE, p);
		return bundle;
	}
}
