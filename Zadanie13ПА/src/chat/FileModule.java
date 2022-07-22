package chat;

import java.io.*;
import java.util.*;

public class FileModule {
  private FileConnect fileConnect; // объект файл
  private ArrayList<String> history; // список сообщений
  
  public FileModule() {
    fileConnect = FileConnect.getObject();
  }

  public String getLineHistory(int n) {
    return history.get(n);
  }

  public void writeMessage(ChatMessage chatMessage) throws IOException {
    fileConnect.appendLine(chatMessage.toString());
  }

  public int readHistory() {
    history = fileConnect.readLines();
    return history.size(); // определяем количество сообщений в истории
  }

}
