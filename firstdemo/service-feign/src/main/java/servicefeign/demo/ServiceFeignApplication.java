package servicefeign.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * fegin实现负载均衡，使用Hystrix熔断器
 * @EnableHystrixDashboard
 * @EnableCircuitBreaker
 * 实现监听功能
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ServiceFeignApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceFeignApplication.class, args);
	}
}
