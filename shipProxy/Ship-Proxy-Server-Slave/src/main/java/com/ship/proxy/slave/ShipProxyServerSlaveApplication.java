package com.ship.proxy.slave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.ship.proxy.slave.config.ClientProxyConfig;
@SpringBootApplication
public class ShipProxyServerSlaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipProxyServerSlaveApplication.class, args);
	}
	
//    @Bean
//    CommandLineRunner run(ClientProxyConfig nettyClient) {
//        return args -> {
//            nettyClient.sendMessage("Hello Offshore Proxy!");
//        };
//    }

}
