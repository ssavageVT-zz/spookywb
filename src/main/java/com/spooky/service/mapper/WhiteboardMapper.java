package com.spooky.service.mapper;

import com.spooky.domain.*;
import com.spooky.service.dto.WhiteboardDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Whiteboard and its DTO WhiteboardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WhiteboardMapper {

    WhiteboardDTO whiteboardToWhiteboardDTO(Whiteboard whiteboard);

    List<WhiteboardDTO> whiteboardsToWhiteboardDTOs(List<Whiteboard> whiteboards);

    @Mapping(target = "spookyMonsters", ignore = true)
    Whiteboard whiteboardDTOToWhiteboard(WhiteboardDTO whiteboardDTO);

    List<Whiteboard> whiteboardDTOsToWhiteboards(List<WhiteboardDTO> whiteboardDTOs);
}
