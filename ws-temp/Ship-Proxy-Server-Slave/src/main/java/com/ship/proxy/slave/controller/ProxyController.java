package com.ship.proxy.slave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ship.proxy.slave.config.ClientProxyConfig;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
	
	@Autowired
	private ClientProxyConfig clientProxyConfig;


    @GetMapping("/fetch")
    public void forwardRequest(@RequestParam String url) {
    	clientProxyConfig.sendMessage(url);
    }

}
