package com.neko233.game.map.util;

import com.neko233.game.map.Coordinate3d;
import com.neko233.game.map.Grid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScanGridResult {

    private Grid centerGrid;
    private Map<Grid, List<Coordinate3d>> aggByGridMap;


}
