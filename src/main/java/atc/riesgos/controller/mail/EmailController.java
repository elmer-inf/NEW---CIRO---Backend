//package atc.riesgos.controller.mail;
//
//import atc.riesgos.dao.service.mail.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/v1/email")
//@CrossOrigin(origins = "*", maxAge = 20000)
//
//public class EmailController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @GetMapping("/sendmailriesgoplanesvencidos")
//    public ResponseEntity<String> sendMailRiesgoPlanesVencidos() {
//        emailService.sendMailRiesgosPlanesVencidos();
//        return ResponseEntity.ok("Correos enviados correctamente o no había correos para enviar.");
//    }
//
//}
