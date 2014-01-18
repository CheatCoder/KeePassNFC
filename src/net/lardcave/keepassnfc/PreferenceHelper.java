package net.lardcave.keepassnfc;
import android.content.*;
import android.preference.*;

public class PreferenceHelper
{
	
	private SharedPreferences prefs;

	private Boolean show;
	private String number;
	
	public Boolean getshow(Context context){
		prefs= PreferenceManager.getDefaultSharedPreferences(context);
		show= prefs.getBoolean("hideapp",false);
		return show;
	}
	
	
	public String getnumber(Context context){
		prefs= PreferenceManager.getDefaultSharedPreferences(context);
		number = prefs.getString("dialer","1234");
		return number;
	}
	
}
