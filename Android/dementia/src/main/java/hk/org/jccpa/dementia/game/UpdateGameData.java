package hk.org.jccpa.dementia.game;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hk.org.jccpa.dementia.Global;

/**
* Created by thomas.wan on 3/7/2015.
*/
public class UpdateGameData extends AsyncTask<String, Integer, Integer> {
    ProgressDialog pd;
    @Override
    protected Integer doInBackground(String... param) {
        try {
            if(param.length > 0) {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(Global.apilink + "submit_marks.php?token=" + Global.onAirAct.token + "&game_level=" + param[0] + "&game_type=" + Global.gamenum + "&user_id=" + Global.s.userid() + "&marks=" + Global.totalscore
                        + "&correct_no=" + Global.right5);
                Log.d("UpdateGameData", "URI: " + get.getURI().toString());
                HttpResponse response = client.execute(get);
                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity);
                JSONObject j = new JSONObject(result);

                Log.d("UpdateGameData", "result: " + result);
//				`game_type`, `game_level`, `user_id`, `marks`, `correct_no`, `create_user`, `create_date`, `update_user`
                //				HttpClient client2 = new DefaultHttpClient();
                //				HttpGet get2 = new HttpGet(Global.apilink+"news_list.php?token="+Global.onAirAct.token);
                //				HttpResponse response2 = client2.execute(get2);
                //				HttpEntity resEntity2 = response2.getEntity();
                //				String result2 = EntityUtils.toString(resEntity2);
                //				JSONObject j2=new JSONObject(result2);

                //				Log.d("result", "result"+result+"\nresult2"+result2);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        //			pd.dismiss();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //			pd = ProgressDialog.show(MainActivity.this, "Little Tree", "Loading...");
    }

}
