import { useContext, useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import UserContext from "../UserContext/UserContext";
import { getUserDetails } from "../utils/userDetails";

const ProtectedRoute = ({ children }) => {
    const { setUser } = useContext(UserContext);
    const [loading, setLoading] = useState(true);
    const [authenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        const verifyUser = async () => {
            try {
                const userData = await getUserDetails();
                setUser(userData);
                setAuthenticated(true);
            } catch {
                setAuthenticated(false);
            } finally {
                setLoading(false);
            }
        };

        verifyUser();
    }, [setUser]);

    if (loading) {
        return (
            <div className="min-h-screen flex justify-center items-center bg-slate-950 text-white">
                <div className="flex flex-col items-center gap-4">
                    <div className="w-12 h-12 border-4 border-indigo-500 border-t-transparent rounded-full animate-spin"></div>
                    <p>Authenticating...</p>
                </div>
            </div>
        );
    }

    return authenticated ? children : <Navigate to="/auth" replace />;
};

export default ProtectedRoute;