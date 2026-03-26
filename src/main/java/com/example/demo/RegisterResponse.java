package com.example.demo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "registerResponse", namespace = "http://example.com/auth")
public class RegisterResponse {
    private boolean success;
    private String message;
    private Long userId;
}