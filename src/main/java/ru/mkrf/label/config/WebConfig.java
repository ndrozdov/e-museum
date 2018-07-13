package ru.mkrf.label.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import ru.mkrf.label.util.ConnectionFilter;

import javax.servlet.*;

public class WebConfig implements WebApplicationInitializer  {
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringServiceConfig.class, SpringDBConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringMVCConfig.class);
 
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("springMVC", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration filterRegistration = servletContext.addFilter("ConnectionFilter", new ConnectionFilter());
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                null
                , -1
                , -1
                , 0);

        dispatcher.setMultipartConfig(multipartConfigElement);

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}