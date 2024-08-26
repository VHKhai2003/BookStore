package com.example.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import com.example.bookstore.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository genreRepository;
	
	@Override
	public List<Genre> getListGenres() {
		return genreRepository.findAll();
	}
	
}
