import { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { useNavigate } from "react-router-dom";
import handler from "../utils/handler";

const cardVariants = {
    initial: {
        opacity: 0,
        rotateY: -90,
        scale: 0.8,
    },
    animate: {
        opacity: 1,
        rotateY: 0,
        scale: 1,
        transition: {
            duration: 0.7,
            ease: "easeInOut",
        },
    },
    exit: {
        opacity: 0,
        rotateY: 90,
        scale: 0.8,
        transition: {
            duration: 0.5,
        },
    },
};

const Auth = () => {
    const navigate = useNavigate();
    useEffect(() => {
        const checkLoggedIn = async () => {
            try {
                await handler.get(
                    "/userdetails"
                );

                navigate("/");
            } catch (error) {
                // User not logged in
            }
        };

        checkLoggedIn();
    }, [navigate]);

    const [isLogin, setIsLogin] = useState(true);
    const [loading, setLoading] = useState(false);

    const [message, setMessage] = useState("");
    const [error, setError] = useState(false);

    const [loginForm, setLoginForm] = useState({
        username: "",
        password: "",
    });

    const [signupForm, setSignupForm] = useState({
        username: "",
        email: "",
        password: "",
        roles: "USER",
    });

    const handleLoginChange = (e) => {
        setLoginForm({
            ...loginForm,
            [e.target.name]: e.target.value,
        });
    };

    const handleSignupChange = (e) => {
        setSignupForm({
            ...signupForm,
            [e.target.name]: e.target.value,
        });
    };

    const login = async (e) => {
        e.preventDefault();

        setLoading(true);
        setMessage("");

        try {
            const res = await handler.post(
                "/sign-in",
                loginForm,
            );

            setMessage(res.data.message);
            setError(false);

            setTimeout(() => {
                navigate("/");
            }, 1200);
        } catch (err) {
            setError(true);
            setMessage(
                err?.response?.data?.message ||
                "Login failed. Please check your credentials."
            );
        } finally {
            setLoading(false);
        }
    };

    const signup = async (e) => {
        e.preventDefault();

        setLoading(true);
        setMessage("");

        try {
            const res = await handler.post(
                "/sign-up",
                signupForm
            );

            setError(false);
            setMessage(res.data.message);

            setTimeout(() => {
                setIsLogin(true);
                setMessage("Account created successfully. Please login.");
            }, 1500);
        } catch (err) {
            setError(true);
            setMessage(
                err?.response?.data?.message ||
                "Registration failed."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex justify-center items-center bg-gradient-to-br from-slate-950 via-indigo-950 to-black overflow-hidden">

            {/* Animated Background */}
            <div className="absolute w-[500px] h-[500px] bg-purple-700 rounded-full blur-[180px] opacity-30 animate-pulse" />
            <div className="absolute right-10 top-10 w-[300px] h-[300px] bg-cyan-500 rounded-full blur-[140px] opacity-20 animate-pulse" />

            <AnimatePresence mode="wait">

                {isLogin ? (
                    <motion.div
                        key="login"
                        variants={cardVariants}
                        initial="initial"
                        animate="animate"
                        exit="exit"
                        className="relative w-[420px] backdrop-blur-xl bg-white/10 border border-white/20 rounded-3xl p-8 shadow-2xl"
                    >
                        <h2 className="text-4xl font-bold text-center text-white mb-2">
                            Welcome Back
                        </h2>

                        <p className="text-center text-gray-300 mb-8">
                            Login to continue
                        </p>

                        <form onSubmit={login} className="space-y-5">
                            <input
                                type="text"
                                name="username"
                                value={loginForm.username}
                                onChange={handleLoginChange}
                                placeholder="Username"
                                className="w-full p-4 rounded-xl bg-white/10 border border-white/20 text-white outline-none"
                            />

                            <input
                                type="password"
                                name="password"
                                value={loginForm.password}
                                onChange={handleLoginChange}
                                placeholder="Password"
                                className="w-full p-4 rounded-xl bg-white/10 border border-white/20 text-white outline-none"
                            />

                            <button
                                disabled={loading}
                                className="w-full p-4 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition text-white font-semibold"
                            >
                                {loading ? "Logging in..." : "Login"}
                            </button>
                        </form>

                        {message && (
                            <div
                                className={`mt-4 text-center ${error ? "text-red-400" : "text-green-400"
                                    }`}
                            >
                                {message}
                            </div>
                        )}

                        <div className="mt-8 text-center">
                            <p className="text-gray-300">
                                Don't have an account?
                            </p>

                            <button
                                onClick={() => {
                                    setMessage("");
                                    setIsLogin(false);
                                }}
                                className="mt-2 text-cyan-400 hover:text-cyan-300"
                            >
                                Create Account →
                            </button>
                        </div>
                    </motion.div>
                ) : (
                    <motion.div
                        key="signup"
                        variants={cardVariants}
                        initial="initial"
                        animate="animate"
                        exit="exit"
                        className="relative w-[420px] backdrop-blur-xl bg-white/10 border border-white/20 rounded-3xl p-8 shadow-2xl"
                    >
                        <h2 className="text-4xl font-bold text-center text-white mb-2">
                            Join Us
                        </h2>

                        <p className="text-center text-gray-300 mb-8">
                            Create your account
                        </p>

                        <form onSubmit={signup} className="space-y-4">

                            <input
                                type="text"
                                name="username"
                                value={signupForm.username}
                                onChange={handleSignupChange}
                                placeholder="Username"
                                className="w-full p-4 rounded-xl bg-white/10 border border-white/20 text-white outline-none"
                            />

                            <input
                                type="email"
                                name="email"
                                value={signupForm.email}
                                onChange={handleSignupChange}
                                placeholder="Email"
                                className="w-full p-4 rounded-xl bg-white/10 border border-white/20 text-white outline-none"
                            />

                            <input
                                type="password"
                                name="password"
                                value={signupForm.password}
                                onChange={handleSignupChange}
                                placeholder="Password"
                                className="w-full p-4 rounded-xl bg-white/10 border border-white/20 text-white outline-none"
                            />

                            <input
                                type="text"
                                name="roles"
                                value={signupForm.roles}
                                onChange={handleSignupChange}
                                placeholder="USER,ADMIN"
                                className="w-full p-4 rounded-xl bg-white/10 border border-white/20 text-white outline-none"
                            />

                            <button
                                disabled={loading}
                                className="w-full p-4 rounded-xl bg-emerald-600 hover:bg-emerald-700 transition text-white font-semibold"
                            >
                                {loading ? "Creating..." : "Create Account"}
                            </button>
                        </form>

                        {message && (
                            <div
                                className={`mt-4 text-center ${error ? "text-red-400" : "text-green-400"
                                    }`}
                            >
                                {message}
                            </div>
                        )}

                        <div className="mt-8 text-center">
                            <p className="text-gray-300">
                                Already a user?
                            </p>

                            <button
                                onClick={() => {
                                    setMessage("");
                                    setIsLogin(true);
                                }}
                                className="mt-2 text-cyan-400 hover:text-cyan-300"
                            >
                                ← Back to Login
                            </button>
                        </div>
                    </motion.div>
                )}
            </AnimatePresence>
        </div>
    );
};

export default Auth;