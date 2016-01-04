package hk.org.jccpa.dementia;

import hk.org.jccpa.dementia.map.MyService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



public class BootCompletedIntentReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			sharepreference s=new sharepreference(context);
			//			
			Intent pushIntent = new Intent(context, MyService.class);
			context.startService(pushIntent);


			//						Calendar cal = Calendar.getInstance();
			//						cal.add(Calendar.MINUTE, s.updaterate());
			//			//			cal.add(Calendar.SECOND, 1);
			//						AlarmIntent.setUpAsAlarm_Service(context, cal);

			Toast.makeText(context, "start:"+s.updaterate(), Toast.LENGTH_LONG).show();
		}
	}
}