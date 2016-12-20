package com.telegram.bot.core.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class SleepTimeGenerator {

	public int getRandomSleepTime()
	{
		int sleepTime;
		if (!isOffWork())
			sleepTime = getRandomNumberInRange(1, 30);
		else
			sleepTime = getRandomNumberInRange(1, 90);
		
		return sleepTime * 60 * 1000;
	}
	
	private int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

	}

	private boolean isOffWork() {
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new
																// calendar
																// instance
		calendar.setTime(new Date()); // assigns calendar to given date
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in
																// 24h format
		if (currentHour > 8 && currentHour < 17) {
			return false;
		} else {
			return true;
		}
	}
	
}
