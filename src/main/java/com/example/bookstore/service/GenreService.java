package com.example.bookstore.service;

import java.util.List;
import java.util.UUID;

import com.example.bookstore.model.Genre;

public interface GenreService {
	List<Genre> getListGenres();

	boolean deleteGenre(UUID genreId);

	void addGenre(Genre genre);

	void update(Genre genre);
}
