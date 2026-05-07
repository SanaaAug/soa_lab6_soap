package com.example.demo;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Spring-WS тохиргоо: SOAP endpoint болон WSDL үүсгэнэ
@EnableWs
@Configuration
public class SoapConfig {

    // SOAP хүсэлтүүдийг боловсруулах servlet-ийг /ws/* замд бүртгэнэ
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        // WSDL доторх хаягийг хүсэлтийн URL-д тааруулж өөрчилнө
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // WSDL тодорхойлолт — /ws/auth.wsdl хаягаар хандах боломжтой
    @Bean(name = "auth")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema authSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("AuthPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://example.com/auth");
        definition.setSchema(authSchema);
        return definition;
    }

    // auth.xsd файлаас schema уншина
    @Bean
    public XsdSchema authSchema() {
        return new SimpleXsdSchema(new ClassPathResource("auth.xsd"));
    }
}
