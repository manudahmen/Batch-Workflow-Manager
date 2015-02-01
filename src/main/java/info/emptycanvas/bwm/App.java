package info.emptycanvas.wmb;
interface AppListener
{
  
}
class ByMailListener implements AppListener, Runnable
{
  public ByMailListener(String username, String password, String host, int port)
  {
    configure(String username, String password, String host, int port);
  }
  public void configure(String username, String password, String host, int port)
  {
    
  }
  public void run()
  {
    while(app.isRunning())
    {
      
    }
  }
}

public class App implements Runnable
{
  List<AppListener> listener = new ArrayList<AppListener>();
  private boolean running = false;

  public App()
  {
    initApp();
    running = true;
  }
  public void initApp()
  {
    
  }
  public void listen(AppListener msg)
  {
  }
  public void run()
  {
    
  }
  public static void main(String [] args)
  {
    App app = new App();
    new Thread(app).start();
    app.listen(new ByEmailListener()
  }
}
