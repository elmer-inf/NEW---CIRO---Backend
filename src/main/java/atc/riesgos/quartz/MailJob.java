package atc.riesgos.quartz;

import atc.riesgos.dao.service.mail.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailJob implements Job {

    @Autowired
    private EmailService emailService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            emailService.sendMailRiesgosPlanes5Dias();
        } catch (Exception e) {
            log.error("Error al ejecutar el servicio de 'sendMailRiesgosPlanes5Dias' - Error: " + e);
            throw new JobExecutionException(e);
        }

        try {
            emailService.sendMailRiesgosPlanes10Dias();
        } catch (Exception e) {
            log.error("Error al ejecutar el servicio de 'sendMailRiesgosPlanes10Dias' - Error: " + e);
            throw new JobExecutionException(e);
        }

        try {
            emailService.sendMailRiesgosPlanesVencidos();
        } catch (Exception e) {
            log.error("Error al ejecutar el servicio de 'sendMailRiesgosPlanesVencidos' - Error: " + e);
            throw new JobExecutionException(e);
        }

        try {
            emailService.sendMailRiesgosRecurrentes();
        } catch (Exception e) {
            log.error("Error al ejecutar el servicio de 'sendMailRiesgosRecurrentes' - Error: " + e);
            throw new JobExecutionException(e);
        }

    }
}

