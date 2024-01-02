package hello.mylogin;

import hello.mylogin.config.MemberConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
public class MyloginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyloginApplication.class, args);
	}

}
