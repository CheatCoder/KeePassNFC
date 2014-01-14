package net.lardcave.keepassnfc;

import android.content.*;
import android.widget.*;
import android.content.pm.*;
import android.preference.*;

public class Receiver extends BroadcastReceiver
{
	
	SharedPreferences prefs;

	Boolean show;
	
	@Override
	public void onReceive(Context context,final Intent intent) {

		if (intent.getAction().equals(android.content.Intent.ACTION_NEW_OUTGOING_CALL)) {
			String phoneNumber = intent.getStringExtra(android.content.Intent.EXTRA_PHONE_NUMBER);

			prefs= PreferenceManager.getDefaultSharedPreferences(context);
			show= prefs.getBoolean("hideapp",false);
			
			PackageManager p = context.getPackageManager();
			Toast.makeText(context, phoneNumber,Toast.LENGTH_SHORT).show();
			try{
			if(phoneNumber.equals("#1234#")) { 
			
				ComponentName write = new ComponentName(context, WriteActivity.class);
				//ComponentName setting = new ComponentName(context, settingActivity.class);
				//p.setComponentEnabledSetting(setting, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
				
				
				if(show)
				p.setComponentEnabledSetting(write, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
				
			
                Intent intent1 = new Intent(context , WriteActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
				setResultData(null);
				
				//PackageManager p = context.getPackageManager();
				if(show)
				p.setComponentEnabledSetting(write, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				
			}	}catch(Exception e){
				e.printStackTrace();
				e.getMessage();
			}
				
			}
	}
	
}
