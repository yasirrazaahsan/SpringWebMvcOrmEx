package com.app.config;

import java.util.Locale;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.app.model.Employee;

@Configuration
@EnableWebMvc //MVC
@EnableTransactionManagement //Tx
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages="com.app")
public class AppConfig implements WebMvcConfigurer {
	//load properties
	@Autowired
	private Environment env;
	
	//DataSource
	@Bean
	public BasicDataSource dsObj() {
		BasicDataSource ds=new BasicDataSource();
		ds.setDriverClassName(env.getProperty("dc"));
		ds.setUrl(env.getProperty("url"));
		ds.setUsername(env.getProperty("un"));
		ds.setPassword(env.getProperty("pwd"));
		ds.setInitialSize(1);
		ds.setMaxIdle(10);
		ds.setMinIdle(5);
		ds.setMaxTotal(10);
		return ds;
	}
	//SessionFactory 
	@Bean
	public LocalSessionFactoryBean sfObj() {
		LocalSessionFactoryBean sf=new LocalSessionFactoryBean();
		sf.setDataSource(dsObj());
		sf.setAnnotatedClasses(Employee.class);
		sf.setHibernateProperties(props());
		return sf;
		
	}
	private Properties props() {
		Properties p=new Properties();
		p.put("hibernate.dialect", env.getProperty("dialect"));
		p.put("hibernate.show_sql", env.getProperty("showsql"));
		p.put("hibernate.format_sql", env.getProperty("fmtsql"));
		p.put("hibernate.hbm2ddl.auto", env.getProperty("ddlauto"));
		return p;
	}
	//HT
	@Bean
	public HibernateTemplate htObj() {
		HibernateTemplate ht=new HibernateTemplate();
		ht.setSessionFactory(sfObj().getObject());
		return ht;
	}
	//Tx manager
	@Bean
	public HibernateTransactionManager httx() {
		HibernateTransactionManager htm=new HibernateTransactionManager();
		htm.setSessionFactory(sfObj().getObject());
		return htm;
	}
	
	
	//view resolver
	@Bean
	public InternalResourceViewResolver ivr() {
		InternalResourceViewResolver v=new InternalResourceViewResolver();
		v.setPrefix("/WEB-INF/views/");
		v.setSuffix(".jsp");
		return v;
	}
	
	/**multi-language configuration**/
	//1. Message Source : .properties file name and data storing
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource r=new ReloadableResourceBundleMessageSource();
		r.setBasename("classpath:messages");
		r.setDefaultEncoding("UTF-8");
		return r;
	}
	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver c=new CookieLocaleResolver();
		c.setDefaultLocale(Locale.ENGLISH);
		c.setCookieName("my-cke");
		return c;
	}
	@Bean
	public LocaleChangeInterceptor interceptor() {
		LocaleChangeInterceptor l=new LocaleChangeInterceptor();
		l.setParamName("lang");
		return l;
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor());
	}
		
}