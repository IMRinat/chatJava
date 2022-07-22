package chat;

public class ChatModule {
  public static Boolean endChat;
  
  public ChatModule(){
    endChat = false;
    ReadConsoleModule readConsoleModule = new ReadConsoleModule(this);
    readConsoleModule.init();
    readConsoleModule.start();
    
    ReadHistoryModule readHistoryModule = new ReadHistoryModule();
    readHistoryModule.init(readConsoleModule.getUserName(),this);
    readHistoryModule.start();
  }


}
