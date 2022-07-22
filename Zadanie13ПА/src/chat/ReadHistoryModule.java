package chat;

public class ReadHistoryModule {
  private FileModule fileModule; // объект файлового модуля для работы с историей сообщений
  private int countLinesHistory; // счетчик количества сообщений в истории сообщений
  Thread threadReadHistory; // поток для бексонечно перечитывания истории сообщений из файла
  private String username;
  ChatModule chatModule;

  ReadHistoryModule() {
    fileModule = new FileModule(); // создаем объект для работы с файлом истории сообщений
    countLinesHistory = 0; // начальное значение количества сообщений в истории
  }

  private void pause(int ms) {
    try {
      Thread.sleep(ms); // пауза 1 секунда
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void init(String username, ChatModule chatModule) {
    this.username = username;
    this.chatModule = chatModule;
  }

  public void start() {
    // отдельный поток который постоянно читает историю сообщений для вывода новых
    // сообщений в консоль
    threadReadHistory = new Thread() { // создаем поток
      @Override
      public void run() {
        while (!ChatModule.endChat) {
          pause(1000);
          int oldCountLinesHistory = countLinesHistory; // сохраняем предыдущее значение количества сообщений
          countLinesHistory = fileModule.readHistory(); // читаем историю сообщений из файла
          if (countLinesHistory > 0) { // если в истории есть сообщения
            if (countLinesHistory > oldCountLinesHistory) { // если количество больше старого количества
              for (var i = oldCountLinesHistory; i < countLinesHistory; i++) { // цикл по новым сообщениям
                String lineHistory = fileModule.getLineHistory(i); // берем строку с сообщением из списка
                System.out.println(lineHistory);
              }
            }
          }
        }
      }

    };

    threadReadHistory.start();// запуск потока
  }
}
