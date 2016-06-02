// Copyright 2012 Square, Inc.

package com.diceyas.usagestats.lib.timessquare;

import java.util.Date;

/** Describes the state of a particular date cell in a {@link CalendarView}. */
class MonthCellDescriptor {
  public enum RangeState {
    NONE, FIRST, MIDDLE, LAST
  }

  private final Date date;
  private final int value;
  private final boolean isCurrentMonth;
  private boolean isSelected;
  private final boolean isToday;
  private final boolean isSelectable;
  private int isMark;
  private boolean isMarkText;
  private RangeState rangeState;

  MonthCellDescriptor(Date date, boolean currentMonth, boolean selectable, boolean selected,
      boolean today, int value, RangeState rangeState) {
    this.date = date;
    isCurrentMonth = currentMonth;
    isSelectable = selectable;
    isSelected = selected;
    isToday = today;
    this.isMark = 0;
    this.isMarkText = false;
    this.value = value;
    this.rangeState = rangeState;
  }
  
  MonthCellDescriptor(Date date, boolean currentMonth, boolean selectable, boolean selected,
	      boolean today, int isMark, boolean isMarkText, int value, RangeState rangeState) {
	    this.date = date;
	    isCurrentMonth = currentMonth;
	    isSelectable = selectable;
	    isSelected = selected;
	    isToday = today;
	    this.isMark = isMark;
	    this.isMarkText = isMarkText;
	    this.value = value;
	    this.rangeState = rangeState;
	  }

  public Date getDate() {
    return date;
  }

  public boolean isCurrentMonth() {
    return isCurrentMonth;
  }

  public boolean isSelectable() {
    return isSelectable;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public boolean isToday() {
    return isToday;
  }

  public RangeState getRangeState() {
    return rangeState;
  }

  public void setRangeState(RangeState rangeState) {
    this.rangeState = rangeState;
  }

  public int getValue() {
    return value;
  }

  
  public int isMark() {
	return isMark;
  }

	public void setMark(int isMark) {
		this.isMark = isMark;
	}

   public boolean isMarkText() {
		return isMarkText;
	}

	public void setMarkText(boolean isMarkText) {
		this.isMarkText = isMarkText;
	}

@Override public String toString() {
    return "MonthCellDescriptor{"
        + "date="
        + date
        + ", value="
        + value
        + ", isCurrentMonth="
        + isCurrentMonth
        + ", isSelected="
        + isSelected
        + ", isToday="
        + isToday
        + ", isSelectable="
        + isSelectable
        + ", isMark="
        + isMark
        + ", isMarkText="
        + isMarkText
        + ", rangeState="
        + rangeState
        + '}';
  }
}
