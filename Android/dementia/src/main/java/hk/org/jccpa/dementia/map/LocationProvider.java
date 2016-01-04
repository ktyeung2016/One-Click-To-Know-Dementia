package hk.org.jccpa.dementia.map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import hk.org.jccpa.dementia.Global;

/**
 * Created by thomas.wan on 18/6/2015.
 */
public class LocationProvider {
    public Location getLocation(LocationListener listener) {
        LocationManager lms = (LocationManager) Global.onAirAct.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = lms.getBestProvider(criteria, true);
        lms.requestLocationUpdates(bestProvider, 1000, 1, listener);
        Log.i("LocationProvider", "locationServiceInitial().bestProvider=" + bestProvider);
        Location location = lms.getLastKnownLocation(bestProvider);
        if(location == null){
            List<String> providers = lms.getProviders(true);
            Iterator<String> itorTemp = providers.iterator();
            while(itorTemp.hasNext()){
                String temp = itorTemp.next();
                Log.d("LocationProvider", "unsorted available provider="+temp);
            }
            if(providers.contains(LocationManager.GPS_PROVIDER)){
                providers.remove(LocationManager.GPS_PROVIDER);
                providers.add(0,LocationManager.GPS_PROVIDER);
            }
            if(providers.contains(LocationManager.NETWORK_PROVIDER)){
                providers.remove(LocationManager.NETWORK_PROVIDER);
                providers.add(1,LocationManager.NETWORK_PROVIDER);
            }

            itorTemp = providers.iterator();
            while(itorTemp.hasNext()){
                String temp = itorTemp.next();
                Log.d("LocationProvider", "prioritized available provider="+temp);
            }

            List<String> badProviders = providers.subList(0,0);
            badProviders.add(bestProvider);
            Iterator<String> itor = providers.iterator();
            while(itor.hasNext()){
                bestProvider = itor.next();
                if(badProviders.contains(bestProvider)){
                    Log.d("LocationProvider", "badProviders, skipped "+badProviders);
                    continue;
                }
                lms.requestLocationUpdates(bestProvider, 1000, 1, listener);
                location = lms.getLastKnownLocation(bestProvider);
                Log.d("LocationProvider", "try altProvider  "+badProviders);
                if(location == null){
                    Log.d("LocationProvider", "altProvider  location = "+location);
                    continue;
                }else{
                    Log.i("LocationProvider", "getLocation().altProvider="+bestProvider);
                    break;
                }
            }
        }
        Log.d("LocationProvider", "getLocation().location="+location);
        return location;
    }
}
