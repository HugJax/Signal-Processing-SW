package fr.mtb.api.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class Date extends GregorianCalendar {
	

	private int microseconds = 0;

	
	/*public final static DateFormat COMPLETE_DATE_FORMAT = new SimpleDateFormat("yyyy'/'MM'/'dd 'at' HH.mm.ss.SSS",Locale.ENGLISH);
	public final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy'/'MM'/'dd",Locale.ENGLISH);*/	
	public final static DateFormat COMPLETE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS",Locale.ENGLISH);
	//public final static DateTimeFormatter COMPLETE_DATE_FORMAT_MICROF = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss:NNNNNNNNN",Locale.ENGLISH);
	public final static DateFormat COMPLETE_DATE_FORMAT_MICROF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS:SSS",Locale.ENGLISH);
	public final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
	public final static DateFormat DATE_FORMAT_TABULAR = new SimpleDateFormat("yyyy'\t'MM'\t'dd '\t' HH.mm '\t' ss '\t' SSS",Locale.ENGLISH);
	public final static DateFormat DIRECTORY_DATE_FORMAT = new SimpleDateFormat("yyyy'-'MM'-'dd 'at' HH'h'mm",Locale.ENGLISH);
	public final static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS",Locale.ENGLISH);
	public final static DateFormat TIME_FILE_FORMAT = new SimpleDateFormat("HH'H'mm",Locale.ENGLISH);


	public static final int DEFAULT_YEAR = 1970; 
	



	
	
	/**
	 * Creates a Date object with provided parameters
	 * @param day
	 * @param month : 1 to 12
	 * @param year : 
	 * @param hours : 1 to 24
	 * @param minutes
	 * @param seconds
	 */
	public Date(int day, int month, int year, int hours, int minutes, int seconds) {	
		super(year, month - 1, day, hours, minutes, seconds);
		this.microseconds = 0;
	}



	
	
	/**
	 * method to update this using a string in date format  
	 * @param stringDate, accepted formats are yyyy:mm:dd:hh:mm:ss:mmm (7 int fields) or  yyyy:mm:dd:hh:mm:ss:mmm:µµµ (8 int fields)
	 * or yyyy-mm-dd-hh-mm-ss:mmm (7 int fields) or  yyyy-mm-dd-hh-mm-ss-mmm-µµµ
	 * month ranges from 1 to 12
	 * hour ranges from Oh to 23h
	 * @return true iff stringDate is of a correct format
	 */
	private boolean fromStringToDate(String stringDate) {

		final int TIME_IN_MS_PARTS_NUMBER = 7;
		final int TIME_IN_MICRO_PARTS_NUMBER = 8;
		String[] part = stringDate.split("[- :]+"); // to split and extract
													// parts of the string
													// separated by "-", " " or
													// ":"
		if (part.length < TIME_IN_MS_PARTS_NUMBER) {
			System.out.println("Incorrect Date Format");
			return false;
		}

		if (Date.isValideFormat(stringDate, COMPLETE_DATE_FORMAT)) {
			this.set(Calendar.YEAR, Integer.parseInt(part[0]));
			this.set(Calendar.MONTH, Integer.parseInt(part[1]) - 1);
			// months starts at 0 in GregorianCalendar
			this.set(Calendar.DAY_OF_MONTH, Integer.parseInt(part[2]));
			this.set(Calendar.HOUR_OF_DAY, Integer.parseInt(part[3]));
			this.set(Calendar.MINUTE, Integer.parseInt(part[4]));
			this.set(Calendar.SECOND, Integer.parseInt(part[5]));
			this.set(Calendar.MILLISECOND, Integer.parseInt(part[6]));

	
			if (part.length == TIME_IN_MICRO_PARTS_NUMBER) {
				this.microseconds = Integer.parseInt(part[7]);
			} else {
				this.microseconds = 0;
			}
			return true;

		} else { // invalid date format
			return false;
		}
	}

	/**
	 * Creates a date object with PC current time, up to the ms
	 */
	public Date() {
		super(); // current date of the laptop, up to ms
		this.microseconds = 0;
	}
	
	/**
	   * Constructor of date from a string
	   * 
	   * @param stringDate : absolute date (string) with one of the following two formats:
	   *  yyyy-MM-dd HH:mm:ss:SSS
	   *  yyyy-MM-dd HH:mm:ss:SSS:SSS
	   *  
	   */
	public Date(String stringDate){
		this(); // date with the current time
		this.fromStringToDate(stringDate);	// updates this if stringDate is of a correct format	
		
	}
	
	
	
	/**
	 * @return the day
	 */
	public int getDay() {
		return this.get(Calendar.DAY_OF_MONTH);
	}


	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.set(Calendar.DAY_OF_MONTH, day);
	}


	/**
	 * @return the month from 1 to 12
	 */
	public int getMonth() {
		return this.get(Calendar.MONTH) + 1;
	}


	/**
	 * @param month the month to set
	 * @requires 1 < month < 12
	 */
	public void setMonth(int month) {
		this.set(Calendar.MONTH, month - 1);
	}


	/**
	 * @return the year
	 */
	public int getYear() {
		return this.get(Calendar.YEAR);
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.set(Calendar.YEAR, year);
	}


	/**
	 * @return the hours from 0 to 23
	 */
	public int getHours() {
		return this.get(Calendar.HOUR_OF_DAY);
	}


	/**
	 * @param hours the hours to set
	 * @requires 0 <= hours <= 23
	 */
	public void setHours(int hours) {

		this.set(Calendar.HOUR_OF_DAY, hours);
	}

 
	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return this.get(Calendar.MINUTE);
	}


	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
	
		this.set(Calendar.MINUTE, minutes);
	}


	/**
	 * @return the second
	 */
	public int getSeconds() {
		return this.get(Calendar.SECOND);
	}


	/**
	 * @param seconds the second to set
	 */
	public void setSeconds(int seconds) {
		this.set(Calendar.SECOND, seconds);
	}


	/**
	 * @return the milliseconds
	 */
	public int getMilliseconds() {
		return this.get(Calendar.MILLISECOND);

	}


	/**
	 * @param milliseconds the milliseconds to set
	 */
	public void setMilliseconds(int milliseconds) {

		this.set(Calendar.MILLISECOND, milliseconds);
	}


	/**
	 * @return the microseconds
	 */
	public int getMicroSeconds() {
		return this.microseconds;
	}


	/**
	 * @param microseconds to set
	 */
	public void setMicroseconds(int microseconds) {
		this.microseconds = microseconds;
		
	}
	
	

	/**
	 * @return the calendar
	 */
	public GregorianCalendar getGregorianFormatDate() {
		return this;
	}
	
	
	/**
	 *  update attributes, using date calendar
	 */
	public void updateUsingCalendar() {
		this.addTimeInMilliseconds(0);
	}



	
	/**
	 * Date setter from a date type
	 * @param other (Date)
	 */
	public void setDate(Date other) {
		this.set(other.getYear(), other.getMonth() - 1, other.getDay(), other.getHours(), other.getMinutes(), other.getSeconds());
		this.set(Calendar.MILLISECOND,other.getMilliseconds());
		this.microseconds = other.microseconds;
	}
	
	/**
	 * Date setter from a string type 
	 * @param stringDate
	 */
	public void setDate(String stringDate) {
		this.fromStringToDate(stringDate);		

	}
	
	
	/**
	 * add any number of ms to the current date without changing the microseconds field
	 * @param timeIncrement time increment in ms (any long value) to add to the date object
	 */
	public void addTimeInMilliseconds(long timeIncrement) {
		long dateTimeInMs = this.getTimeInMillis() + timeIncrement;
		this.setTimeInMillis(dateTimeInMs);
		}
	
	@Override
	public String toString() {
		String currentDateString;
		currentDateString = COMPLETE_DATE_FORMAT.format(this.getTime());
		return currentDateString;
	}
	
	public String encodingToString() {
	String currentDateString = "";
	currentDateString += "day = " + this.getDay() + "\n";
	currentDateString += "month = " + this.getMonth() + "\n";
	currentDateString += "year = " + this.getYear() + "\n";
	currentDateString += "hours = " + this.getHours() + "\n";
	currentDateString += "minutes = " + this.getMinutes() + "\n";
	currentDateString += "seconds = " + this.getSeconds() + "\n";
	if (this.getMilliseconds() != 0) {
		currentDateString += "millis = " + this.getMilliseconds() + "\n";
	}
	if (this.microseconds != 0) {
		currentDateString += "micros = " + this.getMicroSeconds() + "\n";
	}
	return currentDateString;
	}
	
	public String toStringDirectoryFormat() {
		String currentDateString = DIRECTORY_DATE_FORMAT.format(this.getTime());
		return currentDateString;
	}
	/**
	 * 
	 * @return
	 */
	public String toStringCompleteDateFormat() {
		String CompleteDateString = COMPLETE_DATE_FORMAT.format(this.getTime());
		return CompleteDateString;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toStringCompleteDateFormatMicro() {

	
		String CompleteDateString = "";
		String zero = "0";
		
		

//		new String(new char[4 - String.valueOf(this.year)
//		.length()]).replace("\0", zero)
//		+
		CompleteDateString +=  this.getYear()
				+ "-"
				+ new String(new char[2 - String.valueOf(this.getMonth()).length()])
						.replace("\0", zero)
				+ this.getMonth()
				+ "-"
				+ new String(new char[2 - String.valueOf(this.getDay()).length()])
						.replace("\0", zero)
				+ this.getDay()
				+ " "
				+ new String(new char[2 - String.valueOf(this.getHours()).length()])
						.replace("\0", zero)
				+ this.getHours()
				+ ":"
				+ new String(
						new char[2 - String.valueOf(this.getMinutes()).length()])
						.replace("\0", zero)
				+ this.getMinutes()
				+ ":"
				+ new String(
						new char[2 - String.valueOf(this.getSeconds()).length()])
						.replace("\0", zero)
				+ this.getSeconds()
				+ ":"
				+ new String(new char[3 - String.valueOf(this.getMilliseconds())
						.length()]).replace("\0", zero)
				+ this.getMilliseconds()
				+ ":"
				+ new String(new char[3 - String.valueOf(this.getMicroSeconds())
						.length()]).replace("\0", zero) + this.getMicroSeconds();

		return CompleteDateString;  
		
	}


	public String toStringTimeFormat() {
		String currentTimeString = TIME_FORMAT.format(this.getTime());
		return currentTimeString;
	}
	
	public String toStringTimeFileFormat() {
		String currentTimeString = TIME_FILE_FORMAT.format(this.getTime());
		return currentTimeString;
	}
	
	public String toStringDateFormat() {
		String currentDateString = DATE_FORMAT.format(this.getTime());
		return currentDateString;

	}
	
	public String toStringDateFileFormat() {
		String currentDateString = DIRECTORY_DATE_FORMAT.format(this.getTime());
		return currentDateString;
	}
	
	
	
	@Override
	public boolean equals(Object otherDate) {
		if (otherDate instanceof Date) {
			return this.toString().equals(otherDate.toString());
		}
		else {
			return false;
		}
	}

	
	/**
	 * method to check if the date the program uses  has the adequate format : yyyy-MM-dd HH:mm:ss:SSS for example 
	 * @param dateStr
	 * @return true if the date format is valid 
	 */
	public static boolean isValideFormat(String dateStr, DateFormat dateFormat) {
		// TODO Auto-generated method stub

		try {
			dateFormat.setLenient(false);
			dateFormat.parse(dateStr);
		} catch (Exception e) {
			return false;
		}

		return true;
	}



	public Time getTimeDifferenceBetweenDates(Date firstDate, Date secondDate) {
		int firstDateMilliseconds =
				firstDate.getSeconds() + firstDate.getMinutes() * 60 + firstDate.getHours() * 3600;
		int secondDateMilliseconds =
				secondDate.getSeconds() + secondDate.getMinutes() * 60 + secondDate.getHours() * 3600;

		int differenceMilliseconds = Math.abs(secondDateMilliseconds - firstDateMilliseconds);

		int hoursDifference = differenceMilliseconds%3600;
		int minutesDifference = (differenceMilliseconds - hoursDifference * 3600)%60;
		int secondsDifference = differenceMilliseconds - hoursDifference * 3600 - minutesDifference * 60;

		Time difference = new Time(hoursDifference, minutesDifference, secondsDifference);

		return difference;
	}



	
}
