package atc.riesgos.auth.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuUI {

    private Long id;
    protected String name;
    protected String icon;

    @JsonProperty("to")
    protected String url;
    private Long parent;
    private String tipo;  // lectura | crear | editar
    private boolean deleted;

    @JsonProperty("_tag")
    protected String tag;
    private Long idHijo;
    private Object sistema;

    @JsonProperty("_children")
    private List<Menus> children;
}
