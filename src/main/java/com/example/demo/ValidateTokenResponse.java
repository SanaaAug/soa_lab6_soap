package com.example.demo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "validateTokenResponse", namespace = "http://example.com/auth")
public class ValidateTokenResponse {
    private boolean valid;
    private Long userId;
    private String username;
}