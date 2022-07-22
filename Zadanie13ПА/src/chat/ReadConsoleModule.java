package chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ReadConsoleModule {
  private FileModule fileModule; // объект файлового модуля для работы с историей сообщений
  private String userName; // имя пользователя который запустил чат
  private Scanner sc; // объект сканера для чтения с консоли
  Thread threadReadConsole; // поток для бексонечно перечитывания истории сообщений из файла
  ChatModule chatModule;

  public String getUserName() {
    return userName;
  }

  ReadConsoleModule(ChatModule chatModule) {
    fileModule = new FileModule(); // создаем объект для работы с файлом истории сообщений
    OutputStreamWriter osw = new OutputStreamWriter(System.out); // объект для определения кодировки консоли
    var codePage = Charset.forName(osw.getEncoding());
    sc = new Scanner(System.in, codePage); // создаем объект для чтения из консоли
    this.chatModule = chatModule;
  }

  private void sendMessage(String msg) {
    var format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); // формат даты времени
    var datetime = format.format(new Date()); // дата и время сообщения

    var message = new ChatMessage(userName, msg, datetime); // создаем объект сообщения

    try {
      fileModule.writeMessage(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void init() {
    System.out.println("Добро пожаловать в консольный чат!"); // приветствие
    System.out.print("Введите свое имя: "); // запрос имени пользователя
    userName = sc.nextLine(); // ввод имени пользователя
    System.out.println("Здравствуйтe, " + userName + "! Для выхода из чата введите слово Exit");
    sendMessage("Пользователь " + userName + " вошел в чат");
  }

  public void start() {
    // отдельный поток который постоянно читает историю сообщений для вывода новых
    // сообщений в консоль
    threadReadConsole = new Thread() { // создаем поток
      @Override
      public void run() {
        while (!ChatModule.endChat) {
          if (sc.hasNextLine()) { // ждем новое сообщение юзера
            var text = sc.nextLine(); // получаем с консоли новое сообщение пользователя
            if (text.length() > 0) { // если не пустая строка
              sendMessage(text);
              if (text.toLowerCase().equals("exit")) { // если это слово exit то выходим из цикла и заканчиваем программу
                if (text.toLowerCase().equals("exit")) {
                  sendMessage("Пользователь " + userName + " вышел из чата");
                }
                System.out.println("До свидания, " + userName); // прощание с пользователем
                ChatModule.endChat = true;
                break; // выход из цикла
              }

            }
          }
        }
      }
    };

    threadReadConsole.start();// запуск потока
  }

}
