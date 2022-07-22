package chat;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileConnect {
  private String filePathHistory = "chathistory.txt"; // путь к файлу с историей
  private static FileConnect object;
  private File file;

  private FileConnect() {
    file = new File(Paths.get("").toAbsolutePath() + "\\" + filePathHistory);
  }

  public static FileConnect getObject() {
    if (object == null) {
      object = new FileConnect();
    }
    return object;
  }

  public void appendLine(String line) {
    Boolean succesWrite = false; // признак успешности записи
    do {
      try {
        FileWriter fileWriter = new FileWriter(file, true); // создаем объект для дозаписи
        fileWriter.write(line); // дозаписываем в файл сообщение
        fileWriter.close(); // закрываем файл
        succesWrite = true; // признак успешности записи
      } catch (Exception e) {
        succesWrite = false; // если возникла ошибка записи
      }
    } while (!succesWrite); // повторяем пока не будет записи без ошибок
  }

  public ArrayList<String> readLines() {
    ArrayList<String> lines = new ArrayList<String>();

    Boolean succesRead = false; // признак успешности чтения
    do {
      try {
        lines.clear();
        var sc = new Scanner(file); // создаем объект сканер
        while (sc.hasNextLine()) { // проверяем есть ли строка
          var strLine = sc.nextLine(); // читаем строку
          lines.add(strLine); // добавляем строку в список
        }
        sc.close(); // закрываем список
        succesRead = true; // признак успешности чтения
      } catch (Exception e) {
        succesRead = false; // если возникла ошибка чтения
      }
    } while (!succesRead); // повторяем пока не будет чтения без ошибок
    return lines;
  }

}
