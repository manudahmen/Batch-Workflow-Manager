package info.emptycanvas.bwm;

import java.util.ArrayList;
import java.util.List;


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
  public void registerListener(AppListener msg)
  {
      this.listener.add(msg);
  }
  public void run()
  {
    
  }

    public boolean isRunning() {
        return running;
    }
  
  
  
  public static void main(String [] args)
  {
    App app = new App();
    new Thread(app).start();
    app.registerListener(new ByEmailListener());
  }
}
