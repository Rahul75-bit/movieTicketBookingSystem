import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "../pages/Home";
import Movies from "../pages/Movies";
import AddMovie from "../pages/AddMovie";
import UpdateMovie from "../pages/UpdateMovie";

export default function AppRouter() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/movies" element={<Movies />} />
          <Route path="/add-movie" element={<AddMovie />} />
          <Route path="/update-movie/:id" element={<UpdateMovie />} />
        </Routes>
      </BrowserRouter>

      
    </>
  );
}
