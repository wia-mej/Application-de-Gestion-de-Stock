package com.application.gestiondestock.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.util.StringUtils;
import org.slf4j.MDC;

public class CustomInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // Récupère le nom de l'entité (exemple : "utilisateur" dans "select utilisateur0_.")
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String idEntreprise = MDC.get("idEntreprise");
            
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("roles")
                    && StringUtils.hasLength(idEntreprise)) {
                
                // Modifie dynamiquement la requête pour ajouter la clause "where identreprise = ..."
                if (sql.contains("where")) {
                    sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
                }
            }
        }
        return sql; // Renvoie la requête modifiée ou telle quelle
    }
}