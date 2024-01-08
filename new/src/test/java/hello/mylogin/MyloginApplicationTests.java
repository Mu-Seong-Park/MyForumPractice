package hello.mylogin;

import hello.mylogin.config.MemberConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@SpringBootTest
class MyloginApplicationTests {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MemberConfig.class);
	@Test
	void findAllBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			Object bean = ac.getBean(beanDefinitionName);
			log.info("name = {} object = {}", beanDefinitionName,bean);
		}
	}
	@Test
	void findApplicationBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

			if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
				Object bean = ac.getBean(beanDefinitionName);
				log.info("name = {} object = {}", beanDefinitionName, bean);
			}
		}
	}

}
