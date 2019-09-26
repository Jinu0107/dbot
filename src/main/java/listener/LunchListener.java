package listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LunchListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String msg = event.getMessage().getContentRaw();

		if (msg.startsWith("!yy")) {
			int idx = msg.indexOf(" "); // 첫번째로 나오는 공백을 찾아서
			if (idx < 0) {
				sayMsg("안녕하세요 박현진봇입니다. 도움을 원하시면 !yy help를 입력하세요", event);
				return;
			}
			String cmd = msg.substring(idx + 1);
			idx = cmd.indexOf(" ");
			String param = "";
			if (idx > 0) {
				// 파라미터가 들어왔다면 명령어와 파라메터를 구분한다.
				param = cmd.substring(idx + 1);
				cmd = cmd.substring(0, idx);
			}else {
				if(msg.equals("!yy 이석")) {
					sayMsg("육수용 멸치 입니다.", event);
				}else if(msg.equals("!yy 안진우")) {
					sayMsg("존잘남 입니다." ,event);
				}else if(msg.equals("!yy 박현진")) {
					sayMsg("모지리 입니다",event);
				}
			}
			switch (cmd) {
			case "echo":
				if (param == "") {
					sayMsg("echo 명령어는 메아리 할 말을 입력하셔야 합니다", event);
				} else {
					sayMsg(param, event);
				}
				break;
			case "help":
				sayMsg("현진이는 이거 할 수 있습니다. !yy echo ,", event);
				break;
			}

		}
	}

	private void sayMsg(String msg, MessageReceivedEvent e) {
		e.getChannel().sendMessage(msg).queue();
	}

}
