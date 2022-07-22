package chat;

/**
 * Класс "Сообщение" с полями 
 */
public class ChatMessage {
  private String user; // имя пользователя
  private String message; // сообщение
  private String dataTime; // дата и время сообщения

  /**
   *  конструктор класса "сообщение"
   * @param user - имя пользователя
   * @param message - сообщение пользователя
   * @param dataTime - дата и время сообщения
   */
  public ChatMessage(String user, String message, String dataTime) {
    this.user = user;
    this.message = message;
    this.dataTime = dataTime;
  }

  /**
   * тектосове представление сообщения. Например 17.07.2022 15:47:38 Ivanov Privet ot ivanova
   */
  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append(dataTime).append(" ").append(user).append(" ").append(message).append("\r\n");
    return builder.toString();
  }

}
