package com.tsahaylu.www.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsahaylu.www.R;
import com.tsahaylu.www.dto.MessageInfo;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.ui.Activity_UserProfile;
import com.tsahaylu.www.util.DateUtils;

public class MessageAdapter extends BaseAdapter {	
	
	 private List<MessageInfo> comments;// Ҫ�󶨵�����
	 private int resource;// �󶨵�һ����Ŀ�����id�������м�Ϊitem.xml
	 private LayoutInflater inflater;// �����������������ʹ��һ��xml�ļ�����һ��View���󣬿���ͨ��Context��ȡʵ������
	 CoreService cs=CoreService.getCoreService();
	 
	public MessageAdapter(Context context, ArrayList<MessageInfo> clist, int resource) {
		// TODO Auto-generated constructor stub
		
		  inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  this.resource = resource;
		  this.comments = clist;	
		  
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		   TextView sendtimev;	  
		   TextView contentv;
		   TextView nicknamev;
		   ImageView avatarURLv;
		   
		  
		   ViewCache cache;
		  if (convertView == null) {// ��ʾ��һҳ��ʱ��convertViewΪ��

		   
		   cache = new ViewCache();
		   
		   convertView = inflater.inflate(resource, null);// ������Ŀ����

		   cache.sendtimev = (TextView) convertView.findViewById(R.id.sendtime);
		   cache.contentv = (TextView) convertView.findViewById(R.id.content);
		   cache.nicknamev  = (TextView) convertView.findViewById(R.id.nickname);
		   cache.avatarURLv = (ImageView) convertView.findViewById(R.id.avatarURL);

		   convertView.setTag(cache);
		  } else {
			  
		   cache = (ViewCache) convertView.getTag();
		  }	
		  

		   sendtimev = cache.sendtimev;
		   contentv = cache.contentv;
		   nicknamev = cache.nicknamev;
		   avatarURLv = cache.avatarURLv;
		 
		  MessageInfo comment = comments.get(position);
		  // ʵ�����ݰ�
		  	  
		  String avatarURL=comment.getAvatarURL();
		  if (avatarURL!=null && avatarURL!="")
		  cs.setImageView(avatarURLv, avatarURL);
		  else
			  avatarURLv.setImageResource(R.drawable.default_avatar);

		  
		  String sendtime=DateUtils.getRelativeTime(comment.getSendtime());
		  sendtimev.setText(sendtime);

		  contentv.setText(comment.getContent());
		  nicknamev.setText(comment.getNickname());		  
		  
		  avatarURLv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivity_UserProfile();
			}
			  
			  
		  });
		  
		  return convertView;
		  
	}

	
	 private static class ViewCache {
		 
		 public TextView contentv;
		  public TextView sendtimev;
		  public TextView nicknamev;
		  public ImageView avatarURLv;		  
		 }
	 
	   	public void startActivity_UserProfile()
		{
				Intent intent = new Intent(inflater.getContext().getApplicationContext(), Activity_UserProfile.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				inflater.getContext().getApplicationContext().startActivity(intent);		
		}
}
