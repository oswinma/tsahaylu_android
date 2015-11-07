package com.tsahaylu.www.adapter;

import java.util.List;

import com.tsahaylu.www.R;
import com.tsahaylu.www.dto.FavURLShow;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.ui.Activity_FavurlDetail;
import com.tsahaylu.www.ui.Activity_UserProfile;
import com.tsahaylu.www.util.CommonUtils;
import com.tsahaylu.www.util.DateUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavurlShowAdapter extends BaseAdapter {
	
	
	 private List<FavURLShow> favurlshows;// 要绑定的数据
	 private int resource;// 绑定的一个条目界面的id，此例中即为item.xml
	 private LayoutInflater inflater;// 布局填充器，它可以使用一个xml文件生成一个View对象，可以通过Context获取实例对象
	 CoreService cs=CoreService.getCoreService();
	 private Context context;
	 
	 public FavurlShowAdapter(Context context, List<FavURLShow> favurlhows, int resource) {
		 
		 this.context=context;
		 inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 this.resource = resource;
		 this.favurlshows = favurlhows;	  
	 }
	 
	 
	 @Override
	 public int getCount() {// 得到要绑定的数据总数
	  return favurlshows.size();
	 }
	 
	 
	 @Override
	 public Object getItem(int position) {// 给定索引值，得到索引值对应的对象
	  return favurlshows.get(position);
	 }
	 
	 
	 @Override
	 public long getItemId(int position) {// 获取条目id
	  return position;
	 }
	 
	 
	 private static class ViewCache {

	  public TextView urlv;
	  public TextView sendtimev;
	  public TextView favv;
	  public TextView titlev;
	  public ImageView iconv;
	  public TextView nicknamev;
	  public ImageView avatarURLv;
	  public TextView commentnumv;
	  
	 }
	 
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		   
		  ViewCache cache;
		   
		  if (convertView == null) {// 显示第一页的时候convertView为空
			  
			  cache = new ViewCache();			  
			  convertView = inflater.inflate(resource, null);// 生成条目对象	
			   cache.urlv = (TextView) convertView.findViewById(R.id.url);
			   cache.sendtimev = (TextView) convertView.findViewById(R.id.sendtime);
			   cache.favv = (TextView) convertView.findViewById(R.id.fav);
			   cache.titlev = (TextView) convertView.findViewById(R.id.title);
			   cache.iconv = (ImageView) convertView.findViewById(R.id.icon);
			   cache.nicknamev = (TextView) convertView.findViewById(R.id.nickname);
			   cache.avatarURLv = (ImageView) convertView.findViewById(R.id.avatarURL);
			   cache.commentnumv= (TextView) convertView.findViewById(R.id.commentnum);
			   
		   convertView.setTag(cache);
		  } else {
			  
		   cache = (ViewCache) convertView.getTag();
	   
		  }	  
		  
		  TextView urlv = cache.urlv;
		  TextView sendtimev = cache.sendtimev;
		  TextView favv = cache.favv;
		  TextView titlev = cache.titlev;
		  ImageView iconv = cache.iconv;
		  TextView nicknamev = cache.nicknamev;
		  ImageView avatarURLv = cache.avatarURLv;
		  TextView commentnumv=cache.commentnumv;
		   		 
		  FavURLShow favurlshow = favurlshows.get(position);
		  		  	  
		  final String avatarURL=favurlshow.getAvatarURL();
		  if (avatarURL!=null && avatarURL!="")
		  cs.setImageView(avatarURLv, avatarURL);
		  else
			  avatarURLv.setImageResource(R.drawable.default_avatar);
		  
		  String icon=favurlshow.getIcon();
		  if (icon!=null && icon!="")
			  	cs.setImageView(iconv, icon);
			  else
				iconv.setImageResource(R.drawable.defaulticon);
		  

		  CommonUtils.stripUnderlines(urlv);				
		  urlv.setText(favurlshow.getUrl());
		  
		  String sendtime=DateUtils.getRelativeTime(favurlshow.getSendtime());
		  sendtimev.setText(sendtime);
		  titlev.setText(favurlshow.getTitle());
		  commentnumv.setText(favurlshow.getCommentnum()+" comments");
		  
			final String userid=favurlshow.getFromid().toString();
			final String nickname=favurlshow.getNickname();
			nicknamev.setText(nickname);
				
			avatarURLv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {

				// TODO Auto-generated method stub
				startActivity_UserProfile(userid, avatarURL, nickname);
			}
			  
			  
		  });
		  return convertView;
		  
	}   
	
	
   	public void startActivity_UserProfile(String userid,String avatarURL,String nickname)
	{
			Intent intent = new Intent(context, Activity_UserProfile.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("userid", userid);
			intent.putExtra("avatarURL", avatarURL);			
			intent.putExtra("nickname", nickname);			
			((Activity) context).startActivityForResult(intent,0);  			  			
	}
   	
	}


