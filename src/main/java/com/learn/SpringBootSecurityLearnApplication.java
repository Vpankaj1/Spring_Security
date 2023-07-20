package com.learn;


import com.learn.models.User;
import com.learn.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootSecurityLearnApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(SpringBootSecurityLearnApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityLearnApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		User user=new User();
		user.setEmail("john@gmail.com");
		user.setUsername("john");
		//new User("abc","abc","abc@gmail.com")
		user.setPassword(this.bCryptPasswordEncoder.encode("lajka"));
		//user.setRole("normal");
		user.setRole("ROLE_NORMAL");
		this.userRepository.save(user);

		User user1=new User();
		user1.setEmail("roshni@gmail.com");
		user1.setUsername("roshni");
		//new User("abc","abc","abc@gmail.com")
		user1.setPassword(this.bCryptPasswordEncoder.encode("abc"));
		//user1.setRole("admin");
		user1.setRole("ROLE_ADMIN");
		this.userRepository.save(user1);

	}
}
