package hu.ait.android.aitchat.data;

public class ForumMessage {
    private String text;
    private String sender;

    public ForumMessage() {
    }

    public ForumMessage(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
