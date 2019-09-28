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
				sayMsg("현진이는 이거 할 수 있습니다. !yy echo , !yy 이석 , !yy 안진우 , !yy 박현진, !번역 (번역할 내용)", event);
				break;
			case "이석":
				sayMsg("육수용 멸치입니다", event);
				break;
			case "안진우":
				sayMsg("존잘남 입니다", event);
				break;
			case "박현진":
				sayMsg("호구 병신 입니다", event);
				break;
			}

		} else if (msg.startsWith("!번역")) {
			Papago pa = new Papago();
			int idx2 = msg.indexOf(" ");
			if (idx2 < 0) {
				sayMsg("번역할 내용을 입력하세요", event);
				return;
			}
			String cmd = msg.substring(idx2 + 1);
			sayMsg(pa.sensing(cmd), event);

		}
	}

	private void sayMsg(String msg, MessageReceivedEvent event) {
		event.getChannel().sendMessage(msg).queue();
	}

}
