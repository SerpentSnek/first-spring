package firstspring.firstspringdemo;

import org.springframework.boot.SpringApplication;

public class TestFirstspringdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(FirstspringdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
