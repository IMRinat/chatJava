package chat;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс Чат
 */
public class ChatModul {
  private FileModule fileModule; // объект файлового модуля для работы с историей сообщений
  private String userName; // имя пользователя который запустил чат
  private Scanner sc; // объект сканера для чтения с консоли
  private int countLinesHistory; // счетчик количества сообщений в истории сообщений
  Thread threadReadHistory; // поток для бексонечно перечитывания истории сообщений из файла

  /**
   * Конструктор класса
   */
  public ChatModul() throws IOException, InterruptedException {
    fileModule = new FileModule(); // создаем объект для работы с файлом истории сообщений
    
    OutputStreamWriter osw = new OutputStreamWriter(System.out); //объект для определения кодировки консоли
    var codePage =Charset.forName(osw.getEncoding()); 
    sc = new Scanner(System.in, codePage); // создаем объект для чтения из консоли
    countLinesHistory = 0; // начальное значение количества сообщений в истории
  }

  /**
   * Функция для чтения истории сообщений и вывода новых появившихся там строк
   */
  private void printNewHistoryLines() {
    int oldCountLinesHistory = countLinesHistory; // сохраняем предыдущее значение количества сообщений
    countLinesHistory = fileModule.readHistory(); // читаем историю сообщений из файла
    if (countLinesHistory > oldCountLinesHistory) { // если количество больше старого количества
      for (var i = oldCountLinesHistory; i < countLinesHistory; i++) { // цикл по новым сообщениям
        String lineHistory = fileModule.getLineHistory(i); // берем строку с сообщением из списка
        if (oldCountLinesHistory == 0) { // если первый раз то выводим все сообщения
          System.out.println(lineHistory); // выводим в консоль сообщение
        } else {
          // если новое сообщение совпадает с именем пользователя то не выводим потому что оно уже написано в консоли пользователем
          if (lineHistory.indexOf(userName) != 20) { // 20 это местро в строке с которого начинается имя пользователя. например 17.07.2022 15:47:38 Ivanov Privet ot ivanova
            System.out.println(lineHistory);
          }
        }
      }

    }
  }

  /**
   *   Основная функция чата
   */
  public void start() throws IOException, InterruptedException {
    System.out.println("Добро пожаловать в консольный чат!"); // приветствие
    System.out.print("Введите свое имя: "); // запрос имени пользователя
    userName = sc.nextLine();  // ввод имени пользователя
    System.out.println("Здравствуйтe, " + userName + "! Для выхода из чата введите слово Exit");

    // отдельный поток который постоянно читает историю сообщений для вывода новых сообщений в консоль
    threadReadHistory = new Thread() { // создаем поток
      @Override
      public void run() {
        while (true) {
          try {
            Thread.sleep(1000); // пауза 1 секунда
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          printNewHistoryLines(); // вызов функции проверки истории сообщений на новые сообщения
        }
      }
    };

    threadReadHistory.start();// запуск потока

    // бесконечный цикл 
    while (true) {
      if (sc.hasNextLine()) { // ждем новое сообщение юзера
        var text = sc.nextLine(); // получаем с консоли новое сообщение пользователя
        if (text.toLowerCase().equals("exit")) { // если это слово exit то выходим из цикла и заканчиваем программу 
          System.out.println("До свидания, " + userName); // прощание с пользователем
          break;  // выход из цикла
        }

        if (text.length() > 0) {  //если не пустая строка 
          var format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); // формат даты времени
          var datetime = format.format(new Date()); // дата и время сообщения

          var message = new ChatMessage(userName, text, datetime); // создаем объект сообщения
          fileModule.writeMessage(message); // записываем сообщение в файл
        }
      }
    }

  }

}
