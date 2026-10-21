import { useContext } from 'react'
import UserContext from '../UserContext/UserContext';

const Home = () => {
    const { user } = useContext(UserContext);

    return (
        <div className="min-h-screen flex items-center justify-center bg-slate-950 text-white text-4xl font-bold">
            Welcome {user?.username}, to E-Commerce
        </div>
    );
}

export default Home