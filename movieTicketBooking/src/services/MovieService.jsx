import React from "react";

let baseUrl = "http://localhost:8080/api/v1/movies";

export let getAllMovies = async () => {
  let response = await fetch(baseUrl);
  return response.json();
};

export let addMovie = async (formData) => {
  let response = await fetch(baseUrl, {
    method: "POST",
    body: formData,
  });

  return response.json();
};

export let deleteMovie = async (id) => {
  await fetch(`${baseUrl}/${id}`, {
    method: "DELETE",
  });
};

export let getMovieById = async (id) => {
  let response = await fetch(`${baseUrl}/${id}`);
  return response.json();
};

export let updateMovie = async (id, formData) => {
  let response = await fetch(`${baseUrl}/${id}`, {
    method: "PUT",
    body: formData,
  });

  return response.json();
};
