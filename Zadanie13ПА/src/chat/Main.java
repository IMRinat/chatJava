package chat;

import java.io.IOException;

/**
 * Главный класс
 */
public class Main {
  /**
   * Программа должна считывать с консоли сообщения неограниченного числа
   * пользователей и отображать его всем пользователям, которые есть в чате. При
   * первом входе в чат, новый пользователь должен увидеть всю историю сообщений.
   */

  public static void main(String[] args) throws IOException, InterruptedException {
    (new ChatModul()).start(); // Создаем объект чата и запускаем основную функцию
  }

}
