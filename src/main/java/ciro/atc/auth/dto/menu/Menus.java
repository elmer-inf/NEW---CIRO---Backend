package ciro.atc.auth.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menus {
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
}
