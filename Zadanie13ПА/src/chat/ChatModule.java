package chat;

public class ChatModule {
  
  public ChatModule(){
    ReadConsoleModule readConsoleModule = new ReadConsoleModule();
    readConsoleModule.init();
    readConsoleModule.start();
    
    ReadHistoryModule readHistoryModule = new ReadHistoryModule();
    readHistoryModule.init(readConsoleModule.getUserName());
    readHistoryModule.start();
  }


}
