package dbot;

import listener.LunchListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

public class MainApp {
	public static void main(String[] args) {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = "NjI2NjA5NjQ5NDIwMDc1MDQx.XZNOWw.rHM9tvkT5cRu7y_6KXyK91vFkN8";
		builder.setToken(token);

		try {
			builder.addEventListeners(new LunchListener());
			builder.build();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
