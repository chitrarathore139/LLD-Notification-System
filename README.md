# Notification System Using Design Patterns in Java

## 📌 Overview

This project demonstrates a notification system implemented in Java using object-oriented programming and design patterns. It supports multiple notification delivery methods and allows notification content to be dynamically enhanced using decorators.

The project demonstrates how different design patterns work together to create a modular, reusable, and maintainable notification system.

## ✨ Features

- **Dynamic Notifications:** Create notifications with customizable content.
- **Timestamp Decorator:** Add a timestamp to notification messages.
- **Signature Decorator:** Append a signature to notification content.
- **Observer Pattern:** Automatically notify registered observers when a new notification is created.
- **Multiple Notification Strategies:** Support email, SMS, and popup notifications.
- **Notification Logging:** Log notification content when a new notification is sent.
- **Notification Management:** Maintain a collection of notifications.

## 🛠️ Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Java Collections Framework
- Design Patterns

## 🏗️ Design Patterns Implemented

### 1. Decorator Pattern

The Decorator Pattern dynamically adds additional functionality to an existing notification without modifying its original class.

**Classes:**
- `Notification`
- `SimpleNotification`
- `NotificationDecorator`
- `TimeStampDecorator`
- `SignatureDecorator`

**Example:**

```java
Notification notification = new SimpleNotification("Your order has been shipped!");
notification = new TimeStampDecorator(notification);
notification = new SignatureDecorator(notification, "Customer Care");
```

### 2. Observer Pattern

The Observer Pattern allows multiple observers to receive updates when a new notification is created.

**Classes:**
- `Observable`
- `NotificationObservable`
- `Observer`
- `Logger`
- `NotificationEngine`

When a notification is updated, the observable notifies its registered observers.

### 3. Strategy Pattern

The Strategy Pattern allows the notification system to use different delivery methods through a common interface.

**Classes:**
- `NotificationStrategy`
- `EmailNotification`
- `SMSNotification`
- `PopUpNotification`

Each strategy implements the `sendNotification()` method to provide a different notification delivery mechanism.

### 4. Notification Service

The `NoticationService` class manages notifications and passes new notifications to the observable for processing.

It maintains a list of notifications and coordinates the notification workflow.

> Note: The current implementation does not include the Singleton Pattern. It can be added to ensure that only one instance of the notification service exists.

## 📂 Project Structure

```text
NotificationSystem/
├── NotificationDesign.java
└── README.md
```

The Java file contains the notification interfaces, concrete classes, decorators, observers, strategies, and service implementation.

## ⚙️ How to Run

### Prerequisites

- Java Development Kit (JDK) installed.
- A terminal or Java IDE such as IntelliJ IDEA or Eclipse.

### Steps

**1. Clone the repository**

```bash
git clone <your-repository-url>
```

**2. Navigate to the project directory**

```bash
cd NotificationSystem
```

**3. Compile the Java file**

```bash
javac NotificationDesign.java
```

**4. Run the program**

```bash
java NotificationDesign
```

## 📤 Sample Output

```text
Logging Notification:
[2025-04-13 14:22:00] Your order has been shipped!
-- Customer Care

Sending Email Notification:
[2025-04-13 14:22:00] Your order has been shipped!
-- Customer Care

Sending SMS Notification:
[2025-04-13 14:22:00] Your order has been shipped!
-- Customer Care

Sending Popup Notification:
[2025-04-13 14:22:00] Your order has been shipped!
-- Customer Care
```

*Note: The output formatting depends on the newline characters used in the notification content.*

## 🎯 Learning Objectives

- Understand the practical implementation of common design patterns.
- Apply abstraction and polymorphism in Java.
- Separate notification content from delivery mechanisms.
- Implement event-driven communication using the Observer Pattern.
- Extend existing objects using the Decorator Pattern.
- Switch between notification delivery methods using the Strategy Pattern.

## 🚀 Future Improvements

- Implement the Singleton Pattern for `NotificationService`.
- Add email addresses and phone numbers as configurable delivery targets.
- Integrate actual email and SMS delivery APIs.
- Add exception handling and input validation.
- Store notification history in a database.
- Add unit tests for individual design patterns.

## 👩‍💻 Author

**Chitra Rathore**

---

⭐ If you find this project useful for learning Java design patterns, consider giving the repository a star!
