package com.spooky.service.mapper;

import com.spooky.domain.*;
import com.spooky.service.dto.SpookyMonsterDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SpookyMonster and its DTO SpookyMonsterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpookyMonsterMapper {

    @Mapping(source = "whiteboard.id", target = "whiteboardId")
    SpookyMonsterDTO spookyMonsterToSpookyMonsterDTO(SpookyMonster spookyMonster);

    List<SpookyMonsterDTO> spookyMonstersToSpookyMonsterDTOs(List<SpookyMonster> spookyMonsters);

    @Mapping(source = "whiteboardId", target = "whiteboard")
    SpookyMonster spookyMonsterDTOToSpookyMonster(SpookyMonsterDTO spookyMonsterDTO);

    List<SpookyMonster> spookyMonsterDTOsToSpookyMonsters(List<SpookyMonsterDTO> spookyMonsterDTOs);

    default Whiteboard whiteboardFromId(Long id) {
        if (id == null) {
            return null;
        }
        Whiteboard whiteboard = new Whiteboard();
        whiteboard.setId(id);
        return whiteboard;
    }
}
