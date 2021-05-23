package com.bubbling.workflow.config;
import com.bubbling.frame.entity.TAcUserOrg;
import com.bubbling.frame.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @title: SecurityConfiguration
 * @Author yangwen
 * @Date: 2021-05-16 9:55
 * @ignore
 */
@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Bean
    public UserDetailsService myUserDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        //这里添加用户，后面处理流程时用到的任务负责人，需要添加在这里
        List<TAcUserOrg> tAcUserOrgs = userService.queryAllUserAndOrg();
        for (TAcUserOrg tAcUserOrg : tAcUserOrgs) {
            String[] user={tAcUserOrg.getUserId(),"111111","ROLE_ACTIVITI_USER","GROUP_"+tAcUserOrg.getOrgId()};
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }
        return inMemoryUserDetailsManager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
