package listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Riot {

	public String riot(String suname) {

		BufferedReader in = null;
		String key = "?api_key=RGAPI-762561cb-4cf1-4adb-bf9e-ac5480efe752";
		String a = "";
		try {

			URL obj = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + suname + key);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			String line;
			StringBuffer strB = new StringBuffer();
			while ((line = in.readLine()) != null) {
				strB.append(line);
			}
			String json = strB.toString();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
//			System.out.println(json);

			String id = element.getAsJsonObject().get("id").getAsString();
			String name = element.getAsJsonObject().get("name").getAsString();
			int level = element.getAsJsonObject().get("summonerLevel").getAsInt();

			URL obj1 = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + key);
			HttpURLConnection con1 = (HttpURLConnection) obj1.openConnection();

			con1.setRequestMethod("GET");

			in = new BufferedReader(new InputStreamReader(con1.getInputStream(), "UTF-8"));

			String line1;
			StringBuffer strB1 = new StringBuffer();
			while ((line1 = in.readLine()) != null) {
				strB1.append(line1);
			}
			String json1 = strB1.toString();
			JsonParser parser1 = new JsonParser();
			JsonElement element1 = parser1.parse(json1);

			JsonArray items = element1.getAsJsonArray();
			String tier = "UNRANK";
			String rank = "";
			String wins = "0";
			String losses = "0";
			for (JsonElement item : items) {

				if (item.getAsJsonObject().get("queueType").getAsString().equals("RANKED_SOLO_5x5")) {
					tier = item.getAsJsonObject().get("tier").getAsString();
					rank = item.getAsJsonObject().get("rank").getAsString();
					wins = item.getAsJsonObject().get("wins").getAsString();
					losses = item.getAsJsonObject().get("losses").getAsString();

				}

			}
			a = "닉네임  : " + name + "\n레벨 : " + level + "\n티어 : " + tier + " " + rank + "\n승리 : " + wins + "\n패배 : "
					+ losses;

		} catch (Exception e) {
			a = "존재하지 않는 소환사";
		}
		return a;

	}

	public String db(String username, String suname) {
		String tier = "UNRANK";
		String rank = "";
		String wins = "0";
		String losses = "0";
		String name = "";
		String a = "데이터베이스 입력 완료";
		int level = 0;
		int count = 0;
		boolean abc = true;
		String sql = null;
		PreparedStatement pstmt = null;
		Connection con2 = null;
		try {

			BufferedReader in = null;
			String key = "?api_key=RGAPI-762561cb-4cf1-4adb-bf9e-ac5480efe752";
			try {

				URL obj = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + suname + key);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				con.setRequestMethod("GET");

				in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

				String line;
				StringBuffer strB = new StringBuffer();
				while ((line = in.readLine()) != null) {
					strB.append(line);
				}
				String json = strB.toString();
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(json);
//				System.out.println(json);

				String id = element.getAsJsonObject().get("id").getAsString();
				name = element.getAsJsonObject().get("name").getAsString();
				level = element.getAsJsonObject().get("summonerLevel").getAsInt();

				URL obj1 = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + key);
				HttpURLConnection con1 = (HttpURLConnection) obj1.openConnection();

				con1.setRequestMethod("GET");

				in = new BufferedReader(new InputStreamReader(con1.getInputStream(), "UTF-8"));

				String line1;
				StringBuffer strB1 = new StringBuffer();
				while ((line1 = in.readLine()) != null) {
					strB1.append(line1);
				}
				String json1 = strB1.toString();
				JsonParser parser1 = new JsonParser();
				JsonElement element1 = parser1.parse(json1);

				JsonArray items = element1.getAsJsonArray();

				for (JsonElement item : items) {

					if (item.getAsJsonObject().get("queueType").getAsString().equals("RANKED_SOLO_5x5")) {
						tier = item.getAsJsonObject().get("tier").getAsString() + " "
								+ item.getAsJsonObject().get("rank").getAsString();
						;
						wins = item.getAsJsonObject().get("wins").getAsString();
						losses = item.getAsJsonObject().get("losses").getAsString();
						if (item.getAsJsonObject().get("tier").getAsString().equals("IRON")) {
							count += 1;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("BRONZE")) {
							count += 6;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("SILVER")) {
							count += 11;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("GOLD")) {
							count += 16;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("PLATINUM")) {
							count += 21;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("DIAMOND")) {
							count += 26;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("MASTER")) {
							count += 31;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("GRANDMASTER")) {
							count += 36;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("CHALLENGER")) {
							count += 41;
						}
						if (item.getAsJsonObject().get("rank").getAsString().equals("I")) {
							count += 4;
						} else if (item.getAsJsonObject().get("rank").getAsString().equals("II")) {
							count += 3;
						} else if (item.getAsJsonObject().get("rank").getAsString().equals("III")) {
							count += 2;
						} else if (item.getAsJsonObject().get("rank").getAsString().equals("IV")) {
							count += 1;
						}

					}

				}

			} catch (Exception e) {
				a = "전적검색중 오류 발생";
				abc = false;
				System.out.println("전적검색중 오류발생");
				e.printStackTrace();
			}

			if (abc) {
				con2 = getConnection();
				sql = "INSERT INTO lol (name , suname , level , tier, count) VALUES " + "(? , ? , ? , ? , ?)";
				pstmt = con2.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, name);
				pstmt.setInt(3, level);
				pstmt.setString(4, tier);
				pstmt.setInt(5, count);

				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			a = "데이터베이스 입력중 오류 발생";
			System.out.println("에러발생");
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con2);
		}
		return a;
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String conStr = "jdbc:mysql://gondr.asuscomm.com/yy_10214?useUnicode=true" + "&characterEncoding=utf8"
					+ "&useSSL=false" + "&serverTimezone=Asia/Seoul";
			String user = "yy_10214";
			String pw = "1234";

			con = DriverManager.getConnection(conStr, user, pw);
		} catch (Exception e) {

		}
		return con;

	}

	public String show(boolean a) {
		Papago pa = new Papago();
		String re = "순위\t\t이름\t\t\t소환사 이름\t\t\t\t 레벨\t\t\t\t티어\n==================================================";
		int cnt = 0;

		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		if (a) {
			sql = "SELECT * FROM lol ORDER BY count DESC";
		} else {
			sql = "SELECT * FROM lol ORDER BY level DESC";
		}
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cnt++;
				String name = rs.getString("name");
				String suname = rs.getString("suname");
				int level = rs.getInt("level");
				String tier = rs.getString("tier");
				re += "\n" + cnt;
				for (int i = 0; i < 10; i++) {
					re += "-";
				}
				re += name;
				for (int i = 0; i < 20 - name.getBytes().length; i++) {
					re += "-";
				}
				re += suname;
				if (pa.se(suname).equals("ko")) {
					for (int i = 0; i < 20 - suname.length() * 2; i++) {
						re += "-";
					}
				} else {
					for (int i = 0; i < 20 - suname.length(); i++) {
						re += "-";
					}
				}
				re += level;
				if (level / 100 > 1) {
					for (int i = 0; i < 10; i++) {
						re += "-";
					}
				} else if (level / 10 > 1) {
					for (int i = 0; i < 11; i++) {
						re += "-";
					}
				} else {
					for (int i = 0; i < 12; i++) {
						re += "-";
					}
				}
				re += tier;
				re += "\n==================================================";

			}
		} catch (Exception e) {
			System.out.println("오류");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		return re;

	}

	public String delete(String name) {
		Connection con = getConnection();
		String ret = "성공적으로 삭제 완료";
		PreparedStatement pstmt = null;

		String sql = "DELETE FROM lol WHERE name =" + "(?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);

			pstmt.executeUpdate();
		} catch (Exception e) {
			ret = "데이터삭제중 오류발생";
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
		return ret;
	}

	public String update(String username, String suname) {
		String ret = "성공적으로 수정 완료";
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		String name = "";
		String tier = "UNRANK";
		int level = 0;
		int count = 0;
		boolean error = true;

		String sql = String.format(
				"UPDATE lol SET name = %s , suname = %s , level = %s" + " , tier = %s , count = %s WHERE name = %s",
				"(?)", "(?)", "(?)", "(?)", "(?)", "(?)");
		try {
			BufferedReader in = null;
			String key = "?api_key=RGAPI-762561cb-4cf1-4adb-bf9e-ac5480efe752";
			try {

				URL obj = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + suname + key);
				HttpURLConnection con1 = (HttpURLConnection) obj.openConnection();

				con1.setRequestMethod("GET");

				in = new BufferedReader(new InputStreamReader(con1.getInputStream(), "UTF-8"));

				String line;
				StringBuffer strB = new StringBuffer();
				while ((line = in.readLine()) != null) {
					strB.append(line);
				}
				String json = strB.toString();
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(json);

				String id = element.getAsJsonObject().get("id").getAsString();
				name = element.getAsJsonObject().get("name").getAsString();
				level = element.getAsJsonObject().get("summonerLevel").getAsInt();

				URL obj1 = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + key);
				HttpURLConnection con11 = (HttpURLConnection) obj1.openConnection();

				con11.setRequestMethod("GET");

				in = new BufferedReader(new InputStreamReader(con11.getInputStream(), "UTF-8"));

				String line1;
				StringBuffer strB1 = new StringBuffer();
				while ((line1 = in.readLine()) != null) {
					strB1.append(line1);
				}
				String json1 = strB1.toString();
				JsonParser parser1 = new JsonParser();
				JsonElement element1 = parser1.parse(json1);

				JsonArray items = element1.getAsJsonArray();

				for (JsonElement item : items) {

					if (item.getAsJsonObject().get("queueType").getAsString().equals("RANKED_SOLO_5x5")) {
						tier = item.getAsJsonObject().get("tier").getAsString() + " "
								+ item.getAsJsonObject().get("rank").getAsString();
						;
						if (item.getAsJsonObject().get("tier").getAsString().equals("IRON")) {
							count += 1;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("BRONZE")) {
							count += 6;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("SILVER")) {
							count += 11;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("GOLD")) {
							count += 16;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("PLATINUM")) {
							count += 21;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("DIAMOND")) {
							count += 26;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("MASTER")) {
							count += 31;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("GRANDMASTER")) {
							count += 36;
						} else if (item.getAsJsonObject().get("tier").getAsString().equals("CHALLENGER")) {
							count += 41;
						}
						if (item.getAsJsonObject().get("rank").getAsString().equals("I")) {
							count += 4;
						} else if (item.getAsJsonObject().get("rank").getAsString().equals("II")) {
							count += 3;
						} else if (item.getAsJsonObject().get("rank").getAsString().equals("III")) {
							count += 2;
						} else if (item.getAsJsonObject().get("rank").getAsString().equals("IV")) {
							count += 1;
						}

					}

				}

			} catch (Exception e) {
				ret = "전적검색중 오류 발생";
				error = false;
				System.out.println("전적검색중 오류발생");
				e.printStackTrace();
			}
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, suname);
			pstmt.setInt(3, level);
			pstmt.setString(4, tier);
			pstmt.setInt(5, count);
			pstmt.setString(6, username);

			pstmt.executeUpdate();
			
		} catch (Exception e) {
			ret = "데이터베이스 수정중 오류 발생";
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(con);
		}
		return ret;
	}

	public static void close(ResultSet value) {
		if (value != null) {
			try {
				value.close();
			} catch (Exception e) {
			}

		}
	}

	public static void close(PreparedStatement value) {
		if (value != null) {
			try {
				value.close();
			} catch (Exception e) {
			}

		}
	}

	public static void close(Connection value) {
		if (value != null) {
			try {
				value.close();
			} catch (Exception e) {
			}
		}
	}

}
