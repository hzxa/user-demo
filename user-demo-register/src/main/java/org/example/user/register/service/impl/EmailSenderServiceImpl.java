package org.example.user.register.service.impl;

//import cn.hutool.extra.template.engine.velocity.VelocityEngine;
//import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.example.user.core.dao.UserWelcomeMailSentMapper;
import org.example.user.core.domain.UserWelcomeMailSent;
import org.example.user.register.service.EmailService;
import org.example.user.register.service.EmailSenderService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Integer CORE_SIZE = 4;
    private static final Integer MAX_SIZE = 10;
    private static final Integer KEEP_ALIVE_IN_SECONDS = 60;

    private static final Integer THREAD_POOL_QUEUE_CAPACITY = 100;

    private final UserWelcomeMailSentMapper mailSentMapper;

    private final EmailService emailService;

    private VelocityEngine velocityEngine;

    private Template welComeEmailTemplate;

    private ExecutorService asyncSendMailExecutor;

    public EmailSenderServiceImpl(UserWelcomeMailSentMapper mailSentMapper, EmailService emailService) {
        this.mailSentMapper = mailSentMapper;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init(){
        velocityEngine= new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        welComeEmailTemplate = velocityEngine.getTemplate("template/welcomeMail.html","utf-8");

        this.asyncSendMailExecutor = new ThreadPoolExecutor(
                CORE_SIZE,
                MAX_SIZE,
                KEEP_ALIVE_IN_SECONDS,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(THREAD_POOL_QUEUE_CAPACITY),
                new ThreadFactoryBuilder().setNamePrefix("welcome-email-send").build(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    @Override
    public void sendWelcomeEmail(Long userId, String emailAddress, String firstName) {
        asyncSendMailExecutor.submit(new EmailSendTask(userId,firstName,emailAddress,emailService,mailSentMapper,welComeEmailTemplate));
    }

    static class EmailSendTask implements Runnable{
        private Long userId;

        private String firstName;

        private String emailAddress;

        private EmailService emailService;

        private UserWelcomeMailSentMapper mailSentMapper;

        private Template welcomeTemplate;

        public EmailSendTask(Long userId,
                             String firstName,
                             String emailAddress,
                             EmailService emailService,
                             UserWelcomeMailSentMapper mailSentMapper,
                             Template welcomeTemplate) {
            this.userId = userId;
            this.firstName = firstName;
            this.emailAddress = emailAddress;
            this.emailService = emailService;
            this.mailSentMapper = mailSentMapper;
            this.welcomeTemplate = welcomeTemplate;
        }

        @Override
        public void run() {
            try{
                VelocityContext context = new VelocityContext();
                context.put("firstName", firstName);
                StringWriter writer = new StringWriter();
                welcomeTemplate.merge(context, writer);

                String content = writer.toString();

                emailService.send(emailAddress, "Welcome", content);

                UserWelcomeMailSent row = new UserWelcomeMailSent();
                row.setUserId(userId);
                row.setMailContent(content);
                row.setcTime(new Date(System.currentTimeMillis()));
                mailSentMapper.insertSelective(row);
            }catch (Exception e){
                log.error("failed to send welcome email to:{}", userId);
            }
        }
    }
}
