package com.example.demo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "registerRequest", namespace = "http://example.com/auth")
public class RegisterRequest {

    @XmlElement(name = "username", namespace = "http://example.com/auth")
    private String username;

    @XmlElement(name = "password", namespace = "http://example.com/auth")
    private String password;

    @XmlElement(name = "email", namespace = "http://example.com/auth")
    private String email;
}