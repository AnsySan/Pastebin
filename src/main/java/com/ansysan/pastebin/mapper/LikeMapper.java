package com.ansysan.pastebin.mapper;

import com.ansysan.pastebin.dto.LikeDto;
import com.ansysan.pastebin.model.Like;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

    @Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface LikeMapper {

        LikeDto toDto(Like like);

        Like toEntity(LikeDto likeDto);
    }