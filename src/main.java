import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;





import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constant.Region;
import dto.Summoner.Summoner;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;


public class main {
	public static void main(String[] args) throws IOException, JSONException {
		String summonerName = JOptionPane.showInputDialog (null, "Enter the summoner name: ",
                "Summoner Name", JOptionPane.QUESTION_MESSAGE).toLowerCase();
		
		String apiKey = "b40cf360-ac52-479d-b9eb-06dc99bdea83";	

		summonerName.replace(" ", "");
		JSONObject summID = readJsonFromAPI("https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/" + summonerName + "?api_key=" + apiKey);	

		JSONObject summIDChildObject = (JSONObject)summID.get(summonerName);
		String id = summIDChildObject.getString("id");
		System.out.println("Summoner ID: " + id);
		
		JSONObject leagueCall = readJsonFromAPI("https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/" + id + "/entry?api_key=" + apiKey);
		JSONArray dataArr = leagueCall.getJSONArray(id);
		
		JSONObject firstObj = dataArr.getJSONObject(0);
		String tier = firstObj.getString("tier");
		
		JSONArray entr = firstObj.getJSONArray("entries");
		String div = entr.getJSONObject(0).getString("division");
		
		System.out.println(summonerName + " is " + tier + " " + div);

	}
	
	public static JSONObject readJsonFromAPI(String call) throws IOException, JSONException {
		InputStream is = new URL(call).openStream();
		
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(read);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	private static String readAll(Reader read) throws IOException {
		StringBuilder sb = new StringBuilder();
		int i;
		while ((i = read.read()) != -1) {
			sb.append((char) i);
		}
		return sb.toString();
	}
}
