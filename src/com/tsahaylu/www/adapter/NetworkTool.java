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
        StringBuffer sb = new StringBuffer();//�ַ������ݵ�ƴ��
		try {
		url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
	        InputStream in;
			in = conn.getInputStream();
			  BufferedReader br = new BufferedReader(
				        new InputStreamReader(in));//����������İ�װ��
				        String str = null;//�洢��ȡ����һ������
	
						while ((str = br.readLine()) != null) {//���ж��ǲ��Ƕ�����
						sb.append(str);//��û���꣬��ƴ��
						}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        System.out.println(sb);
        
        return sb.toString();

    }
	
	/**
     * �������л�ȡͼƬ����������ʽ����
     * @return
     */
    public static Bitmap getImageBitmap(String imageurl) throws IOException {
    	
        InputStream inputStream = null;
        Bitmap bitmap=null;
        URL url = new URL(imageurl);                    //��������ַ
        if (url != null) {
            //������
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);//�����������ӳ�ʱ��ʱ��Ϊ3��
            httpURLConnection.setRequestMethod("GET");        //�������󷽷�ΪGET
            httpURLConnection.setDoInput(true);                //��������
            int responseCode = httpURLConnection.getResponseCode();    // ��ȡ��������Ӧֵ
            if (responseCode == HttpURLConnection.HTTP_OK) {        //��������
                inputStream = httpURLConnection.getInputStream();        //��ȡ������
            }                    
            bitmap = BitmapFactory.decodeStream(inputStream);            
        }
        return bitmap;
    }
	
    

    public static String HttpPostHttpURLConnection(Map<String, String> params, String urlStr) {
        
    	
    	String datastr=CommonUtils.getRequestData(params).toString();
        byte[] data =datastr.getBytes(); //���������
        try {            
            URL url = new URL(urlStr);
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(30000);        //�������ӳ�ʱʱ��
            httpURLConnection.setDoInput(true);                  //�����������Ա�ӷ�������ȡ����
            httpURLConnection.setDoOutput(true);                 //����������Ա���������ύ����
            httpURLConnection.setRequestMethod("POST");    //������Post��ʽ�ύ����
            httpURLConnection.setUseCaches(false);               //ʹ��Post��ʽ����ʹ�û���
            //������������������ı�����
            //httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            //����������ĳ���
           httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //�����������������д������
            
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
          	
            int response = httpURLConnection.getResponseCode();            //��÷���������Ӧ��
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //�������������Ӧ���
            }
            
            outputStream.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return "";
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //�洢������
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

