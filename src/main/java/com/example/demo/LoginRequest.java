package com.example.demo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "loginRequest", namespace = "http://example.com/auth")
public class LoginRequest {

    @XmlElement(name = "username", namespace = "http://example.com/auth")
    private String username;

    @XmlElement(name = "password", namespace = "http://example.com/auth")
    private String password;
}