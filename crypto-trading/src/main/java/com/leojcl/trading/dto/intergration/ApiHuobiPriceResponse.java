package com.leojcl.trading.dto.intergration;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiHuobiPriceResponse {

    private List<ApiHuobiTickerItem> data;

}
