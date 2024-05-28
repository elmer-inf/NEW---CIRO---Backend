package atc.riesgos.dao.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTONotificaciones;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    MatrizRiesgoRepository matrizRiesgoRepository;

    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("noreply@atc.com.bo");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

//    private Long idRiesgo;
//    private int nroPlan;
//    private String codigo;
//    private String descripcion;
//    private String fechaImpl;
//    private String estado;
//    private String cargo;
//    private String informadoPorCorreo;

    @Transactional
    public void sendMailRiesgosPlanesVencidos(){
        List<Object[]> results = matrizRiesgoRepository.planesAVencer5Dias();
        if (results != null) {
            for (Object[] result : results) {
                // Envio solo para los que no esten informados
                if (("NO").equals(result[7])) {
                    // Envio a solo a los que tengan cargo registrado en el Plan
                    if (result[6] != null && !((String) result[5]).trim().isEmpty()) {
                        // armar correo
                        String destinatario = "";
                        if( result[6].toString().equals("Subgerente Adjunto de Punto de Venta y Canales")){
                            System.out.println("Cargo a Subgerente Adjunto de Punto de Venta y Canales");
                            destinatario = "SistemasdeInformacion@atc.com.bo";
                        }

                        try {
                            String htmlContent = buildHtmlTemplate(result[2].toString(), result[3].toString());
                            sendHtmlMessage(destinatario, "Plan de riesgo a 5 dias de vencer", htmlContent);
                            System.out.println("Correo enviado");

                            // Corrige el casting para pasar los parámetros adecuados
                            int updateCount = matrizRiesgoRepository.updatePlanRiesgoInformado(Long.parseLong(result[0].toString()), Integer.parseInt(result[1].toString()));
                            if (updateCount == 1) {
                                System.out.println("Registro actualizado correctamente.");
                            } else if(updateCount > 1){
                                System.out.println("Actualizo mas de un control");
                            } else if(updateCount == 0){
                                System.out.println("No actualizo ningun plan que deberia haber sido informado");
                            }

                        } catch (MessagingException e) {
                            System.out.println("Error en el envio de correo: " + e.getMessage());
                        }
                    } else{
                        System.out.println("El campo 'Cargo' es nulo o está vacío.");
                    }
                } else{
                    System.out.println("El Plan ya fue informado");
                }
            }
        } else {
            System.out.println("No hay planes a vencer en los próximos 5 días.");
        }
    }

    public String buildHtmlTemplate(String descripcion, String fechaImpl) {
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
                "        <p>Se informa que el plan de acción: <i>'" + descripcion + "'</i>, con fecha de implementación " + fechaImpl + " ha vencido.</p>\n" +
                "        <p>Por favor, tomen las medidas necesarias para revisar y actualizar este plan a la brevedad.</p>\n" +
                "        <p>Saludos cordiales,</p>\n" +
                "        <p>Unidad de Riesgos</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }




}