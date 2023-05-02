package city.olooe.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class, SecurityConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

/*    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        CharacterEncodingFilter filter = new CharacterEncodingFilter("utf-8", true);
        return new Filter[] {filter, delegatingFilterProxy};
    }*/
}
