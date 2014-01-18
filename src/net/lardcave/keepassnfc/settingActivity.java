package net.lardcave.keepassnfc;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.content.pm.*;
import android.content.*;
import android.preference.*;
import android.widget.*;


/**
 * Created by sow on 17.12.13.
 */
public class settingActivity extends PreferenceActivity {

PreferenceHelper help = new PreferenceHelper();
Boolean show;
String number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

		
		//prefs= PreferenceManager.getDefaultSharedPreferences(this);
		//show= prefs.getBoolean("hideapp",false);
		number = help.getnumber(getBaseContext());
		
		final PackageManager p = getPackageManager();
        final ComponentName write = new ComponentName(getBaseContext(), WriteActivity.class);
        final ComponentName receiver = new ComponentName(getBaseContext(), Receiver.class);
		
        Preference hide = (Preference) findPreference("hideapp");
        hide.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
				show= help.getshow(getBaseContext());
				if(show)
				{
					try{
						
						p.setComponentEnabledSetting(write, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}else if(!show)
				{
					
					p.setComponentEnabledSetting(write, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);	
				}
                return true;
            }
        });
		final Preference dialer = (Preference)findPreference("dialer");
		dialer.setSummary("Open the app with *#"+number+"# in the dialer");
		dialer.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
			public boolean onPreferenceChange(Preference preference, Object object){
				//number = prefs.getString("dialer","1234");
			   
				dialer.setSummary("Open the app with *#"+object+"# in the dialer");
				//Toast.makeText(getBaseContext(),"Restart the phone please",Toast.LENGTH_LONG).show();
				
				return true;
			}
		});
		


    }
}
