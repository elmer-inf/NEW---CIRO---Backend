package atc.riesgos.util;

public interface JPQL {
    // 2.1. Evento de Riesgo Operativo A

    String riesgoOperativoA = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "'307' as tipo_entidad ,\n" +
            "regexp_replace(e.eve_descripcion,  E'[\\\\n\\\\r\\\\u2028]+', '') as eve_descripcion,\n" +
            "(select a.des_nombre from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_factor_riesgo_id ) as eve_factor_riesgo_id,\n" +
            "(select a.des_nombre from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_cargo_id ) as eve_cargo_id,\n" +
            "(select a.des_nombre from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_area_id ) as eve_area_id,\n" +
            "(select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_clave = e.eve_tipo_evento ) as categoria,\n" +
            "case when COALESCE(ROUND(CAST(e.eve_monto_perdida AS numeric),2),0) = 0 then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_monto_perdida AS numeric),2),0) as varchar(10)) end as perdida_riesgo_operativo_valor_contable,\n" +
            "case when (e.eve_perdida_mercado is null or e.eve_perdida_mercado = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_perdida_mercado AS numeric),2),0) as varchar(10)) end as perdida_riesgo_operativo_valor_mercado,\n" +
            "case when (e.eve_gasto_asociado is null or e.eve_gasto_asociado = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_gasto_asociado AS numeric),2),0) as varchar(10)) end as gasto_asociado_a_perdida,\n" +
            "case when (e.eve_monto_recuperado is null or e.eve_monto_recuperado = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_monto_recuperado AS numeric),2),0) as varchar(10)) end as monto_total_recuperado,\n" +
            "case when (e.eve_monto_recuperado_seguro is null or e.eve_monto_recuperado_seguro = 0) then '0.00' else cast(COALESCE(ROUND(CAST(e.eve_monto_recuperado_seguro AS numeric),2),0) as varchar(10)) end as monto_total_recuperado_por_cobertura_de_seguro,\n" +
            "case when e.eve_recuperacion_activo_id is null then '3' else (select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_recuperacion_activo_id) end as eve_recuperacion_activo_id,\n" +
            "'2' as relacion_riesgo_credito,\n" +
            "case when e.eve_evento_critico = 'Crítico' then 1 when e.eve_evento_critico = 'No crítico' then 2  end as eve_evento_critico,\n" +
            "case when e.eve_evento_critico = 'Crítico' then e.eve_detalle_evento_critico else '' end  as eve_detalle_evento_critico,\n" +
            "case \n" +
            "\twhen (select a.des_clave from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) = 'BOB'\n" +
            "\t\tthen case when coalesce(e.eve_monto_perdida,0) <> 0 then coalesce(e.eve_monto_perdida,0) || (select a.des_clave from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) else '0.00' end\n" +
            "\twhen (select a.des_clave from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) = 'USD'\n" +
            "\t\tthen case when coalesce(e.eve_monto_perdida,0) <> 0  then (coalesce(e.eve_monto_perdida,0) * (select round(cast(ttd.des_nombre as numeric),2) tasa_cambio from riesgos.tbl_tabla_descripcion ttd where des_tabla_id = 14 FETCH FIRST 1 ROWS only)) || (select a.des_clave from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_moneda_id) || ' (' || COALESCE(e.eve_tasa_cambio_id,'') || ')' else '0.00' end \n" +
            "\telse '0.00'\n" +
            "end as moneda_montos_evento,\n" +
            "e.eve_fecha_desc as eve_fecha_desc,\n" +
            "to_char(e.eve_hora_desc, 'HH24:mm') as eve_hora_desc,\n" +
            "e.eve_fecha_ini as eve_fecha_ini,\n" +
            "to_char(e.eve_hora_ini, 'HH24:mm') as eve_hora_ini,\n" +
            "e.eve_fecha_fin as eve_fecha_fin,\n" +
            "to_char(e.eve_hora_fin, 'HH24:mm') as eve_hora_fin,\n" +
            "'someone' as elaborador,\n" +
            "'someone' as revisor,\n" +
            "'someone' as aprobador,\n" +
            "case\n" +
            "when e.eve_estado_evento ='Investigación' then 1\n" +
            "when e.eve_estado_evento ='Seguimiento' then 2\n" +
            "when e.eve_estado_evento ='Solución' then 3\n" +
            "end as eve_estado_evento,\n" +
            "e.eve_detalle_estado as eve_detalle_estado,\n" +
            "'' as codigo_envio_relacionado,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e\n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";

    // 2.2.  Cuentas Contables (Revisar con el solicitante) B

    String cuentasContablesB = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "'' as cuenta_contable,\n" +
            "'' as fecha_registro_cuenta_contable,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e\n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";

    //2.3. Tipo de evento
    String tipoEventoC = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_clase_evento_id) as tipo_evento,\n" +
            "case \n" +
            "when (select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_clase_evento_id) like '__99' then e.eve_otros else '' end as descripcion_tipo_evento,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc ";// +
    //"FETCH FIRST 1 ROWS ONLY";

    // 2.4. Punto de Atención Financiera (PAF) D

    String puntosAtencionD = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select ttd.des_clave from riesgos.tbl_tabla_descripcion ttd where des_tabla_id = 2 and des_id = e.eve_ciudad_id) as codigo_paf,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";

    // 2.5. Canal E

    String canalE = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "trim((select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_canal_asfi_id)) as canal,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";


    //-2.6. Proceso F

    String procesoF = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_proceso_id) as proceso,\n" +
            "case when eve_evento_critico = 'Crítico' then 1 when e.eve_evento_critico  = 'No crítico' then 2 end proceso_critico,\n" +
            "e.eve_detalle_evento_critico,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";

    // 2.7. Operación G
    String operacionG = "select \n" +
            "--e.eve_id as eve_id,\n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "--e.eve_operacion_id,\n" +
            "(select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_operacion_id),\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";

    // 2.8. Lugar H

    String lugarH = "select \n" +
            "--e.eve_id as eve_id,\n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "--e.eve_ciudad_id ,\n" +
            "(select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_ciudad_id),\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";

    // 2.9 Linea de negocio I

    String lineaNegocioI = "select \n" +
            "'ATATC' as codigo,\n" +
            "'2021-12-31' as fecha_corte,\n" +
            "e.eve_codigo as eve_codigo,\n" +
            "(select a.des_codigo_asfi from riesgos.tbl_tabla_descripcion a where a.des_id = e.eve_linea_asfi_id),\n" +
            "e.eve_linea_negocio ,\n" +
            "'' as tipo_envio\n" +
            "from riesgos.tbl_evento_riesgo e \n" +
            "where (e.eve_estado_evento = 'Seguimiento' or e.eve_estado_evento = 'Solución') and lower(e.eve_estado_reportado) = 'reportado'\n" +
            "order by e.eve_id asc";
}
