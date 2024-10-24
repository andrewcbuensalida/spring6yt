package com.telusko.part38jwt.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Log request details
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URL: " + request.getRequestURL().toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        // Wrap the response to capture the content
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Log response status
        System.out.println("Response Status: " + wrappedResponse.getStatus());

        // Log response headers
        System.out.println("Response Headers:");
        Collection<String> headerNames = wrappedResponse.getHeaderNames();
        for (String headerName : headerNames) {
            System.out.println(headerName + ": " + wrappedResponse.getHeader(headerName));
        }

        // Log response body
        byte[] content = wrappedResponse.getContentAsByteArray();
        if (content.length > 0) {
            System.out.println("Response Body: " + new String(content, wrappedResponse.getCharacterEncoding()));
        }

        // Copy the response content back to the original response
        wrappedResponse.copyBodyToResponse();
    }
}