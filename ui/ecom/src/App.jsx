import { Routes, Route, Navigate } from "react-router-dom";
import Auth from "./auth/Auth";
import ProtectedRoute from "./auth/ProtectedRoute";
import Home from "./components/Home";


const App = () => {
  return (
    <Routes>
      <Route path="/auth" element={<Auth />} />

      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        }
      />

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default App;