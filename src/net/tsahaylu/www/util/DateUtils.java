package net.tsahaylu.www.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.ocpsoft.prettytime.PrettyTime;

public class DateUtils 
{
	private final static SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private static PrettyTime prettyTime = new PrettyTime();
	private static TimeZone utctimezone = TimeZone.getTimeZone("UTC");
	private static TimeZone localtimezone = TimeZone.getDefault();
	
	public static Date formatSendTime(String sendtimes)
	{
		myFormatter.setTimeZone(utctimezone);
		Date sendtime = null;
		try {
			sendtime = myFormatter.parse(sendtimes);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendtime;
	}	
	
	
	public static String formatSendTime(Date sendtime)
	{
		myFormatter.setTimeZone(localtimezone);
		String sendtimes=myFormatter.format(sendtime);
		
		return sendtimes;
	}	
	
	
	public static Date getCurrentTime()
	{
		myFormatter.setTimeZone(utctimezone);
		
		Date now = null;
		String nows=myFormatter.format(new Date());
		try {
			now = myFormatter.parse(nows);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return now;
	}
	
	public static String getCurrenTimeString()
	{
		myFormatter.setTimeZone(utctimezone);
		String nows=myFormatter.format(new Date());

		return nows;
	}
	
	public static String getRelativeTime(Date date)
	{
		return prettyTime.format(date);	
	}
	
	}
