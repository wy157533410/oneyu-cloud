package com.oneyu.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  9:22:03 PM Jun 14, 2020
 */


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain6791
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayMain6791.class, args);
    }
}
