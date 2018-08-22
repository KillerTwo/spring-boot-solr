package lwt.org.springbootsolr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootSolrApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSolrApplication.class, args);
	}
}
/**
 * 
 *      移除内嵌的tomcat后需要继承SpringBootServletInitializer类并实现configure方法
 * @author Administrator
 *
 */
/*@SpringBootApplication
public class SpringBootSolrApplication extends SpringBootServletInitializer{
  public static void main(String[] args) {
    SpringApplication.run(SpringBootSolrApplication.class, args);
  }
  
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(this.getClass());
  }
  
}*/
