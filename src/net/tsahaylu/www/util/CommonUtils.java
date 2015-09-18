package net.tsahaylu.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.widget.TextView;
import net.tsahaylu.www.common.Constants;


public class CommonUtils
{
	
	
	public static String getMailBody(String link) {

		try {
		URL url = new URL(link);
			
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf8")); 			
			
		  StringBuilder sb = new StringBuilder();
		  String line;
          while ((line = br.readLine()) != null) {
          sb.append(line).append("\n");       }
          return sb.toString();  
         
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
		
	}
	

	
	public static String getRandomString() { //lengthï¿½ï¿½Ê¾ï¿½ï¿½ï¿½ï¿½Ö·ï¿½Ä³ï¿½ï¿½ï¿½
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < Constants.CODE_LENGTH; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }

	

	
	
	public static String getHostByUrl(String url)
	{
		
		String[] s = url.split("/");
 		return s[2];
	}
	
	public static boolean checkEmailFormat(String email){		
	    Pattern p = Pattern.compile(Constants.EMAILADDPATTERN);
	    Matcher m = p.matcher(email);
	    return m.find();
	}


    public static StringBuffer getRequestData(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer();        //´æ´¢·â×°ºÃµÄÇëÇóÌåÐÅÏ¢
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                            .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //É¾³ý×îºóµÄÒ»¸ö"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    

public static void stripUnderlines(TextView textView) {
	Spannable s = new SpannableString(textView.getText());
    URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
    for (URLSpan span: spans) {
        int start = s.getSpanStart(span);
        int end = s.getSpanEnd(span);
        s.removeSpan(span);
        span = new URLSpanNoUnderline(span.getURL());
        s.setSpan(span, start, end, 0);
    }
    textView.setText(s);
}


public static File getDiskCacheDir(Context context, String uniqueName) {  
    String cachePath;  
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
            || !Environment.isExternalStorageRemovable()) {  
        cachePath = context.getExternalCacheDir().getPath();  
    } else {  
        cachePath = context.getCacheDir().getPath();  
    }  
    return new File(cachePath + File.separator + uniqueName);  
}  


public static int getAppVersion(Context context) {  
    try {  
        PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),  
                0);  
        return info.versionCode;  
    } catch (NameNotFoundException e) {  
        e.printStackTrace();
    }  
    return 1;  
}  


}

class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }
    @Override public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        }
}



