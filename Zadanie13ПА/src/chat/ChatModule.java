package chat;

public class ChatModule {
  private Boolean endChat;
  
  public Boolean getEndChat() {
    return endChat;
  }

  public void setEndChat(Boolean endChat) {
    this.endChat = endChat;
  }



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
