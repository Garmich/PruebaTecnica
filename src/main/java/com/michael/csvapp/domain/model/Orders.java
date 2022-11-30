package com.michael.csvapp.domain.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Orders {
    @CsvBindByName(column = "Region")
    private String region;

    @CsvBindByName(column = "Country")
    private String country;

    @CsvBindByName(column = "Item Type")
    private String itemType;

    @CsvBindByName(column = "Sales Channel")
    private String salesChannel;

    @CsvBindByName(column = "Order Priority")
    private String priority;

    @CsvDate(value = "M/d/yyyy")
    @CsvBindByName(column = "Order Date")
    private LocalDate orderDate;

    @CsvDate(value = "M/d/yyyy")
    @CsvBindByName(column = "Ship Date")
    private LocalDate shipDate;

    @CsvBindByName(column = "Units Sold")
    private int unitsSold;

    @CsvBindByName(column = "Unit Price")
    private float unitPrice;

    @CsvBindByName(column = "Unit Cost")
    private float unitCost;

    @CsvBindByName(column = "Total Revenue")
    private float totalRevenue;

    @CsvBindByName(column = "Total Cost")
    private float totalCost;

    @CsvBindByName(column = "Total Profit")
    private float totalProfit;

    @CsvBindByName(column = "Order Id")
    private int id;
}
