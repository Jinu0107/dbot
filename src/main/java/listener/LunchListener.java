package listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LunchListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String msg = event.getMessage().getContentRaw();
		Riot ri = new Riot();
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

		} else if(msg.startsWith("!전적")) {
			
			int idx2 = msg.indexOf(" ");
			if (idx2 < 0) {
				sayMsg("닉네임을 입력하세요", event);
				return;
			}
			String cmd = msg.substring(idx2 + 1);
			cmd = cmd.replace(" ", "");
			
			sayMsg(ri.riot(cmd), event);
		}else if(msg.startsWith("!등록")) {
			int idx = msg.indexOf(" "); // 첫번째로 나오는 공백을 찾아서
			if (idx < 0) {
				sayMsg("전적등록 시스템을 사용하시려면 !등록 (당신의 이름) (소환사 닉네임)  을 입력하세요", event);
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
			if(param =="") {
				sayMsg("전적등록 시스템을 사용하시려면 !등록 (당신의 이름) (소환사 닉네임)  을 입력하세요", event);
			}else {
				param = param.replace(" ", "");
				sayMsg(ri.db(cmd, param), event);
			}
		}else if(msg.startsWith("!랭킹")) {
			int idx = msg.indexOf(" "); // 첫번째로 나오는 공백을 찾아서
			if (idx < 0) {
				sayMsg("랭킹 시스템을 사용하시려면 !랭킹 (티어,레벨) 을 입력하세요", event);
				return;
			}
			String cmd = msg.substring(idx + 1);
			switch(cmd) {
			case "티어":
				sayMsg(ri.show(true), event);
				break;
			case "레벨":
				sayMsg(ri.show(false), event);
				break;
			}
		}else if(msg.startsWith("!삭제")) {
			int idx = msg.indexOf(" "); // 첫번째로 나오는 공백을 찾아서
			if (idx < 0) {
				sayMsg("삭제 시스템을 사용하시려면 !삭제 (당신의 이름) 을 입력하세요", event);
				return;
			}
			String cmd = msg.substring(idx + 1);
			sayMsg(ri.delete(cmd), event);
		}else if(msg.startsWith("!수정")) {
			int idx = msg.indexOf(" "); // 첫번째로 나오는 공백을 찾아서
			if (idx < 0) {
				sayMsg("수정 시스템을 사용하시려면 !수정 (당신의 이름) (수정될 소환사 닉네임)  을 입력하세요", event);
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
			if(param =="") {
				sayMsg("수정 시스템을 사용하시려면 !수정 (당신의 이름) (수정될 소환사 닉네임)  을 입력하세요", event);
			}else {
				param = param.replace(" ", "");
				sayMsg(ri.update(cmd, param), event);
			}
		}
	}

	private void sayMsg(String msg, MessageReceivedEvent event) {
		event.getChannel().sendMessage(msg).queue();
	}

}
