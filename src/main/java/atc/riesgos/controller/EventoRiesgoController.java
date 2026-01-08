package atc.riesgos.controller;

import atc.riesgos.dao.service.EventoRiesgoService;
import atc.riesgos.auth.Controller;
import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.EventoRiesgo.EventoRecurrente.EventoRiesgoFilePutDTOrecurrente;
import atc.riesgos.model.dto.EventoRiesgo.EventoRecurrente.EventoRiesgoPutDTOrecurrente;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import atc.riesgos.model.dto.EventoRiesgo.*;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/eventoRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class EventoRiesgoController extends Controller {

    @Autowired
    EventoRiesgoService eventoRiesgoService;

    private static final Logger log = LoggerFactory.getLogger(EventoRiesgoController.class);

    @PostMapping("/registrar")
    public ResponseEntity<EventoRiesgo> save(@Valid @RequestBody EventoRiesgoPostDTO data) {
        return eventoRiesgoService.create(data);
    }

    @PostMapping("/registrarwithfiles")
    public ResponseEntity<EventoRiesgo> saveWithFiles(@ModelAttribute EventoRiesgoFilePostDTO data) {
        EventoRiesgoPostDTO dataDTO = Log.jsonToObject(data.getEventoRiesgoPostDTO(), EventoRiesgoPostDTO.class);
        return eventoRiesgoService.createWithFiles(dataDTO, data.getFile());
    }

    /* otro
    @PutMapping(value = "/editarwithfiles/{id}")
    public ResponseEntity<EventoRiesgoGetDTO> updateById(@PathVariable("id") Long id, @ModelAttribute EventoRiesgoFilePutDTO data) {
        System.out.println("data.getFiles() " + data.getFile());
        EventoRiesgoPutDTO dataDTO = Log.jsonToObject(data.getEventoRiesgoPutDTO(),EventoRiesgoPutDTO.class);

        System.out.println("DAAATTAAA desseria: " + Log.toJSON(dataDTO));
        return eventoRiesgoService.updateById(id, dataDTO, data.getFile() , data.getFilesToDelete());
    }*/

    /*@PutMapping(value = "/editarwithfiles/{id}")
    public ResponseEntity<EventoRiesgoGetDTO> updateByIdWithFiles(@PathVariable("id") Long id, @ModelAttribute EventoRiesgoFilePutDTO data) {
        EventoRiesgoPutDTO dto = Log.jsonToObject(data.getEventoRiesgoPutDTO(), EventoRiesgoPutDTO.class);

        return eventoRiesgoService.updateByIdWithFiles(id, dto, data.getFile(), data.getFilesToDelete()
        );
    }*/

    @PutMapping(value = "/editarwithfiles/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateByIdWithFiles(
            @PathVariable("id") Long id,
            @RequestPart("eventoRiesgoPutDTO") String eventoRiesgoPutDTOJson,
            @RequestPart(value = "filesToDelete", required = false) String filesToDeleteJsonOrCsv,
            @RequestPart(value = "file", required = false) MultipartFile[] files
    ) {
        try {
            log.info("PUT editarwithfiles id={} jsonLen={} filesToDeleteRaw={} filesCount={}",
                    id,
                    eventoRiesgoPutDTOJson == null ? 0 : eventoRiesgoPutDTOJson.length(),
                    filesToDeleteJsonOrCsv,
                    files == null ? 0 : files.length
            );

            EventoRiesgoPutDTO dto = Log.jsonToObject(eventoRiesgoPutDTOJson, EventoRiesgoPutDTO.class);

            // Si te llega JSON tipo "[207,208]" o CSV "207,208" tu parseIds actual lo soporta.
            return eventoRiesgoService.updateByIdWithFiles(id, dto, files, filesToDeleteJsonOrCsv);

        } catch (Exception e) {
            log.error("Error updateByIdWithFiles id={}", id, e);
            Map<String, Object> body = new HashMap<>();
            body.put("code", "ERROR");
            body.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(body);
        }
    }




    @PutMapping("/editar/{id}")
    public ResponseEntity<EventoRiesgoGetDTO> updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody EventoRiesgoPutDTO data) {
        return eventoRiesgoService.updateById(id, data);
    }

    @PutMapping("/evaluaEvento/{id}")
    public ResponseEntity<EventoRiesgo> evaluaEvento(@PathVariable(value = "id") Long id, @Valid @RequestBody EventoRiesgoPutDTOevaluacion data) {
        return eventoRiesgoService.evaluaEvento(id, data);
    }

    @GetMapping("/generaCodigo/{id}")
    public String generaCodigo(@PathVariable(value = "id") Long id) {
        return eventoRiesgoService.generaCodigo(id);
    }

    @GetMapping("/mostrar/{id}")
    public EventoRiesgoGetDTO getEventoByById(@PathVariable(value = "id") Long id) {
        return eventoRiesgoService.findEventoByID(id);
    }

    @GetMapping("/listar")
    public List<EventoRiesgo> listEventoRiesgo() {
        return eventoRiesgoService.listEventoRiesgo();
    }

    @PutMapping("/eliminar/{id}")
    public EventoRiesgo deleteById(@PathVariable(value = "id") Long id) {
        return eventoRiesgoService.deleteByIdEvento(id);
    }


    @GetMapping({"/{page}/{size}/{order}", "/{page}/{size}"})
    public Object filterBy(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size,
            @PathVariable(value = "order", required = false) String order,
            HttpServletRequest request) {

        return createHQL(EventoRiesgo.class).order(order)
                .map(request).paging(page, size);
    }


    // Eventos recurrentes - Factor persona

    @GetMapping({"/eventosrecurrentes/{page}/{size}/{order}", "/eventosrecurrentes/{page}/{size}"})
    public ResponseEntity<?> filterBy(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size,
            @PathVariable(value = "order", required = false) String order,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "fechaDesc", required = false) String fechaDescStr,
            @RequestParam(value = "fechaFin", required = false) String fechaFinStr,
            @RequestParam(value = "tipoEvento", required = false) String tipoEvento,
            @RequestParam(value = "procesoId.nombre", required = false) String procesoNombre,
            @RequestParam(value = "estadoRegistro", required = false) String estadoRegistro,
            @RequestParam(value = "estadoEvento", required = false) String estadoEvento) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDesc = fechaDescStr != null ? sdf.parse(fechaDescStr) : null;
        Date fechaFin = fechaFinStr != null ? sdf.parse(fechaFinStr) : null;

        List<EventoRiesgo> allEvents = eventoRiesgoService.listEventoRiesgoRecurrentes();

        Stream<EventoRiesgo> filteredEvents = allEvents.stream()
                .filter(e -> id == null || e.getId().equals(id))
                .filter(e -> codigo == null || (e.getCodigo() != null && e.getCodigo().toLowerCase().contains(codigo.toLowerCase())))
                .filter(e -> fechaDesc == null || (e.getFechaDesc() != null && e.getFechaDesc().equals(fechaDesc)))
                .filter(e -> fechaFin == null || (e.getFechaFin() != null && e.getFechaFin().equals(fechaFin)))
                .filter(e -> tipoEvento == null || (e.getTipoEvento() != null && e.getTipoEvento().toLowerCase().contains(tipoEvento.toLowerCase())))
                .filter(e -> procesoNombre == null || (Optional.ofNullable(e.getProcesoId()).map(TablaDescripcion::getNombre).orElse("").toLowerCase().contains(procesoNombre.toLowerCase())))
                .filter(e -> estadoRegistro == null || (e.getEstadoRegistro() != null && e.getEstadoRegistro().toLowerCase().contains(estadoRegistro.toLowerCase())))
                .filter(e -> estadoEvento == null || (e.getEstadoEvento() != null && e.getEstadoEvento().toLowerCase().contains(estadoEvento.toLowerCase())));

        List<EventoRiesgo> sortedFilteredEvents = (order != null && order.contains("id:desc")) ?
                filteredEvents.sorted(Comparator.comparing(EventoRiesgo::getId).reversed()).collect(Collectors.toList()) :
                filteredEvents.sorted(Comparator.comparing(EventoRiesgo::getId)).collect(Collectors.toList());

        int totalRecords = sortedFilteredEvents.size();
        int start = Math.min(page * size, totalRecords);
        int end = Math.min((page + 1) * size, totalRecords);
        List<EventoRiesgo> paginatedList = sortedFilteredEvents.subList(start, end);

        Map<String, Object> paging = new HashMap<>();
        paging.put("size", size);
        paging.put("page", page);
        paging.put("pages", (int) Math.ceil((double) totalRecords / size));
        paging.put("total", totalRecords);

        Map<String, Object> response = new HashMap<>();
        response.put("data", paginatedList);
        response.put("paging", paging);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/editarrecurrentewithfiles/{id}")
    public ResponseEntity<EventoRiesgoGetDTO> updateEventoRecurrenteById(@ModelAttribute EventoRiesgoFilePutDTOrecurrente data, @PathVariable("id") Long id) {

        EventoRiesgoPutDTOrecurrente dataDTO = Log.jsonToObject(data.getEventoRiesgoPutDTOrecurrente(), EventoRiesgoPutDTOrecurrente.class);
        return eventoRiesgoService.updateEventoRecurrenteWithFiles(id, dataDTO, data.getFile());
    }

}
