package com.chirantan.project.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Integer commentId;
	private String content;
	private UserDto user;
//	private PostDto post;
}
