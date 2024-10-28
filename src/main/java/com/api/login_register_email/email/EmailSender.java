package com.api.login_register_email.email;

public interface EmailSender {
    public void send(String to, String email);
}
