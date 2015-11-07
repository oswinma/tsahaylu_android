package com.tsahaylu.www.adapter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.tsahaylu.www.util.CommonUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetworkTool {
	
	public String HttpGetHttpURLConnection(String urlStr) {
        
        URL url = null;
        StringBuffer sb = new StringBuffer();//字符串数据的拼接
		try {
		url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
	        InputStream in;
			in = conn.getInputStream();
			  BufferedReader br = new BufferedReader(
				        new InputStreamReader(in));//获得输入流的包装流
				        String str = null;//存储读取出的一行数据
	
						while ((str = br.readLine()) != null) {//做判断是不是读完了
						sb.append(str);//若没读完，则拼接
						}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        System.out.println(sb);
        
        return sb.toString();

    }
	
	/**
     * 从网络中获取图片，以流的形式返回
     * @return
     */
    public static Bitmap getImageBitmap(String imageurl) throws IOException {
    	
        InputStream inputStream = null;
        Bitmap bitmap=null;
        URL url = new URL(imageurl);                    //服务器地址
        if (url != null) {
            //打开连接
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);//设置网络连接超时的时间为3秒
            httpURLConnection.setRequestMethod("GET");        //设置请求方法为GET
            httpURLConnection.setDoInput(true);                //打开输入流
            int responseCode = httpURLConnection.getResponseCode();    // 获取服务器响应值
            if (responseCode == HttpURLConnection.HTTP_OK) {        //正常连接
                inputStream = httpURLConnection.getInputStream();        //获取输入流
            }                    
            bitmap = BitmapFactory.decodeStream(inputStream);            
        }
        return bitmap;
    }
	
    

    public static String HttpPostHttpURLConnection(Map<String, String> params, String urlStr) {
        
    	
    	String datastr=CommonUtils.getRequestData(params).toString();
        byte[] data =datastr.getBytes(); //获得请求体
        try {            
            URL url = new URL(urlStr);
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(30000);        //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");    //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            //httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            //设置请求体的长度
           httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            
          // httpURLConnection.connect();
            
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
           	outputStream.flush();
            
           // DataOutputStream dos = new DataOutputStream(outputStream);
           // dos.writeBytes("email="+URLEncoder.encode("1@test.com", "utf-8"));
           // DataOutputStream dos1 = new DataOutputStream(outputStream);          //
          	//dos1.writeBytes("password="+URLEncoder.encode("1qaz2wsx", "utf-8"));
                              
          	outputStream.flush();
          	httpURLConnection.disconnect();
          	
            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
            
            outputStream.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return "";
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        System.out.println(resultData);
        return resultData;
    }
}

