package com.example.bookstore.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.bookstore.model.Genre;
import com.example.bookstore.service.GenreService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDataConfig {
	
	@Autowired
	private GenreService genreService;
	
	@ModelAttribute("genres")
	public List<Genre> getListGenres(HttpServletRequest request) {
		System.out.println("controller advice ---- " + request.getRequestURL());
		return genreService.getListGenres();
	}
}
