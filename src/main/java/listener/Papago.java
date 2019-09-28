package listener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Papago {
	
	private String langcode;
	
	private String bbb;
	
	public String sensing(String text1) {
		String clientId = "zNhRJ9ecTPh7Ro763Xor";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "KwptPP5oIy";// 애플리케이션 클라이언트 시크릿값";
		try {
			String query = URLEncoder.encode(text1, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/papago/detectLangs";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			// post request
			String postParams = "query=" + query;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			String json = response.toString();

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			langcode = element.getAsJsonObject().get("langCode").getAsString();
			
			String clientId1 = "zNhRJ9ecTPh7Ro763Xor";// 애플리케이션 클라이언트 아이디값";
			String clientSecret1 = "KwptPP5oIy"; // 애플리케이션 클라이언트 시크릿값";
			String apiURL1 = "https://openapi.naver.com/v1/language/translate";
			try {
				String text = URLEncoder.encode(text1, "UTF-8");
				URL url1 = new URL(apiURL1);
				HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
				con1.setRequestMethod("POST");
				con1.setRequestProperty("X-Naver-Client-Id", clientId1);
				con1.setRequestProperty("X-Naver-Client-Secret", clientSecret1);
				// post request
				String word;
				if(langcode.equals("ko")) {
					word = "en";
				}else {
					word = "ko";
				}
				String postParams1 = "source=" + langcode + "&target=" + word + "&text=" + text;

				con1.setDoOutput(true);
				DataOutputStream wr1 = new DataOutputStream(con1.getOutputStream());
				wr1.writeBytes(postParams1);
				wr1.flush();
				wr1.close();
				int responseCode1 = con1.getResponseCode();
				BufferedReader br1;
				if (responseCode1 == 200) { // 정상 호출
					br1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
				} else { // 에러 발생
					br1 = new BufferedReader(new InputStreamReader(con1.getErrorStream()));
				}
				String inputLine1;
				StringBuffer response1 = new StringBuffer();
				while ((inputLine1 = br1.readLine()) != null) {
					response1.append(inputLine1);
				}
				br1.close();
				String json1 = response1.toString();
				JsonParser parser1 = new JsonParser();
				JsonElement element1 = parser1.parse(json1);
				String temp = element1.toString();
				if (!temp.substring(2, 14).equals("errorMessage")) {
					String abc = element1.getAsJsonObject().get("message").getAsJsonObject().get("result").getAsJsonObject()
							.get("translatedText").getAsString();
					bbb = abc;
				} else {
					bbb = "번역할 수 없습니다";
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return bbb;
		
		
	}

}
