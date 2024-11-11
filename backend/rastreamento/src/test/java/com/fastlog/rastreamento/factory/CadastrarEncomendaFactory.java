package com.fastlog.rastreamento.factory;

import com.fastlog.rastreamento.presentation.dto.request.CadastrarEncomendaDto;

public class CadastrarEncomendaFactory {

    public static CadastrarEncomendaDto cadastrarEncomendaDtoValido() {
        return new CadastrarEncomendaDto(
                "Asgard ddr4 ram memory ddr4 8gb 16GBx2 32GB 3200MHz 3600MHZ ram ddr4 Feryr Series metal heat sink for PC",
                "China",
                "Prédio empresarial da JazzTech,Pinheiros;Avenida Rebouças;1368 Prédio empresarial da JazzTech,Sao Paulo,Sao Paulo,BR 05402-100",
                new CadastrarEncomendaDto.CadastrarStatusDto(
                        "Informações enviadas para análise da autoridade aduaneira/órgãos anuentes",
                        "Brazil",
                        "Sao Paulo - SP"
                )
        );
    }
}
