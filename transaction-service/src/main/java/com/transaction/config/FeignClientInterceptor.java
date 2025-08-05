
package com.transaction.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.transaction.security.JwtUtil;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void apply(RequestTemplate template) {
        String jwt = jwtUtil.generateInternalToken();
        template.header("Authorization", "Bearer " + jwt);
    }
}
