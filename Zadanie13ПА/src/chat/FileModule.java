package chat;

import java.io.*;
import java.util.*;

/**
 *  Класс для работы с файлом в котором будет храниться история сообщений 
 */
public class FileModule {
  private String filePathHistory = "C:\\temp\\chathistory.txt"; // путь к файлу с историе 
  private File file; // объект файл
  private ArrayList<String> history; // список сообщений 

  /**
   * конструктор класса
   */
  public FileModule() {
    file = new File(filePathHistory); // создаем объект файл
    history = new ArrayList<String>(); // создаем объект список сообщений
  }

  /**
   *  Функция для получения строки из истории сообщений по номеру
   * @param n - номер строки в истории
   * @return - строка с сообщением
   */
  public String getLineHistory(int n) {
    return history.get(n);
  }

  /**
   * Функция записи сообщения в файл истории сообщений
   * @param chatMessage  - объект сообщения
   * @throws IOException - ошибка ввода/вывода
   */
  public void writeMessage(ChatMessage chatMessage) throws IOException {
    Boolean succesWrite = false; // признак успешности записи 
    do {
      try {
        FileWriter fileWriter = new FileWriter(file, true); // создаем объект для дозаписи
        fileWriter.write(chatMessage.toString()); // дозаписываем в файл сообщение 
        fileWriter.close(); // закрываем файл
        succesWrite = true; // признак успешности записи 
      } catch (Exception e) {
        succesWrite = false; // если возникла ошибка записи
      }
    } while (!succesWrite); // повторяем пока не будет записи без ошибок
  }

  /**
   * Функция чтения истории сообщений из файла в объект список ArrayList
   * @return - функция возвращает количество прочитанных строк
   */
  public int readHistory() {
    Boolean succesRead = false; // признак успешности чтения
    history.clear(); // очищаем список
    do {
      try {
        var sc = new Scanner(file); // создаем объект сканер
        while (sc.hasNextLine()) { // проверяем есть ли строка
          var strLine = sc.nextLine(); // читаем строку
          history.add(strLine); // добавляем строку в список
        }
        sc.close(); // закрываем список 
        succesRead = true; // признак успешности чтения
      } catch (Exception e) {
        succesRead = false; // если возникла ошибка чтения
      }
    } while (!succesRead); // повторяем пока не будет чтения без ошибок

    return history.size(); // определяем количество сообщений в истории
  }

}
