package com.wayruha;

import java.io.Serializable;

/**
 * Simple abstraction of message. It`s used in app to communicate instead of common text.
 * Contains information about author, message index (per author) and previous message
 */
public class Message implements Serializable {
    private String author;
    private String text;
    private int authorsMessageindex;
    private Message prevMsg;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAuthorsMessageindex() {
        return authorsMessageindex;
    }

    public void setAuthorsMessageindex(int authorsMessageindex) {
        this.authorsMessageindex = authorsMessageindex;
    }

    public Message getPrevMsg() {
        return prevMsg;
    }

    public void setPrevMsg(Message prevMsg) {
        this.prevMsg = prevMsg;
    }

    @Override
    public String toString() {
        String str = "Msg{" +
                "from " + author +
                " #" + authorsMessageindex +
                ": '" + text + '\'';

        if (prevMsg != null)
            str += ", prevMsg=" + prevMsg;

        str += '}';
        return str;
    }
}
