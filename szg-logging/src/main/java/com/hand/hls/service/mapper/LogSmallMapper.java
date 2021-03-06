package com.hand.hls.service.mapper;

import com.hand.hls.mapper.EntityMapper;
import com.hand.hls.domain.Log;
import com.hand.hls.mapper.EntityMapper;
import com.hand.hls.service.dto.LogSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends EntityMapper<LogSmallDTO, Log> {

}