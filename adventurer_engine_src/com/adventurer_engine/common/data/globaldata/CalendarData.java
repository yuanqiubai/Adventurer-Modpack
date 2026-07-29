/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by
 */
package com.adventurer_engine.common.data.globaldata;

import com.adventurer_engine.common.data.globaldata.IGlobalData;

public class CalendarData
extends IGlobalData {
    private long calendarValue = -1L;
    private static final String CALENDAR_VALUE_NAME = "calendarValue";
    private static final int TICK_EACH_HOUR = 1200;
    private static final int TICK_EACH_DAY = 24000;
    private static final int TICK_EACH_MONTH = 720000;
    private static final int TICK_EACH_YEAR = 8640000;

    CalendarData() {
    }

    public long getCalendarValue() {
        return this.calendarValue;
    }

    public void upDataCalendar() {
        ++this.calendarValue;
        this.modified = true;
    }

    public void addDataCalendar(long add) {
        this.calendarValue += add;
        this.modified = true;
    }

    @Override
    public String saveFile() {
        return "calendar_value.dat";
    }

    @Override
    public boolean initialed() {
        return this.calendarValue != -1L;
    }

    @Override
    public void setDefaultValue() {
        this.calendarValue = 0L;
    }

    @Override
    public void readNBTTagCompound(by nbt) {
        this.calendarValue = nbt.f(CALENDAR_VALUE_NAME);
    }

    @Override
    public void writeNBTTagCompound(by nbt) {
        nbt.a(CALENDAR_VALUE_NAME, this.calendarValue);
    }

    public static CalendarData getInstance() {
        return new CalendarData();
    }

    public void setYear(int year) {
        this.calendarValue %= 8640000L;
        this.calendarValue += (long)year * 8640000L;
        this.modified = true;
    }

    public void setMonth(int month) {
        int delta = month - this.getCurrentMonth();
        this.calendarValue += (long)delta * 720000L;
        this.modified = true;
    }

    public void setDay(int day) {
        int delta = day - this.getCurrentDay();
        this.calendarValue += (long)delta * 24000L;
        this.modified = true;
    }

    public void addYear(int year) {
        this.calendarValue += (long)year * 8640000L;
        this.modified = true;
    }

    public void addMonth(int month) {
        this.calendarValue += (long)month * 720000L;
        this.modified = true;
    }

    public void addDay(int day) {
        this.calendarValue += (long)day * 24000L;
        this.modified = true;
    }

    public int getCurrentYear() {
        return (int)(this.calendarValue / 8640000L);
    }

    public int getCurrentMonth() {
        return (int)(this.calendarValue % 8640000L / 720000L);
    }

    public int getCurrentDay() {
        return (int)(this.calendarValue % 720000L / 24000L);
    }

    public void setDayTime(int tick) {
        this.calendarValue -= this.calendarValue % 24000L;
        this.calendarValue += (long)(tick % 24000);
        this.modified = true;
    }
}

