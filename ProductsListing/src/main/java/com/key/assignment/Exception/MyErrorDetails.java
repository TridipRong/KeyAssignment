package com.key.assignment.Exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyErrorDetails {
	private LocalDateTime timestamp;
	private String message;
	private String details;
}
