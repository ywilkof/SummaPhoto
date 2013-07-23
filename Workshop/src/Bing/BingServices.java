package Bing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BingServices {
	
	public static void getBla() {
	 String readTwitterFeed = readTwitterFeed();
	    try {
	      JSONArray jsonArray = new JSONArray(readTwitterFeed);
	      for (int i = 0; i < jsonArray.length(); i++) {
	        JSONObject jsonObject = jsonArray.getJSONObject(i);
	        System.out.println(jsonObject.toString());
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	private JSONObject buildJsonObject() {
		JSONObject object = new JSONObject();
		  try {
		    object.put("name", "Jack Hack");
		    object.put("score", new Integer(200));
		    object.put("current", new Double(152.32));
		    object.put("nickname", "Hacker");
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		  return object;
	}
	
	  public static String readTwitterFeed() {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet("http://twitter.com/statuses/user_timeline/vogella.json");
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return builder.toString();
	  }
}
