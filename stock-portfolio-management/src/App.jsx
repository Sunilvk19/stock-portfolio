import React from "react";
import RegisterPage from "./pages/Register.jsx";
import Dashboard from "./Components/Dashboard.jsx";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login.jsx";

const App = () => {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
      </BrowserRouter>
  );
};

export default App;
