package com.tsahaylu.www.common;

public class Constants {

	
	public static final String URL_HOST = "http://tsahaylubeta.appspot.com"; 
    //public static final String URL_HOST = "http://192.168.1.191:8888";
    
    public static final String SESSION_USERID = "_USERID";
    public static final String COOKIE_USERID = "current_USERID";
    public static final int COOKIE_PERIOD = 60*24*3600;
    
    public static final String RECIEVE_GROUP_NAME = "Recieve";
    public static final String RECIEVE_GROUP_DES = "Default recieve group";
        
    public static final int CODE_LENGTH = 10;
    public static final int PAGE_SIZE = 10;
    public static final int MAX_TABS =20;

    public static final String ENCODE_METHOD ="MD5";
    public static final String SYSEMAIL= "info@tsahaylu.net";
    public static final String DEFAULT_EMAIL_FROM ="info@tsahaylu.net";
    
    public static final String TWITTER_API_KEY ="Jwp7H6jf5pS61Dsx28Uxg";
    public static final String TWITTER_API_SECRET ="G0KV7prWD7U1fierlXooZVGnMpmHSj1Y6b85B88";
    public static final String TWITTER_FRIENDS_LINK="https://api.twitter.com/1.1/friends/ids.json";
    public static final String TWITTER_CALLBACK_URL = "oauth://net.www.tsahaylu";
    public static final String STRING_EXTRA_AUTHENCATION_URL = "AuthencationUrl"; 
    
    public static final String FACEBOOK_API_KEY ="141416579340734";
    public static final String FACEBOOK_API_SECRET ="081cbda671968b5c7aeec24ed007cceb";
	public static final String FACEBOOK_FRIENDS_LINK = "https://graph.facebook.com/me/friends";
		
	public static final String GOOGLE_API_KEY ="www.tsahaylu.net";
    public static final String GOOGLE_API_SECRET ="v03mdhBGULG0GIWJXgAD-I4d";		
    public static final String GOOGLE_CONTACT_LINK="https://www.google.com/m8/feeds/contacts/default/full?alt=json&max-results=100";
    
    public static final String PAGE_TEMPLATE_EMAILCONFIRM="EmailTemplate/EmailConfirmation.html";
    public static final String PAGE_TEMPLATE_PASSRESET="EmailTemplate/ForgotPass.html";
    public static final String PAGE_TEMPLATE_INVITATION="EmailTemplate/Invitation.html";

    public static final String PAGE_LOGIN ="/login.html";
    public static final String PAGE_JSP_SIGNUP ="/jsp/OpenIDSignUp.jsp";
	public static final String PAGE_JSP_INVITESIGNUP ="/jsp/InviteSignUp.jsp";
    public static final String PAGE_SIGNUPE ="/signup.html";
    public static final String PAGE_HOME ="/new.html";
    public static final String PAGE_FRIENDS ="/friends.html";
    public static final String PAGE_FORGOT ="/forgot.html";
	public static final String PAGE_INVITATION = "/invitations.html";
    public static final String PAGE_RESETPASSWORD ="/jsp/ResetPass.jsp";
    public static final String PAGE_JSP_FORMREDIRECTOIN ="/jsp/formredirection.jsp";
    public static final String PAGE_JSP_NOTICE ="/jsp/notice.jsp";
	public static final String PAGE_JSP_AVATAR = "/setting_basic.jsp";
	public static final String PAGE_JSP_RESTPASS = "/jsp/ResetPass.jsp";
	public static final String PAGE_JSP_USERVIEW = "/jsp/UserView.jsp";
	public static final String PAGE_JSP_LINKPAGE = "/jsp/link.jsp";
	
    public static final String PUBLIC_ID="";
    
    public static final String DEFAULT_AVATAR_URL ="images/mystery-man.jpg";
    
    public static final String DEFAULT_ICON_URL ="../images/defaulticon.ico";
    
	public static final Long SYSTEMID = 1234567890l;

    public static final String RETURN_SUCCESS ="true";
    public static final String RETURN_FAILUTE ="false";
  
    public static final String EMAILADDPATTERN = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

    public static final int TAB_INDEX_COUNT = 3;      
    public static final int TAB_INDEX_ONE = 0;  
    public static final int TAB_INDEX_TWO = 1;  
    public static final int TAB_INDEX_THREE = 2; 
    
    public static final String BITMAP_CACHE_DIR ="bitmap";
    public static final int BITMAP_CACHE_MAX_SIZE =10 * 1024 * 1024;
	public static final long FAVURL_CACHE_MAX_SIZE = 0;
	public static final String FAVURL_CACHE_DIR = "object";
	public static final String URL_PARAMETER_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	public static final String URL_OPENIDOAUTH = "/android/openidOAuth";	
	
	public static final String USERDTO_ID = "id";
	public static final String USERDTO_EMAIL = "email";
	public static final String USERDTO_NICKNAME = "nickname";
	public static final String USERDTO_COUNTRY = "country";
	public static final String USERDTO_LANGUAGE = "language";
	public static final String USERDTO_AVATARURL = "avatarurl";
	public static final String USERDTO_STATUS = "status";
	public static final String OPENID_PROVIDER_TWITTER = "twitter";
	public static final String OPENID_PROVIDER_FACEBOOK= "facebook";
	public static final String OPENID_PROVIDER_GOOGLE = "google";	
	
    public static final String URL_CHECKUSER = "/android/user/check"; 
    public static final String URL_NEW ="/favurl/new";
    public static final String URL_ARCHIVE ="/favurl/archive";
    public static final String URL_FAV ="/favurl/fav";
	public static final String URL_OPENIDSIGNUP = "/android/openidsignup";
	public static final String URL_NORMALSIGNUP = "/user/create";
	public static final String URL_COMMENT = "/comment/pub/url";
	public static final String URL_FAVURL_MY = "/favurl/my";
	public static final String URL_FRIEND_USEID = "/friend/info";
	public static final String URL_MESSAGE = "/message";
	public static final String URL_COMMENT_ADD = "/comment/add";
	public static final String URL_FAVURL_STATUS = "/favurl/status";
	public static final String URL_FAVURL_FAV_UPDATE = "/favurl/fav/update";
	public static final String URL_FAVURL_NEWCOMING = "/favurl/newcoming";
	public static final String URL_USER_SHARE = "/user/share";	
	public static final String URL_USER_FAV = "/user/fav";
	public static final String URL_FRIEND_INVITE = "/friend/invite";
	public static final String URL_FRIEND_DELETE = "/friend/delete";
	public static final String URL_FRIEND_BLOCK = "/friend/block";
	public static final String URL_FRIEND_POPUP = "/friend/popup";
	public static final String URL_GROUP_INFO = "/group/info";
	public static final String URL_GROUP_DATA = "/group/data";
	public static final String URL_FRIEND_USERFRIEND = "/friend/userfriend";
	public static final String URL_FAVURL_SEND = "/android/favurl/send";
	
}
