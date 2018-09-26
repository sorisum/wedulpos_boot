package com.wedul.common.config;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.wedul.common.util.MessageBundleUtil;

/**
 * Root는 controller를 제외한 모든 설정을 지정한다. root-context.xml과 동일하다
 * 
 * @author wedul
 *
 */
@Configuration
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
@EnableTransactionManagement(proxyTargetClass = true) // • TransactionInterceptor가 @Transactional 어노테이션이 부여된 메소드에 트랜잭션 적용 xml의 <tx:annotation-driven />와 같다
@EnableAspectJAutoProxy(proxyTargetClass = true) // 자동으로 AspectJ 라이브러리를 이용해서 Proxy 객체를 생성해 내는 용도로 사용된다. xml의 <aop:aspectj-autoproxy></aop:aspectj-autoproxy> 설정과 같다
@ComponentScan(basePackages = {"com.wedul.*", "com.wedul.common.interceptor" })
@MapperScan(basePackages = {"com.wedul.wedulpos.*.*"}, annotationClass=Mapper.class)
public class RootApplicationContextConfig {

	@Bean(name = "sqlSession", destroyMethod = "clearCache")
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource , ApplicationContext applicationContext) throws Exception { 
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));
		sessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        return sessionFactory.getObject();
	}
	    
	// Message bundle
	@Bean
	public MessageBundleUtil messageBundleUtil(MessageSource source) throws Exception {
        MessageSourceAccessor accessor = new MessageSourceAccessor(source);
        MessageBundleUtil util = new MessageBundleUtil();
        util.setMessageSourceAccessor(accessor);
        return util;
	}
	
	@Bean(name ="messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setDefaultEncoding("utf-8");
		// true 라면 (기본값) message_ko_KR.properties 와 message.prorperties를 참조하고
		// false 라면 message.properties 만 참조한다.  ​
		messageSource.setFallbackToSystemLocale(true);
		messageSource.setBasenames("classpath:messages/common/common", 
								   "classpath:messages/error/error", 
								   "classpath:messages/money/money", 
								   "classpath:messages/schedule/schedule",
								   "classpath:messages/message/message",
								   "classpath:messages/todo/todo",
								   "classpath:messages/user/user");
	    return messageSource;
	}
	
	@Bean
	public SessionLocaleResolver getLocaleResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(Locale.KOREA);
		return localResolver;
	}
	
    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{
                "classpath:tiles/tiles.xml",
                "classpath:tiles/todo-tiles.xml",
                "classpath:tiles/money-tiles.xml",
                "classpath:tiles/schedule-tiles.xml",
                "classpath:tiles/message-tiles.xml"
        });
        return tilesConfigurer;
    }
	
}
