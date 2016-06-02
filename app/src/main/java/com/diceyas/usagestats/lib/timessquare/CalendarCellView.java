// Copyright 2013 Square, Inc.

package com.diceyas.usagestats.lib.timessquare;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.diceyas.usagestats.lib.timessquare.MonthCellDescriptor.RangeState;
import com.diceyas.usagestats.R;

public class CalendarCellView extends TextView {

  private static final int[] STATE_SELECTABLE = {
      R.attr.state_selectable
  };
  private static final int[] STATE_CURRENT_MONTH = {
      R.attr.state_current_month
  };
  private static final int[] STATE_TODAY = {
      R.attr.state_today
  };
  private static final int[] STATE_RANGE_FIRST = {
      R.attr.state_range_first
  };
  private static final int[] STATE_RANGE_MIDDLE = {
      R.attr.state_range_middle
  };
  private static final int[] STATE_RANGE_LAST = {
      R.attr.state_range_last
  };
  
  private static final int[] STATE_MRAK_TEXT = {
      R.attr.state_mark_text
  };

  private boolean isSelectable = false;
  private boolean isCurrentMonth = false;
  private boolean isToday = false;
  private int isMark = 0;
  private boolean isMarkText = false;
  private RangeState rangeState = RangeState.NONE;
  private Paint paint = new Paint(Paint.SUBPIXEL_TEXT_FLAG
          |Paint.ANTI_ALIAS_FLAG);
  
  public CalendarCellView(Context context) {
    super(context);
  }

  public CalendarCellView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CalendarCellView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setSelectable(boolean isSelectable) {
    this.isSelectable = isSelectable;
    refreshDrawableState();
  }

  public void setCurrentMonth(boolean isCurrentMonth) {
    this.isCurrentMonth = isCurrentMonth;
    refreshDrawableState();
  }

  public void setToday(boolean isToday) {
    this.isToday = isToday;
    refreshDrawableState();
  }

  public void setRangeState(MonthCellDescriptor.RangeState rangeState) {
    this.rangeState = rangeState;
    refreshDrawableState();
  }



  public void setMark(int isMark) {
	this.isMark = isMark;
  }

  public void setMarkText(boolean isMarkText) {
	this.isMarkText = isMarkText;
	refreshDrawableState();
  }

@Override protected int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 4);

    if (isSelectable) {
      mergeDrawableStates(drawableState, STATE_SELECTABLE);
    }

    if (isCurrentMonth) {
      mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
    }

    if (isToday) {
      mergeDrawableStates(drawableState, STATE_TODAY);
    }

    if (isMarkText) {
      mergeDrawableStates(drawableState, STATE_MRAK_TEXT);
    }

    if (rangeState == MonthCellDescriptor.RangeState.FIRST) {
      mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
    } else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE) {
      mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
    } else if (rangeState == RangeState.LAST) {
      mergeDrawableStates(drawableState, STATE_RANGE_LAST);
    }

    return drawableState;
  }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isMark == 1) {
			paint.setColor(0xffda0a13);
            paint.setStrokeWidth(7.0f);
            canvas.drawLine(getWidth() -100,40,getWidth()-40,100,paint);
            canvas.drawLine(getWidth() -40,40,getWidth()-100,100,paint);
		}
        if (isMark == 2) {
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(7.0f);
            canvas.drawLine(getWidth() -100,60,getWidth()-80,100,paint);
            canvas.drawLine(getWidth() -40,40,getWidth()-80,100,paint);
        }
	}


}