package atc.riesgos.util;

public interface JPQL {


    /*
    * El siguiente script obtiene los ID's de los reportes  que se deben genera
    *
    * NUEVO: La primera parte del script extrae los eventos que cuya fecha fin pertenecen al trimestre seleccionado
    * y estado en Solucion
    *
    * La segundo seccion del script extrae todos los eventos que se encuentran
      en seguimiento y no se cerraron ANTERIORES A la fecha inicio del trimiestre seleccionado.

    * la tercera seccion del script extrae los datos del los eventos de riesgo que pertencen al
      trimestre selccionado con estado solucionado y seguimiento
    * */

    String byIdEvento = "select cast(e.eve_id as varchar) \n" +
            "from riesgosbk.tbl_evento_riesgo e\n" +
            "where e.eve_estado_evento = 'Solución' and e.eve_fecha_fin between :fechaIniTrimestre and :fechaFinTrimestre\n" +
            "union \n" +
            "select cast(e.eve_id as varchar) \n" +
            "from riesgosbk.tbl_evento_riesgo e\n" +
            "where (e.eve_estado_evento = 'Seguimiento' and  e.eve_estado_evento <> 'Solución') and e.eve_fecha_desc < :fechaIniTrimestre \n" +
            "union \n" +
            "select cast(e.eve_id as varchar) \n" +
            "from riesgosbk.tbl_evento_riesgo e\n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and e.eve_fecha_desc between :fechaIniTrimestre and :fechaFinTrimestre\n" +
            "order by 1 asc";

    // 2.1. Evento de Riesgo Operativo A

    String riesgoOperativoA = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "'307' as tipo_entidad ,\n" +
            "regexp_replace(e.eve_descripcion,  E'[\\\\n\\\\r\\\\u2028]+', '') as eve_descripcion,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_factor_riesgo_id ) as eve_factor_riesgo_id,\n" +
            "coalesce((select a.des_nombre from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_cargo_id ),'') as eve_cargo_id,\n" +
            "(select a.des_nombre from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_area_id ) as eve_area_id,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_clave = e.eve_tipo_evento ) as categoria,\n" +
            "case when COALESCE(ROUND(CAST(e.eve_monto_perdida AS numeric),2),0) = 0 then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_monto_perdida AS numeric),2),0) as varchar(10)) end as perdida_riesgo_operativo_valor_contable,\n" +
            "case when (e.eve_perdida_mercado is null or e.eve_perdida_mercado = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_perdida_mercado AS numeric),2),0) as varchar(10)) end as perdida_riesgo_operativo_valor_mercado,\n" +
            "case when (e.eve_gasto_asociado is null or e.eve_gasto_asociado = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_gasto_asociado AS numeric),2),0) as varchar(10)) end as gasto_asociado_a_perdida,\n" +
            "case when (e.eve_monto_recuperado is null or e.eve_monto_recuperado = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_monto_recuperado AS numeric),2),0) as varchar(10)) end as monto_total_recuperado,\n" +
            "case when (e.eve_monto_recuperado_seguro is null or e.eve_monto_recuperado_seguro = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_monto_recuperado_seguro AS numeric),2),0) as varchar(10)) end as monto_total_recuperado_por_cobertura_de_seguro,\n" +
            "case when e.eve_recuperacion_activo_id is null then '3' else (select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_recuperacion_activo_id) end as eve_recuperacion_activo_id,\n" +
            "'2' as relacion_riesgo_credito,\n" +
            "case when e.eve_evento_critico = 'Crítico' then 1 when e.eve_evento_critico = 'No crítico' then 2  end as eve_evento_critico,\n" +
            "case when e.eve_evento_critico = 'Crítico' then e.eve_detalle_evento_critico else '' end  as eve_detalle_evento_critico,\n" +
            "case \n" +
            "when (select a.des_clave from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) = 'BOB'\n" +
            "then case when coalesce(e.eve_monto_perdida,0) <> 0 then coalesce(e.eve_monto_perdida,0) || (select a.des_clave from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) else '0.00' end\n" +
            "when (select a.des_clave from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) = 'USD'\n" +
            "then case when coalesce(e.eve_monto_perdida,0) <> 0  then (coalesce(e.eve_monto_perdida,0) * (select round(cast(ttd.des_nombre as numeric),2) tasa_cambio from riesgosbk.tbl_tabla_descripcion ttd where des_tabla_id = 14 FETCH FIRST 1 ROWS only)) || (select a.des_clave from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) || ' (' || COALESCE(e.eve_tasa_cambio_id,'') || ')' else '0.00' end \n" +
            "else '0.00'\n" +
            "end as moneda_montos_evento,\n" +
            "e.eve_fecha_desc as eve_fecha_desc,\n" +
            "to_char(e.eve_hora_desc, 'HH24:mm') as eve_hora_desc,\n" +
            "e.eve_fecha_ini as eve_fecha_ini,\n" +
            "to_char(e.eve_hora_ini, 'HH24:mm') as eve_hora_ini,\n" +
            "e.eve_fecha_fin as eve_fecha_fin,\n" +
            "to_char(e.eve_hora_fin, 'HH24:mm') as eve_hora_fin,\n" +
            "(select 'Nombre: ' || ttd.des_nombre || '; Cargo= ' || ttd.des_descripcion || ';' || ttd.des_campo_a from riesgosbk.tbl_tabla_descripcion ttd where des_tabla_id = 38 and ttd.des_campo_c = 'Elaborador') as elaborador,\n" +
            "(select 'Nombre: ' || ttd.des_nombre || '; Cargo= ' || ttd.des_descripcion || ';' || ttd.des_campo_a from riesgosbk.tbl_tabla_descripcion ttd where des_tabla_id = 38 and ttd.des_campo_c = 'Revisor') as revisor,\n" +
            "(select 'Nombre: ' || ttd.des_nombre || '; Cargo= ' || ttd.des_descripcion || ';' || ttd.des_campo_a from riesgosbk.tbl_tabla_descripcion ttd where des_tabla_id = 38 and ttd.des_campo_c = 'Aprobador') as aprobador,\n" +
            "case\n" +
            "when e.eve_estado_evento ='Investigación' then 1\n" +
            "when e.eve_estado_evento ='Seguimiento' then 2\n" +
            "when e.eve_estado_evento ='Solución' then 3\n" +
            "end as eve_estado_evento,\n" +
            "e.eve_detalle_estado as eve_detalle_estado,\n" +
            "'' as codigo_envio_relacionado,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e\n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";

    // 2.2.  Cuentas Contables (Revisar con el solicitante) B

    String cuentasContablesB = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "replace(replace((select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_cuenta_contable_id ),'-',''),'.','') as cuenta_contable,\n" +
            "e.eve_fecha_contable as fecha_registro_cuenta_contable,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e\n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_tipo_evento  = 'A') and  (e.eve_cuenta_contable_id is not null ) and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";

    //2.3. Tipo de evento
    String tipoEventoC = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_clase_evento_id) as tipo_evento,\n" +
            "case when (select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_clase_evento_id) like '__99' then e.eve_otros else '' end as descripcion_tipo_evento,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";

    // 2.4. Punto de Atención Financiera (PAF) D

    String puntosAtencionD = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select ttd.des_clave from riesgosbk.tbl_tabla_descripcion ttd where des_tabla_id = 2 and des_id = e.eve_ciudad_id) as codigo_paf,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";

    // 2.5. Canal E

    String canalE = "select" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "trim((select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_canal_asfi_id)) as canal,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";


    //-2.6. Proceso F
    String procesoF = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_procedimiento_id) as procedimiento,\n" +
            "case when eve_evento_critico = 'Crítico' then 1 when e.eve_evento_critico  = 'No crítico' then 2 end proceso_critico,\n" +
            "e.eve_detalle_evento_critico,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and e.eve_id in (:idEventos)\n" +
            "order by e.eve_id asc";

    // 2.7. Operación G
    String operacionG = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_operacion_id),\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";

    // 2.8. Lugar H

    String lugarH = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_ciudad_id),\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";

    // 2.9 Linea de negocio I

    String lineaNegocioI = "select \n" +
            "'ATATC' as codigo,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgosbk.tbl_tabla_descripcion a where a.des_id = e.eve_linea_asfi_id),\n" +
            "e.eve_linea_negocio ,\n" +
            "'' as tipo_envio\n" +
            "from riesgosbk.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and (e.eve_id in :idEventos)\n" +
            "order by e.eve_id asc";
}
