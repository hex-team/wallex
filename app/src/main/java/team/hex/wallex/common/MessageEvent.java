package team.hex.wallex.common;

/**
 * Created by alireza on 7/13/17.
 */

public class MessageEvent {

    public enum Message {
        backPress, loadComplete, loadError, onOption
    }

    private Message message;
    private String textMessage;


    public MessageEvent(Message message) {
        this.message = message;
    }

    public MessageEvent(Message message, String textMessage) {
        this.message = message;
        this.textMessage = textMessage;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
