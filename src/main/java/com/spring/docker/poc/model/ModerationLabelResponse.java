package com.spring.docker.poc.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModerationLabelResponse {
		public String name;
		public Float confidence;
		public String category;
}
