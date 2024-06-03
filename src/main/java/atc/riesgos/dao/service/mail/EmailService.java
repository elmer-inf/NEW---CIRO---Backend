//package atc.riesgos.dao.service.mail;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.transaction.Transactional;
//import atc.riesgos.model.repository.MatrizRiesgoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    MatrizRiesgoRepository matrizRiesgoRepository;
//
//    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
//
//    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//        helper.setFrom("noreply@atc.com.bo");
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlContent, true);
//
//        mailSender.send(message);
//    }
//
//    @Transactional
//    public void sendMailRiesgosPlanes5Dias(){
//        List<Object[]> results = matrizRiesgoRepository.planesAVencer5Dias();
//        if (results != null) {
//            for (Object[] result : results) {
//                // Envio solo para los que no esten informados
//                if (("NO").equals(result[7])) {
//                    // Envio a solo a los que tengan cargo registrado en el Plan
//                    if (result[6] != null && !((String) result[5]).trim().isEmpty()) {
//                        // armar correo
//                        String destinatario = "";
//                        if( result[6].toString().equals("Subgerente Adjunto de Punto de Venta y Canales")){
//                            System.out.println("Cargo a Subgerente Adjunto de Punto de Venta y Canales");
//                            destinatario = "SistemasdeInformacion@atc.com.bo";
//                        }
//
//                        try {
//                            String htmlContent = htmlTemplatePlanesAVencer(result[3].toString(), result[4].toString(), "cinco");
//                            sendHtmlMessage(destinatario, "Plan de riesgo a cinco dias de vencer", htmlContent);
//                            System.out.println("Correo enviado");
//                            logger.info("Correo enviado - ID Riesgo " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//
//                            // Corrige el casting para pasar los parámetros adecuados
//                            int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
//                            if (updateCount == 1) {
//                                System.out.println("Campo 'informadoPorCorreo' actualizado correctamente.");
//                                logger.info("Campo 'informadoPorCorreo' actualizado correctamente. - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//                            } else if(updateCount > 1){
//                                System.out.println("Se actualizó mas de un control.");
//                                logger.error("Se actualizó mas de un control.");
//                            } else if(updateCount == 0){
//                                System.out.println("Error al actualizar campo 'informadoPorCorreo'.");
//                                logger.info("Error al actualizar campo 'informadoPorCorreo' - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//                            }
//                        } catch (MessagingException e) {
//                            System.out.println("Error al enviar correo en 'sendMailRiesgosPlanes5Dias': " + e.getMessage());
//                            logger.error("Error al enviar correo en 'sendMailRiesgosPlanes5Dias': " + e.getMessage());
//                        }
//                    } else{
//                        System.out.println("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
//                        logger.warn("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
//                    }
//                } else{
//                    System.out.println("El Plan ya fue informado");
//                }
//            }
//        } else {
//            System.out.println("No hay planes a vencer en los próximos 5 días.");
//            logger.info("No hay planes a vencer en los próximos 5 días.");
//        }
//    }
//
//    @Transactional
//    public void sendMailRiesgosPlanes10Dias(){
//        List<Object[]> results = matrizRiesgoRepository.planesAVencer10Dias();
//        if (results != null) {
//            for (Object[] result : results) {
//                // Envio solo para los que no esten informados
//                if (("NO").equals(result[7])) {
//                    // Envio a solo a los que tengan cargo registrado en el Plan
//                    if (result[6] != null && !((String) result[5]).trim().isEmpty()) {
//                        // armar correo
//                        String destinatario = "";
//                        if( result[6].toString().equals("Subgerente Adjunto de Punto de Venta y Canales")){
//                            System.out.println("Cargo a Subgerente Adjunto de Punto de Venta y Canales");
//                            destinatario = "SistemasdeInformacion@atc.com.bo";
//                        }
//                        System.out.println("cargo 10: " + result[6].toString());
//                        try {
//
//                            String htmlContent = htmlTemplatePlanesAVencer(result[3].toString(), result[4].toString(), "diez");
//                            sendHtmlMessage(destinatario, "Plan de riesgo a diez dias de vencer", htmlContent);
//                            System.out.println("Correo enviado");
//                            logger.info("Correo enviado - ID Riesgo " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//
//                            // Corrige el casting para pasar los parámetros adecuados
//                            int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
//                            if (updateCount == 1) {
//                                System.out.println("Campo 'informadoPorCorreo' actualizado correctamente.");
//                                logger.info("Campo 'informadoPorCorreo' actualizado correctamente. - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//                            } else if(updateCount > 1){
//                                System.out.println("Se actualizó mas de un control.");
//                                logger.error("Se actualizó mas de un control.");
//                            } else if(updateCount == 0){
//                                System.out.println("Error al actualizar campo 'informadoPorCorreo'.");
//                                logger.info("Error al actualizar campo 'informadoPorCorreo' - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//                            }
//                        } catch (MessagingException e) {
//                            System.out.println("Error al enviar correo en 'sendMailRiesgosPlanes10Dias': " + e.getMessage());
//                            logger.error("Error al enviar correo en 'sendMailRiesgosPlanes10Dias': " + e.getMessage());
//                        }
//                    } else{
//                        System.out.println("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
//                        logger.warn("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
//                    }
//                } else{
//                    System.out.println("El Plan ya fue informado");
//                }
//            }
//        } else {
//            System.out.println("No hay planes a vencer en los próximos 5 días.");
//            logger.info("No hay planes a vencer en los próximos 5 días.");
//        }
//    }
//
//    @Transactional
//    public void sendMailRiesgosPlanesVencidos(){
//        List<Object[]> results = matrizRiesgoRepository.planesVencidos();
//        if (results != null) {
//            for (Object[] result : results) {
//                // Envio solo para los que no esten informados
//                if (("NO").equals(result[7])) {
//                    // Envio a solo a los que tengan cargo registrado en el Plan
//                    if (result[6] != null && !((String) result[5]).trim().isEmpty()) {
//                        // armar correo
//                        String destinatario = "";
//                        if( result[6].toString().equals("Subgerente Adjunto de Punto de Venta y Canales")){
//                            System.out.println("Cargo a Subgerente Adjunto de Punto de Venta y Canales");
//                            destinatario = "SistemasdeInformacion@atc.com.bo";
//
//
//                            try {
//                                String htmlContent = htmlTemplatePlanesVencidos(result[3].toString(), result[4].toString());
//                                sendHtmlMessage(destinatario, "Plan de riesgo vencido", htmlContent);
//                                System.out.println("Correo enviado");
//                                logger.info("Correo enviado - ID Riesgo " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//
//                                // Corrige el casting para pasar los parámetros adecuados
//                                int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
//                                if (updateCount == 1) {
//                                    System.out.println("Campo 'informadoPorCorreo' actualizado correctamente.");
//                                    logger.info("Campo 'informadoPorCorreo' actualizado correctamente. - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//                                } else if(updateCount > 1){
//                                    System.out.println("Se actualizó mas de un control.");
//                                    logger.error("Se actualizó mas de un control.");
//                                } else if(updateCount == 0){
//                                    System.out.println("Error al actualizar campo 'informadoPorCorreo'.");
//                                    logger.info("Error al actualizar campo 'informadoPorCorreo' - " + result[0].toString() + ", Codigo Riesgo: "+ result[2].toString() + ", Plan num: " + result[1].toString());
//                                }
//                            } catch (MessagingException e) {
//                                System.out.println("Error al enviar correo en 'sendMailRiesgosPlanesVencidos': " + e);
//                                logger.error("Error al enviar correo en 'sendMailRiesgosPlanesVencidos': " + e.getMessage());
//                            }
//                        }
//
//
//                    } else{
//                        System.out.println("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
//                        logger.warn("No se envio el correo por que el campo 'Cargo' es nulo o está vacío.");
//                    }
//                } else{
//                    System.out.println("El Plan ya fue informado");
//                }
//            }
//        } else {
//            System.out.println("No hay planes a vencer en los próximos 5 días.");
//            logger.info("No hay planes a vencer en los próximos 5 días.");
//        }
//    }
//
//    public String htmlTemplatePlanesAVencer(String descripcion, String fechaImpl, String dias) {
//        if (descripcion.endsWith(".")) {
//            descripcion = descripcion.substring(0, descripcion.length() - 1);
//        }
//        return "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <style>\n" +
//                "        body {\n" +
//                "            font-family: Arial, sans-serif;\n" +
//                "            line-height: 1.6;\n" +
//                "            margin: 0;\n" +
//                "            padding: 20px;\n" +
//                "            color: #333;\n" +
//                "        }\n" +
//                "        .container {\n" +
//                "            max-width: 600px;\n" +
//                "            margin: auto;\n" +
//                "            background: #f7f7f7;\n" +
//                "            padding: 20px;\n" +
//                "            border: 1px solid #ccc;\n" +
//                "            border-radius: 10px;\n" +
//                "        }\n" +
//                "    </style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <div class=\"container\">\n" +
//                "        <p>Estimados,</p>\n" +
//                "        <p>Se informa que el plan de acción: <i>'" + descripcion + "'</i>, con fecha de implementación: <i>" + fechaImpl + " </i> está a " + dias + " dias de vencer.</p>\n" +
//                "        <p>Por favor, tomen las medidas necesarias para revisar y dar solución a la brevedad posible.</p>\n" +
//                "        <p>Saludos cordiales,</p>\n" +
//                "        <p>Unidad de Riesgos</p>\n" +
//                "    </div>\n" +
//                "</body>\n" +
//                "</html>\n";
//    }
//
//    public String htmlTemplatePlanesVencidos(String descripcion, String fechaImpl) {
//        if (descripcion.endsWith(".")) {
//            descripcion = descripcion.substring(0, descripcion.length() - 1);
//        }
//        return "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <style>\n" +
//                "        body {\n" +
//                "            font-family: Arial, sans-serif;\n" +
//                "            line-height: 1.6;\n" +
//                "            margin: 0;\n" +
//                "            padding: 20px;\n" +
//                "            color: #333;\n" +
//                "        }\n" +
//                "        .container {\n" +
//                "            max-width: 600px;\n" +
//                "            margin: auto;\n" +
//                "            background: #f7f7f7;\n" +
//                "            padding: 20px;\n" +
//                "            border: 1px solid #ccc;\n" +
//                "            border-radius: 10px;\n" +
//                "        }\n" +
//                "    </style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <div class=\"container\">\n" +
//                "        <p>Estimados,</p>\n" +
//                "        <p>Se informa que el plan de acción: <i>'" + descripcion + "'</i>, con fecha de implementación: <i>" + fechaImpl + " </i> ha vencido.</p>\n" +
//                "        <p>Por favor, tomen las medidas necesarias para revisar y dar solución.</p>\n" +
//                "        <p>Saludos cordiales,</p>\n" +
//                "        <p>Unidad de Riesgos</p>\n" +
//                "    </div>\n" +
//                "</body>\n" +
//                "</html>\n";
//    }
//
//
//
//
//}