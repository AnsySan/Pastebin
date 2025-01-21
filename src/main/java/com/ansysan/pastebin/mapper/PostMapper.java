package com.ansysan.pastebin.mapper;

import com.ansysan.pastebin.dto.PostCreateDto;
import com.ansysan.pastebin.dto.PostDto;
import com.ansysan.pastebin.dto.PostResponse;
import com.ansysan.pastebin.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    Post toEntity(PostCreateDto postCreateDto);

    PostDto toDto(Post post);

    @Mapping(target = "content", ignore = true)  // Контент будет устанавливаться отдельно
    PostResponse toPostResponse(Post post);
}