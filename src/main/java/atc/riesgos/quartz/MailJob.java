//package atc.riesgos.quartz;
//
//import atc.riesgos.dao.service.mail.EmailService;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MailJob implements Job {
//
//    @Autowired
//    private EmailService emailService;
//
//    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        try {
//            emailService.sendMailRiesgosPlanes5Dias();
//        } catch (Exception e) {
//            logger.error("Error al ejecutar el servicio de 'sendMailRiesgosPlanes5Dias' - Error: " + e.getMessage());
//            throw new JobExecutionException(e);
//        }
//
//        try {
//            emailService.sendMailRiesgosPlanes10Dias();
//        } catch (Exception e) {
//            logger.error("Error al ejecutar el servicio de 'sendMailRiesgosPlanes10Dias' - Error: " + e.getMessage());
//            throw new JobExecutionException(e);
//        }
//
//        try {
//            emailService.sendMailRiesgosPlanesVencidos();
//        } catch (Exception e) {
//            logger.error("Error al ejecutar el servicio de 'sendMailRiesgosPlanesVencidos' - Error: " + e.getMessage());
//            throw new JobExecutionException(e);
//        }
//    }
//}
//
