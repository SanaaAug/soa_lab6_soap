package com.example.demo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "validateTokenRequest", namespace = "http://example.com/auth")
public class ValidateTokenRequest {

    @XmlElement(name = "token", namespace = "http://example.com/auth")
    private String token;
}