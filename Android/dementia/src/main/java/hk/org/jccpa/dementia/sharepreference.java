package hk.org.jccpa.dementia;

import android.content.Context;
import android.content.SharedPreferences;

public class sharepreference {
	SharedPreferences settings;
	final String s="SETTING";
	final String g="GAME5";
	public sharepreference(Context c){
		settings = c.getSharedPreferences (s, 0);
	}
	
	
	//lastversion
	public String last1version(){
		return settings.getString("last1version", "-1");

	}
	public void setlast1version(String ispfirstuse){

		SharedPreferences.Editor PE = settings.edit();
		PE.putString("last1version", ispfirstuse);
		PE.commit();
	}
	public String last2version(){
		return settings.getString("last2version", "-1");

	}
	public void setlast2version(String ispfirstuse){

		SharedPreferences.Editor PE = settings.edit();
		PE.putString("last2version", ispfirstuse);
		PE.commit();
	}
	public String last3version(){
		return settings.getString("last3version", "-1");

	}
	public void setlast3version(String ispfirstuse){

		SharedPreferences.Editor PE = settings.edit();
		PE.putString("last3version", ispfirstuse);
		PE.commit();
	}
	//lastversion
	//music
		public boolean ispfirstuse(){
			return settings.getBoolean("firstuse", true);

		}
		public void setispfirstuse(boolean ispfirstuse){

			SharedPreferences.Editor PE = settings.edit();
			PE.putBoolean("firstuse", ispfirstuse);
			PE.commit();
		}
		
	//music
	public boolean isplaymusic(){
		return settings.getBoolean("music", true);

	}
	public void setplaymusic(boolean isplaymusic){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("music", isplaymusic);
		PE.commit();
	}
	//music
	//map
	public boolean isfirstmap(){
		return settings.getBoolean("map", true);

	}
	
	public void setmap(boolean isfirst){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("map", isfirst);
		PE.commit();
	}
	//map
	//gps update
	public boolean isupdate(){
		return settings.getBoolean("update", true);

	}
	
	public void setupdate(boolean isfirst){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("update", isfirst);
		PE.commit();
	}
	public int updaterate(){
		return settings.getInt("rate", 60);

	}
	
	public void setupdaterate(int isfirst){

		SharedPreferences.Editor PE = settings.edit();
		PE.putInt("rate", isfirst);
		PE.commit();
	}
	//gps update
	//gamedata
	public String userid(){
		return settings.getString("useid", "no_user");

	}
	public void setuserid(String id){

		SharedPreferences.Editor PE = settings.edit();
		PE.putString("useid", id);
		PE.commit();
	}
    public String userlogin(){
        return settings.getString("userlogin", "no_user");

    }
    public void setuserlogin(String id){

        SharedPreferences.Editor PE = settings.edit();
        PE.putString("userlogin", id);
        PE.commit();
    }
    public String userKey(){
        return settings.getString("userkey", "");

    }
    public void setuserKey(String key){

        SharedPreferences.Editor PE = settings.edit();
        PE.putString("userkey", key);
        PE.commit();
    }
	//gamedata


	//gamedata
	public boolean gamedatako(){
		return settings.getBoolean("done", false);

	}
	public void setgamedatako(boolean is_game){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("done", is_game);
		PE.commit();
	}
	//gamedata

	//Setting 
	public void setlang(boolean is_zh){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("is_zh", is_zh);
		PE.commit();
	}


	public boolean whichlang(){
		return settings.getBoolean("is_zh", true);

	}
	public void setmode(boolean is_game){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("is_game", is_game);
		PE.commit();
	}
	public boolean whichmode(){
		return settings.getBoolean("is_game", false);

	}
	public void setlangsize(boolean is_big){

		SharedPreferences.Editor PE = settings.edit();
		PE.putBoolean("is_big", is_big);
		PE.commit();
	}
	public boolean whichsize(){
		return settings.getBoolean("is_big", true);

	}
	//Setting 


	//game
	//game 5
	public void game5status(int status){
		//0 lv1...
		SharedPreferences.Editor PE = settings.edit();
		PE.putInt("status", status);
		PE.commit();
	}


	public int getgame5status(){
		return settings.getInt("status", -1);
	}
	//game 5

	//game2
	public void game2status(int status){
		//0 lv1...
		SharedPreferences.Editor PE = settings.edit();
		PE.putInt("g2status", status);
		PE.commit();
	}


	public int getgame2status(){
		return settings.getInt("g2status", -1);
	}
	//game2


	//game4

	public void game4status(int status){
		//0 lv1...
		SharedPreferences.Editor PE = settings.edit();
		PE.putInt("g4status", status);
		PE.commit();
	}


	public int getgame4status(){
		return settings.getInt("g4status", -1);
	}


	//game4



	//game1

	public void game1status(int status){
		//0 lv1...
		SharedPreferences.Editor PE = settings.edit();
		PE.putInt("g1status", status);
		PE.commit();
	}


	public int getgame1status(){
		return settings.getInt("g1status", -1);
	}


	//game1



	//game1

	public void game3status(int status){
		//0 lv1...
		SharedPreferences.Editor PE = settings.edit();
		PE.putInt("g3status", status);
		PE.commit();
	}


	public int getgame3status(){
		return settings.getInt("g3status", -1);
	}


	//game1

	//game 


}
