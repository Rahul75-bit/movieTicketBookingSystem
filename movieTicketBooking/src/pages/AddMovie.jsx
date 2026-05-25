import React, { useState } from "react";
import { addMovie } from "../services/MovieService";
import { toast } from "react-toastify";

export default function AddMovie({ closeModal }) {
  let [movie, setMovie] = useState({
    title: "",
    genre: "",
    description: "",
    duration: "",
    language: "",
    movieImgUrl: "",
    rating: "",
    releaseDate: "",
  });
  let [selectedImage, setSelectedImage] = useState(null);

  let handleChange = (e) => {
    setMovie({
      ...movie,
      [e.target.name]: e.target.value,
    });
  };

  let handleSubmit = async (e) => {
    e.preventDefault();

    let formData = new FormData(); 
    formData.append("movieObject", JSON.stringify(movie)); 

    formData.append("movieImage", selectedImage); 

    await addMovie(formData); 
    toast.success("🎬 Movie Added Successfully!");
    closeModal();
    console.log(movie);
    console.log(selectedImage);
  };

  return (
    <>
      {/* Body */}
      <div className="p-4">
        <form onSubmit={handleSubmit}>
          <div className="row">
            {/* Movie Title */}
            <div className="col-md-6 mb-3">
              <label className="form-label fw-semibold">Movie Title</label>

              <input
                type="text"
                name="title"
                placeholder="Enter movie title"
                className="form-control form-control-lg"
                onChange={handleChange}
                required
              />
            </div>
            {/* Genre */}
            <div className="col-md-6 mb-3">
              <label className="form-label fw-semibold">Genre</label>

              <input
                type="text"
                name="genre"
                placeholder="Action, Comedy..."
                className="form-control form-control-lg"
                onChange={handleChange}
                required
              />
            </div>
            {/* Duration */}
            <div className="col-md-6 mb-3">
              <label className="form-label fw-semibold">Duration</label>

              <input
                type="text"
                name="duration"
                placeholder="2h 30m"
                className="form-control form-control-lg"
                onChange={handleChange}
              />
            </div>
            {/* Language */}
            <div className="col-md-6 mb-3">
              <label className="form-label fw-semibold">Language</label>

              <input
                type="text"
                name="language"
                placeholder="Hindi / English"
                className="form-control form-control-lg"
                onChange={handleChange}
              />
            </div>
            {/* Rating */}
            <div className="col-md-6 mb-3">
              <label className="form-label fw-semibold">Rating</label>

              <input
                type="number"
                step="0.1"
                name="rating"
                placeholder="8.5"
                className="form-control form-control-lg"
                onChange={handleChange}
              />
            </div>
            {/* Release Date */}
            <div className="col-md-6 mb-3">
              <label className="form-label fw-semibold">Release Date</label>

              <input
                type="date"
                name="releaseDate"
                className="form-control form-control-lg"
                onChange={handleChange}
              />
            </div>

            {/* Movie Poster Upload */}
            <div className="col-12 mb-3">
              <label className="form-label fw-semibold">
                Upload Movie Poster
              </label>

              <input
                type="file"
                accept="image/*"
                className="form-control form-control-lg"
                onChange={(e) => {
                  let file = e.target.files[0];

                  setSelectedImage(file);
                }}
              />
            </div>

            {/* Image Preview */}

            {selectedImage && (
              <div className="col-12 mb-4 text-center">
                <img
                  src={URL.createObjectURL(selectedImage)}
                  alt="Preview"
                  className="img-fluid rounded shadow"
                  style={{
                    maxHeight: "250px",
                    objectFit: "cover",
                  }}
                />
              </div>
            )}

            {/* Description */}
            <div className="col-12 mb-4">
              <label className="form-label fw-semibold">Description</label>

              <textarea
                name="description"
                placeholder="Enter movie description..."
                className="form-control"
                rows="4"
                onChange={handleChange}
              ></textarea>
            </div>
          </div>

          {/* Buttons */}

          <div className="d-flex justify-content-end gap-3">
            <button
              type="button"
              onClick={closeModal}
              className="btn px-4 fw-semibold"
              style={{
                background: "linear-gradient(135deg, #2563eb, #1d4ed8)",
                color: "white",
                borderRadius: "12px",
                border: "none",
                padding: "10px 22px",
              }}
            >
              Cancel
            </button>

            <button
              type="submit"
              className="btn px-5 fw-semibold"
              style={{
                backgroundColor: "#111827",
                color: "#60a5fa",
                border: "1px solid #2563eb",
                borderRadius: "12px",
                padding: "10px 26px",
              }}
            >
              Save Movie
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
