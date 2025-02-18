public class NotificationSystem {
    public static void main(String[] args) {
        Notifiable sms = new SMSNotification("This is an SMS message");
        Notifiable email = new EmailNotification("This is an Email message");
        Notifiable push = new PushNotification("This is a Push Notification");

        sms.sendNotification();
        email.sendNotification();   
        push.sendNotification();
    }
}
