package ru.terentyev.playtoxapp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class HibernateConfig {

	private ApplicationContext context;
	private Environment environment;
	

	@Autowired
	public HibernateConfig(ApplicationContext context, Environment environment) {
		super();
		this.environment = environment;
	}
	
	 @Bean
	 public DataSource dataSource() {
	     DriverManagerDataSource dataSource = new DriverManagerDataSource();

	     dataSource.setDriverClassName(environment.getRequiredProperty("hibernate.driver_class"));
	     dataSource.setUrl(environment.getRequiredProperty("hibernate.connection.url"));
	     dataSource.setUsername(environment.getRequiredProperty("hibernate.connection.username"));
	     dataSource.setPassword(environment.getRequiredProperty("hibernate.connection.password"));

	     return dataSource;
	 }
	 
	    private Properties hibernateProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

	        return properties;
	    }
	   
	   
	    
	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan("ru.terentyev.playtoxapp.entities");
	        sessionFactory.setHibernateProperties(hibernateProperties());

	        return sessionFactory;
	    }
	    
	   
	    
	    @Bean
	    public PlatformTransactionManager hibernateTransactionManager() {
	        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	        transactionManager.setSessionFactory(sessionFactory().getObject());

	        return transactionManager;
	    }
	    
}
