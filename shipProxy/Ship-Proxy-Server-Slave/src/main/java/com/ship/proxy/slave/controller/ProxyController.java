package com.ship.proxy.slave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ship.proxy.slave.config.ClientProxyConfig;

@RestController
@RequestMapping("/")
public class ProxyController {
    
    @Autowired
    private ClientProxyConfig clientProxyConfig;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, 
                     RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD})
    public void forwardRequest(HttpServletRequest request, 
                             @RequestBody(required = false) byte[] body) {
        // Extract full URL from request
        String fullUrl = request.getRequestURL().toString() + 
                        (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        
        // Forward to offshore proxy
        clientProxyConfig.sendMessage(fullUrl);
    }

    @RequestMapping(value = "/", method = RequestMethod.CONNECT)
    public void handleConnect(HttpServletRequest request) {
        // Handle HTTPS CONNECT requests
        String host = request.getRemoteHost();
        clientProxyConfig.sendMessage("CONNECT " + host);
    }
}