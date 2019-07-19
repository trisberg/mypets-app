package com.springdeveloper.mypets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.dekorate.kubernetes.annotation.Env;
import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;
import io.dekorate.kubernetes.annotation.ServiceType;
import io.dekorate.servicecatalog.annotation.Parameter;
import io.dekorate.servicecatalog.annotation.ServiceCatalog;
import io.dekorate.servicecatalog.annotation.ServiceCatalogInstance;

@KubernetesApplication(serviceType = ServiceType.LoadBalancer,
	ports = @Port(name = "web", containerPort = 8080), 
	envVars = {@Env(name = "SPRING_PROFILES_ACTIVE", value = "kubernetes")}, 
	imagePullPolicy = ImagePullPolicy.Always)
@ServiceCatalog(instances = @ServiceCatalogInstance(name = "mypets", 
		serviceClass = "mysql", servicePlan = "5-7-14", 
		bindingSecret = "mypets-credentials",
		parameters = {@Parameter(key = "mysqlDatabase", value = "mypets")}))
@SpringBootApplication
public class MypetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MypetsApplication.class, args);
	}

}
