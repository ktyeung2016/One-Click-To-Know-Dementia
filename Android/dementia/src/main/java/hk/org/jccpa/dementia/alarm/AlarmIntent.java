package hk.org.jccpa.dementia.alarm;

import java.util.Calendar;

import hk.org.jccpa.dementia.map.MyService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmIntent {

	// Service
	public static PendingIntent getAlarmIntent_Service(Context context) {
		Log.e("AlarmIntent", "getAlarmIntent_Service");

		Intent i = new Intent(context, MyService.class);
		PendingIntent pending = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		return pending;
	}

	public static void setUpAsAlarm_Service(Context context,Calendar cal ) {
		Log.d("time:", "setUpAsAlarm_Service");

		AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//		Calendar cal = Calendar.getInstance();
		// start 30 seconds after boot completed
//		cal.add(Calendar.MINUTE, 5);
		// fetch every 5 minutes
		// InexactRepeating allows Android to optimize the energy consumption
//		service.setRepeating(AlarmManager.RTC_WAKEUP, 1000, cal.getTimeInMillis(), getAlarmIntent_Service(context));
		service.setRepeating(AlarmManager.RTC_WAKEUP, 1000, cal.getTimeInMillis(), getAlarmIntent_Service(context));
	}

	public static void cleanAsAlarm_Service(Context context) {
		AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmMgr.cancel(getAlarmIntent_Service(context));
	}

}