import java.util.*;

class NotificationDesign {
  public static void main(String[] args) {
    NoticationService service = new NoticationService();
    NotificationObservable observable = new NotificationObservable();
    Logger logger = new Logger(observable);
    NotificationEngine engine = new NotificationEngine(observable);

    engine.strategies.add(new EmailNotification());
    engine.strategies.add(new SMSNotification());
    engine.strategies.add(new PopUpNotification());

    observable.addObserver(logger);
    observable.addObserver(engine);

    Notification n = new SimpleNotification("Your order has been shipped!");
    n = new TimeStampDecorator(n);
    n = new SignatureDecorator(n, "Customer Care");

    service.sendNotification(n, observable);
  }
}

interface Notification {
  String getContent();
}

class SimpleNotification implements Notification {
  private String content;

  public SimpleNotification(String content) {
    this.content = content;
  }

  @Override
  public String getContent() {
    return content;
  }
}

abstract class NotificationDecorator implements Notification {
  protected Notification notification;

  public NotificationDecorator(Notification n) {
    this.notification = n;
  }
}

class TimeStampDecorator extends NotificationDecorator {
  public TimeStampDecorator(Notification n) {
    super(n);
  }

  @Override
  public String getContent() {
    return "[2025-04-13 14:22:00] " + notification.getContent();
  }
}

class SignatureDecorator extends NotificationDecorator {
  private String signature;

  public SignatureDecorator(Notification n, String signature) {
    super(n);
    this.signature = signature;
  }

  @Override
  public String getContent() {
    return notification.getContent() + "\n-- " + signature + "\n";
  }
}

class NoticationService {
  ArrayList<Notification> list = new ArrayList<>();

  public void sendNotification(Notification n, NotificationObservable observable) {
    list.add(n);
    observable.setNotification(n);
  }
}

interface Observable {
  void addObserver(Observer o);
  void removeObserver(Observer o);
  void notifyObservers();
}

class NotificationObservable implements Observable {
  private ArrayList<Observer> observers = new ArrayList<>();
  private Notification n;

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  public void setNotification(Notification n) {
    this.n = n;
    notifyObservers();
  }

  public Notification getNotification() {
    return n;
  }

  public String getNotificationContent() {
    return n.getContent();
  }
}

interface Observer {
  void update();
}

class Logger implements Observer {
  private NotificationObservable nO;

  public Logger(NotificationObservable nO) {
    this.nO = nO;
  }

  @Override
  public void update() {
    System.out.println("Logging Notification:\n" + nO.getNotificationContent());
  }
}

class NotificationEngine implements Observer {
  NotificationObservable nO;
  ArrayList<NotificationStrategy> strategies = new ArrayList<>();

  public NotificationEngine(NotificationObservable nO) {
    this.nO = nO;
  }

  @Override
  public void update() {
    String content = nO.getNotificationContent();
    for (NotificationStrategy strategy : strategies) {
      strategy.sendNotification(content);
    }
  }
}

interface NotificationStrategy {
  void sendNotification(String content);
}

class EmailNotification implements NotificationStrategy {
  @Override
  public void sendNotification(String content) {
    System.out.println("Sending Email Notification:\n" + content);
  }
}

class SMSNotification implements NotificationStrategy {
  @Override
  public void sendNotification(String content) {
    System.out.println("Sending SMS Notification:\n" + content);
  }
}

class PopUpNotification implements NotificationStrategy {
  @Override
  public void sendNotification(String content) {
    System.out.println("Sending Popup Notification:\n" + content);
  }
}
