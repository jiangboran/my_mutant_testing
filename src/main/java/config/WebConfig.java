package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${url}")
    private String url;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                //如果有多个路径需要跨域，只需要将跨域路径放入数组中
                //String []  allowDomain={"http://**","http://*","http://*"};
                //.allowedOrigins(allowDomain)//多url跨域
                .allowedOrigins(url)//设置允许跨域请求的域名
                .allowCredentials(true)//是否允许证书 不写默认开启
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE","PATCH") //设置允许的方法
                .maxAge(3600);//跨域允许时间
    }
}