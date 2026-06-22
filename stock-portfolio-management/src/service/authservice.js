import apiClient from "./apiClient";

const authService = {
    register : async (userData) => {
        try{
            const response = await apiClient.post("/auth/register", userData);
            return response.data;
        }catch(err){
            const message = err.response?.data?.message || "Registration failed";
            throw new Error(message);
        }
    },
    login : async (email, password) => {
        try{
            const response = await apiClient.post("/auth/login", {email, password});
            const loginData = response.data.data;
            localStorage.setItem("token", loginData.token);
            localStorage.setItem("user", JSON.stringify(loginData.user));
            localStorage.setItem("role", loginData.role);
            return loginData;
        }catch(err){
            const message = err.response?.data?.message || "Login failed";
            throw new Error(message);
        }
    },
    logout : async () => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        localStorage.removeItem("role");
    },
    getToken: ()=>{
        return localStorage.getItem("token");
    },
    isAuthenticated: () => {
        return !! localStorage.getItem("token");
    }
}
export default authService;