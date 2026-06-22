import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../service/authservice";

const Dashboard = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({ name: "", email: "", role: "" });

  useEffect(() => {
    const rawUser = localStorage.getItem("user");
    const storedRole = localStorage.getItem("role");

    if (!rawUser) {
      navigate("/");
      return;
    }
    try {
      const parsed = JSON.parse(rawUser);
      setUser({
        name: parsed.name || "",
        email: parsed.email || "",
        role: storedRole || "",
      });
    } catch (e) {
      console.error("Failed to parse stored user", e);

      localStorage.clear();
      navigate("/");
    }
  }, [navigate]);

  const handleLogout = () => {
    authService.logout();
    navigate("/");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-linear-to-br from-indigo-50 via-purple-50 to-pink-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-700">
      <p>{user.name}</p>
      <p>{user.email}</p>
      <p>{user.role}</p>
      <button
        onClick={handleLogout}
        className="mt-6 bg-red-600 text-white py-2 px-4 rounded-md hover:bg-red-700"
      >
        Logout
      </button>
    </div>
  );
};

export default Dashboard;
