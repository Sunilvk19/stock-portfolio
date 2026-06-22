import React,{createContext, useContext, useState} from 'react'
import authService from '../service/authservice'
const AuthContext = createContext();
export const AuthProvider = ({children}) => {
    const [user, setUser] = useState(localStorage.getItem("user"));
    const [token, setToken] = useState(localStorage.getItem("token"));
    const login = async(email, password) => {
        const data = await authService.login(email, password);
        setUser(data.user);
        setToken(data.token);
    }
    const logout = async() => {
        await authService.logout();
        setUser(null);
        setToken(null);
    }
    return (
        <AuthContext.Provider value={{user, token, login, logout}}>  
            {children}
        </AuthContext.Provider>
    )
}
export const useAuth = () => {
    return useContext(AuthContext);
}
    