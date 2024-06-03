//package atc.riesgos.quartz;
//
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.CronScheduleBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.TimeZone;
//
//@Configuration
//public class QuartzConfig {
//
//    @Value("${email.schedule.cron}")
//    private String cronSchedule;
//
//    @Bean
//    public JobDetail jobDetail() {
//        return JobBuilder.newJob(MailJob.class)
//                .withIdentity("sendMailRiesgoPlanesVencidosJob")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger trigger(JobDetail jobDetail) {
//        TimeZone.setDefault(TimeZone.getTimeZone("America/La_Paz"));
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
//                .withIdentity("sendMailRiesgoPlanesVencidosTrigger")
//                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
//                .build();
//    }
//}
//
