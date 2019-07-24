package com.springdeveloper.mypets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.dekorate.kubernetes.annotation.Annotation;
import io.dekorate.kubernetes.annotation.Env;
import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;
import io.dekorate.kubernetes.annotation.ServiceType;

@KubernetesApplication(serviceType = ServiceType.LoadBalancer,
	ports = @Port(name = "http", containerPort = 80),
	annotations = {@Annotation(key = "service.projectriff.io/binding-secrets", value = "mypets-credentials")},
	envVars = {@Env(name = "SPRING_PROFILES_ACTIVE", value = "kubernetes")}, 
	imagePullPolicy = ImagePullPolicy.Always)
@SpringBootApplication
public class MypetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MypetsApplication.class, args);
	}

}
