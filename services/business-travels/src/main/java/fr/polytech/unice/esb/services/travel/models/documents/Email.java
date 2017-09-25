package fr.polytech.unice.esb.services.travel.models.documents;

public class Email<T> {

    private String recipient;
    private T content;

    public Email() {
        // Empty ctr
    }

    public Email(String recipient, T content) {
        this.recipient = recipient;
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
