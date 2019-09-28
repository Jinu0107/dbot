package listener;

import java.net.HttpURLConnection;
import java.net.URL;

public class Riot {
	public void sadas() {
		String clientId = "RGAPI-638396d1-bd00-49ff-981c-1da366b4acad";
		try {
			String apiURL = "https://kr.api.riotgames.com//lol/summoner/v4/summoners/by-name/인간조무사%20지누?api_key=RGAPI-6d74361a-9e44-4118-95f7-f68a8db97023";
			URL url = new URL(apiURL);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			System.out.println(con);
		} catch (Exception e) {

		}
	}
}
