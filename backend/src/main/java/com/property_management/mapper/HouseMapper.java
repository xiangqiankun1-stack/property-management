package com.property_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.property_management.dao.House;
import com.property_management.dao.HouseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HouseMapper extends BaseMapper<House> {

    @Select("""
            SELECT
            h.id,
            h.house_number,
            h.floor,
            h.unit_number,
            h.area,
            h.house_type,
            h.status,

            b.id AS buildingId,
            b.building_name AS buildingName,

            c.id AS communityId,
            c.community_name AS communityName,
            c.community_address AS communityAddress

        FROM house h
        LEFT JOIN building b
        ON h.building_id = b.id
        LEFT JOIN community c
        ON b.community_id = c.id
        WHERE h.id=#{id}
            """)
    HouseDTO getDetailWithCommunityAndBuilding(Long id);

    @Select( """
                SELECT
            h.id,
            h.house_number,
            h.floor,
            h.unit_number,
            h.area,
            h.house_type,
            h.status,

            b.id AS buildingId,
            b.building_name AS buildingName,

            c.id AS communityId,
            c.community_name AS communityName,
            c.community_address AS communityAddress

        FROM house h
        LEFT JOIN building b
        ON h.building_id = b.id
        LEFT JOIN community c
        ON b.community_id = c.id
        """)
    List<HouseDTO> getAllWithCommunityAndBuilding();
}
