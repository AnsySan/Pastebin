package com.ansysan.pastebin.mapper;

import com.ansysan.pastebin.dto.CommentDto;
import com.ansysan.pastebin.dto.CommentToCreateDto;
import com.ansysan.pastebin.dto.CommentToUpdateDto;
import com.ansysan.pastebin.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    Comment toEntity(CommentToCreateDto commentDto);

    void update(CommentToUpdateDto dto, @MappingTarget Comment comment);
}