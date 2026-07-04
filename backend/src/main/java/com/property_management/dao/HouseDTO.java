package com.property_management.dao;

import lombok.Data;

@Data
public class HouseDTO extends House {
    private String buildingName;
    private String communityName;
    private String communityAddress;
}
