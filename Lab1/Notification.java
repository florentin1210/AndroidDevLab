abstract class Notification {
    private String message;

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void displayNotification() {
        System.out.println("Notification: " + message);
    }
}

class SMSNotification extends Notification implements Notifiable {
    public SMSNotification(String message) {
        super(message);
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending SMS Notification: " + getMessage());
    }
}

class EmailNotification extends Notification implements Notifiable {
    public EmailNotification(String message) {
        super(message);
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending Email Notification: " + getMessage());
    }
}

class PushNotification extends Notification implements Notifiable {
    public PushNotification(String message) {
        super(message);
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending Push Notification: " + getMessage());
    }
}