package academy.devdojo.spring_boot_essentials.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration // A configuration which will be applied globally in the spring
public class DevDojoWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) { // The configuration of page size and the initial page what will be displayed when get http://localhost:8080/animes
        PageableHandlerMethodArgumentResolver pageableHandler = new PageableHandlerMethodArgumentResolver();
        pageableHandler.setFallbackPageable(PageRequest.of(0, 5));
        resolvers.add(pageableHandler);
    }

}
