import java.util.*;

class NotificationSystemUpdated {
  public static void main(String[] args) {
    // Get the single instance of NotificationService (Singleton Pattern).
    NotificationService service = NotificationService.getInstance();

    // Get the observable that manages observers and notifications.
    NotificationObservable observable = service.getObservable();

    // Create a Logger and register it as an observer.
    new Logger(observable);

    // Create a NotificationEngine and register it as an observer.
    NotificationEngine engine = new NotificationEngine(observable);

    // Add different notification delivery strategies.
    engine.addStrategy(new EmailStrategy("random.person@gmail.com"));
    engine.addStrategy(new SMSStrategy("+91 9876543210"));
    engine.addStrategy(new PopUpStrategy());

    // Create a basic notification.
    Notification n = new SimpleNotification("Your order has been shipped!");

    // Wrap the notification to add a timestamp.
    n = new TimestampDecorator(n);

    // Wrap it again to add a signature.
    n = new SignatureDecorator(n, "Customer Care");

    // Store the notification and notify all registered observers.
    service.sendNotification(n);
  }
}

// Common interface for all notification types.
interface Notification {
  String getContent();
}

// Creates a basic notification containing text.
class SimpleNotification implements Notification {
  private String content;

  public SimpleNotification(String content) {
    this.content = content;
  }

  // Returns the original notification message.
  @Override
  public String getContent() {
    return content;
  }
}

// Base decorator that wraps another notification.
abstract class NotificationDecorator implements Notification {
  protected Notification notification;

  public NotificationDecorator(Notification n) {
    this.notification = n;
  }
}

// Adds a timestamp to the existing notification.
class TimestampDecorator extends NotificationDecorator {
  public TimestampDecorator(Notification n) {
    super(n);
  }

  // Adds the timestamp before the existing content.
  @Override
  public String getContent() {
    return "[2025-04-13 14:22:00] " + notification.getContent();
  }
}

// Adds a signature to the existing notification.
class SignatureDecorator extends NotificationDecorator {
  private String signature;

  public SignatureDecorator(Notification n, String signature) {
    super(n);
    this.signature = signature;
  }

  // Returns the existing content followed by the signature.
  @Override
  public String getContent() {
    return notification.getContent() + "\n-- " + signature + "\n";
  }
}

// Common interface for all observers.
interface Observer {
  void update();
}

// Defines the operations required by an observable.
interface Observable {
  void addObserver(Observer o);
  void removeObserver(Observer o);
  void notifyObservers();
}

// Maintains observers and the latest notification.
class NotificationObservable implements Observable {
  private ArrayList<Observer> observers = new ArrayList<>();
  private Notification notification;

  // Registers an observer to receive notifications.
  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  // Removes an observer from the list.
  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  // Calls update() on every registered observer.
  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  // Stores the new notification and informs all observers.
  public void setNotification(Notification n) {
    this.notification = n;
    notifyObservers();
  }

  // Returns the current notification object.
  public Notification getNotification() {
    return notification;
  }

  // Returns the formatted content of the current notification.
  public String getContent() {
    return notification.getContent();
  }
}

// Manages notifications using the Singleton Pattern.
class NotificationService {
  private static NotificationService instance;
  private NotificationObservable observable;
  private ArrayList<Notification> notifications = new ArrayList<>();

  // Private constructor prevents other classes from creating instances directly.
  private NotificationService() {
    observable = new NotificationObservable();
  }

  // Creates the service once and returns the same instance on subsequent calls.
  public static NotificationService getInstance() {
    if (instance == null) {
      instance = new NotificationService();
    }
    return instance;
  }

  // Provides access to the observable.
  public NotificationObservable getObservable() {
    return observable;
  }

  // Stores the notification and passes it to the observable.
  public void sendNotification(Notification n) {
    notifications.add(n);
    observable.setNotification(n);
  }
}

// Observer responsible for logging notification content.
class Logger implements Observer {
  private NotificationObservable observable;

  public Logger(NotificationObservable observable) {
    this.observable = observable;

    // Register this Logger with the observable.
    observable.addObserver(this);
  }

  // Called automatically when the observable sends an update.
  @Override
  public void update() {
    System.out.println("Logging Notification:\n" + observable.getContent());
  }
}

// Common interface for all notification delivery methods.
interface NotificationStrategy {
  void sendNotification(String content);
}

// Sends the notification to an email address.
class EmailStrategy implements NotificationStrategy {
  private String email;

  public EmailStrategy(String email) {
    this.email = email;
  }

  @Override
  public void sendNotification(String content) {
    System.out.println("Sending Email to: " + email + "\n" + content);
  }
}

// Sends the notification to a mobile number.
class SMSStrategy implements NotificationStrategy {
  private String mobileNumber;

  public SMSStrategy(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  @Override
  public void sendNotification(String content) {
    System.out.println("Sending SMS to: " + mobileNumber + "\n" + content);
  }
}

// Displays the notification as a popup.
class PopUpStrategy implements NotificationStrategy {
  @Override
  public void sendNotification(String content) {
    System.out.println("Sending Popup Notification:\n" + content);
  }
}

// Observer responsible for delivering notifications through different strategies.
class NotificationEngine implements Observer {
  private NotificationObservable observable;
  private ArrayList<NotificationStrategy> strategies = new ArrayList<>();

  public NotificationEngine(NotificationObservable observable) {
    this.observable = observable;

    // Register the engine so it receives notification updates.
    observable.addObserver(this);
  }

  // Adds a delivery strategy to the list.
  public void addStrategy(NotificationStrategy strategy) {
    strategies.add(strategy);
  }

  // Called when a new notification is available.
  @Override
  public void update() {
    // Get the latest notification content.
    String content = observable.getContent();

    // Send the content through every registered strategy.
    for (NotificationStrategy strategy : strategies) {
      strategy.sendNotification(content);
    }
  }
}
