package atc.riesgos.dao.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
@Configuration
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    MatrizRiesgoRepository matrizRiesgoRepository;

    @Value("${mailCc}")
    private String mailCc;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("noreply@atc.com.bo");
        helper.setTo(to);
        if (mailCc != null && !mailCc.isEmpty()) {
            helper.addCc(mailCc);
        }
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    @Transactional
    public void sendMailRiesgosPlanes5Dias(){
        List<Object[]> results = matrizRiesgoRepository.planesAVencer5Dias();
        if (results != null) {
            for (Object[] result : results) {
                if (("NO").equals(result[7])) { // Envio solo para los que no esten informados
                    if (result[6] != null && !((String) result[6]).trim().isEmpty()) {  // Envio a solo a los que tengan 'cargo' registrado en el Plan
                        // arma destinatario
                        String destinatario = "";
                        if(result[6].toString().equals("Supervisor Comercial") || result[6].toString().equals("Supervisor de Servicio Post Venta")){
                            System.out.println("cargo:  " + result[6].toString());
                            destinatario = matrizRiesgoRepository.getCorreoByCargo("Gerente de Negocio Adquirente");
                        } else{
                            destinatario = result[8].toString();
                        }

                        if (destinatario != null && !destinatario.trim().isEmpty()) {
                            try {
                                String htmlContent = htmlTemplatePlanesAVencer(result[3].toString(), result[4].toString(), "cinco");
                                sendHtmlMessage(destinatario, "NOTIFICACIÓN PLAN DE ACCIÓN PENDIENTE", htmlContent);
                                System.out.println("Correo enviado");
                                logger.info("Correo enviado - ID Riesgo " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());

                                int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
                                if (updateCount == 1) {
                                    System.out.println("Campo 'informadoPorCorreo' actualizado correctamente.");
                                } else{
                                    System.out.println("Error al actualizar campo 'informadoPorCorreo'.");
                                    logger.error("Error al actualizar campo 'informadoPorCorreo' - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
                                }
                            } catch (MessagingException e) {
                                System.out.println("Error al enviar correo en 'sendMailRiesgosPlanes5Dias': " + e.getMessage());
                                logger.error("Error al enviar correo en 'sendMailRiesgosPlanes5Dias': " + e.getMessage());
                            }
                        }
                        else{
                            System.out.println("No se envio el correo por que el campo 'CorreoCargo' es nulo o está vacío.");
                            logger.warn("No se envio el correo por que el campo 'CorreoCargo' es nulo o está vacío.");
                        }
                    } else{
                        System.out.println("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
                        logger.warn("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
                    }
                }
            }
        } else {
            System.out.println("No hay planes a vencer en los próximos 5 días.");
            logger.info("No hay planes a vencer en los próximos 5 días.");
        }
    }

    @Transactional
    public void sendMailRiesgosPlanes10Dias(){
        List<Object[]> results = matrizRiesgoRepository.planesAVencer10Dias();
        if (results != null) {
            for (Object[] result : results) {
                if (("NO").equals(result[7])) { // Envio solo para los que no esten informados
                    if (result[6] != null && !((String) result[6]).trim().isEmpty()) { // Envio a solo a los que tengan 'cargo' registrado en el Plan
                        // arma destinatario
                        String destinatario = "";
                        if(result[6].toString().equals("Supervisor Comercial") || result[6].toString().equals("Supervisor de Servicio Post Venta")){
                            System.out.println("cargo:  " + result[6].toString());
                            destinatario = matrizRiesgoRepository.getCorreoByCargo("Gerente de Negocio Adquirente");
                        } else{
                            destinatario = result[8].toString();
                        }

                        if (destinatario != null && !destinatario.trim().isEmpty()) {
                            try {
                                String htmlContent = htmlTemplatePlanesAVencer(result[3].toString(), result[4].toString(), "diez");
                                sendHtmlMessage(destinatario, "NOTIFICACIÓN PLAN DE ACCIÓN PENDIENTE", htmlContent);
                                System.out.println("Correo enviado");
                                logger.info("Correo enviado - ID Riesgo " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());

                                int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
                                if (updateCount == 1) {
                                    System.out.println("Campo 'informadoPorCorreo' actualizado correctamente.");
                                } else {
                                    System.out.println("Error al actualizar campo 'informadoPorCorreo'.");
                                    logger.error("Error al actualizar campo 'informadoPorCorreo' - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
                                }
                            } catch (MessagingException e) {
                                System.out.println("Error al enviar correo en 'sendMailRiesgosPlanes10Dias': " + e.getMessage());
                                logger.error("Error al enviar correo en 'sendMailRiesgosPlanes10Dias': " + e.getMessage());
                            }
                        } else{
                            System.out.println("No se envio el correo por que el campo 'CorreoCargo' es nulo o está vacío.");
                            logger.warn("No se envio el correo por que el campo 'CorreoCargo' es nulo o está vacío.");
                        }
                    } else{
                        System.out.println("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
                        logger.warn("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
                    }
                }
            }
        } else {
            System.out.println("No hay planes a vencer en los próximos 10 días.");
            logger.info("No hay planes a vencer en los próximos 10 días.");
        }
    }

    @Transactional
    public void sendMailRiesgosPlanesVencidos(){
        List<Object[]> results = matrizRiesgoRepository.planesVencidos();
        if (results != null) {
            for (Object[] result : results) {
                if (("NO").equals(result[7])) { // Envio solo para los que no esten informados
                    if (result[6] != null && !((String) result[6]).trim().isEmpty()) { // Envio a solo a los que tengan cargo registrado en el Plan
                        // arma destinatario
                        String destinatario = "";
                        if(result[6].toString().equals("Supervisor Comercial") || result[6].toString().equals("Supervisor de Servicio Post Venta")){
                            System.out.println("cargo:  " + result[6].toString());
                            destinatario = matrizRiesgoRepository.getCorreoByCargo("Gerente de Negocio Adquirente");
                        } else{
                            destinatario = result[8].toString();
                        }

                        if (destinatario != null && !destinatario.trim().isEmpty()) {
                            try {
                                String htmlContent = htmlTemplatePlanesVencidos(result[3].toString(), result[4].toString());
                                sendHtmlMessage(destinatario, "NOTIFICACIÓN PLAN DE ACCIÓN VENCIDO", htmlContent);
                                System.out.println("Correo enviado");
                                logger.info("Correo enviado - ID Riesgo " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());

                                int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
                                if (updateCount == 1) {
                                    System.out.println("Campo 'informadoPorCorreo' actualizado correctamente.");
                                } else {
                                    System.out.println("Error al actualizar campo 'informadoPorCorreo'.");
                                    logger.error("Error al actualizar campo 'informadoPorCorreo' - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
                                }
                            } catch (MessagingException e) {
                                System.out.println("Error al enviar correo en 'sendMailRiesgosPlanesVencidos': " + e);
                                logger.error("Error al enviar correo en 'sendMailRiesgosPlanesVencidos': " + e.getMessage());
                            }
                        } else{
                            System.out.println("No se envio el correo por que el campo 'CorreoCargo' es nulo o está vacío.");
                            logger.warn("No se envio el correo por que el campo 'CorreoCargo' es nulo o está vacío.");
                        }
                    } else{
                        System.out.println("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
                        logger.warn("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
                    }
                }
            }
        } else {
            System.out.println("No hay planes vencidos.");
            logger.info("No hay planes vencidos.");
        }
    }

    @Transactional
    public void sendMailRiesgosRecurrentes(){
        try {
            matrizRiesgoRepository.insertaVerificaRecurrenciasRiesgo();
            List<Object[]> results = matrizRiesgoRepository.getRecurrenciasRiesgo();

            if (results != null) {
                for (Object[] result : results) {
                    try {
                        String htmlContent = htmlTemplateRecurrenciasRiesgos(result);
                        sendHtmlMessage(mailCc, "ALERTA EVENTO RECURRENTE", htmlContent);
                        System.out.println("Correo enviado");
                        logger.info("Correo enviado - ID recurrencia : " + result[0].toString() + "ID Riesgo: " + result[1].toString() + ", Evento1: "+ result[3].toString() + ", Evento2: " + result[5].toString() + ", Evento3: " + result[7].toString());

                        int updateCount = matrizRiesgoRepository.updateRecurrenciaRiesgoInformado(Long.parseLong(result[0].toString()));
                        if (updateCount == 1) {
                            System.out.println("Campo 'correEnviado' actualizado correctamente.");
                        } else {
                            System.out.println("Error al actualizar campo 'correEnviado'.");
                            logger.error("Error al actualizar campo 'correEnviado' - ID recurrencia : " + result[0].toString() + "ID Riesgo: " + result[1].toString() + ", Evento1: "+ result[3].toString() + ", Evento2: " + result[5].toString() + ", Evento3: " + result[7].toString());
                        }
                    } catch (MessagingException e) {
                        System.out.println("Error al enviar correo en 'sendMailRiesgosRecurrentes': " + e);
                        logger.error("Error al enviar correo en 'sendMailRiesgosRecurrentes': " + e);
                    }
                }
            } else {
                System.out.println("No hay recurrencias en Riesgos.");
                logger.info("No hay recurrencias en Riesgos");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila completa de errores
            System.err.println("Error en insertaVerificaRecurrenciasRiesgo: " + e);
            logger.error("Error en insertaVerificaRecurrenciasRiesgo: " + e);
        }
    }

    public String htmlTemplatePlanesAVencer(String descripcion, String fechaImpl, String dias) {
        if (descripcion.endsWith(".")) {
            descripcion = descripcion.substring(0, descripcion.length() - 1);
        }
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: auto;\n" +
                "            background: #f7f7f7;\n" +
                "            padding: 20px;\n" +
                "            border: 1px solid #ccc;\n" +
                "            border-radius: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <p>Estimados,</p>\n" +
                "        <p>Le informamos que el Plan de Acción: <i>'" + descripcion + "'</i>, con fecha de implementación estaba programada para el: <i>" + fechaImpl + " </i> se encuentra a " + dias + " dias de vencer.</p>\n" +
                "        <p>Le solicitamos tomar las medidas necesarias para revisar y completar las acciones pendientes comprometidas para la fecha.</p>\n" +
                "        <p>Quedamos a su disposición para cualquier consulta adicional</p>\n" +
                "        <p>Unidad de Riesgo Operativo.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public String htmlTemplatePlanesVencidos(String descripcion, String fechaImpl) {
        if (descripcion.endsWith(".")) {
            descripcion = descripcion.substring(0, descripcion.length() - 1);
        }
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: auto;\n" +
                "            background: #f7f7f7;\n" +
                "            padding: 20px;\n" +
                "            border: 1px solid #ccc;\n" +
                "            border-radius: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <p>Estimados,</p>\n" +
                "        <p>Le informamos que el Plan de Acción: <i>'" + descripcion + "'</i>, cuya fecha de implementación estaba programada para el: <i>" + fechaImpl + " </i> se encuentra vencido.</p>\n" +
                "        <p>Es importante que se tomen las medidas necesarias para revisar las tareas pendientes y completar la implementación del plan. Les solicitamos priorizar esta situación para evitar posibles impactos en nuestras operaciones.</p>\n" +
                "        <p>Quedamos a su disposición para cualquier consulta adicional.</p>\n" +
                "        <p>Unidad de Riesgo Operativo.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public String htmlTemplateRecurrenciasRiesgos(Object[] result) {
        String riesgo1 = result[4] != null && !((String) result[4]).trim().isEmpty() ? result[4].toString() : (result[3] != null ? result[3].toString() : "");
        String riesgo2 = result[6] != null && !((String) result[6]).trim().isEmpty() ? result[6].toString() : (result[5] != null ? result[5].toString() : "");
        String riesgo3 = result[8] != null && !((String) result[8]).trim().isEmpty() ? result[8].toString() : (result[7] != null ? result[7].toString() : "");

        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: auto;\n" +
                "            background: #f7f7f7;\n" +
                "            padding: 20px;\n" +
                "            border: 1px solid #ccc;\n" +
                "            border-radius: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <p>Se informa que a la fecha el riesgo: <i>'"  + (result[2] != null && !result[2].toString().trim().isEmpty()? result[2].toString() : result[1].toString()) + "'</i>, se materializó en 3 eventos, favor validar su recurrencia:</p>\n" +
                "        <ul>\n" +
                "           <li>" + riesgo1 + "</li>\n" +
                "           <li>" + riesgo2 + "</li>\n" +
                "           <li>" + riesgo3 + "</li>\n" +
                "        </ul>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }



}