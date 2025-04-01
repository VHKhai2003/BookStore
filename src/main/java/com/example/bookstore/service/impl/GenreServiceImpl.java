package com.example.bookstore.service.impl;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.bookstore.exception.CustomException;
import com.example.bookstore.model.Genre;
import com.example.bookstore.repository.GenreRepository;
import com.example.bookstore.service.GenreService;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;

	@Override
	public List<Genre> getListGenres() {
		return genreRepository.findAll();
	}

	@Override
	public boolean deleteGenre(UUID genreId) {
		Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new CustomException(404, "Genre not found"));
		if (!genre.getBooks().isEmpty()) {
			return false;
		}
		genreRepository.deleteById(genreId);
		return true;
	}

	@Override
	public void addGenre(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public void update(Genre genre) {
		genreRepository.save(genre);
	}

}
