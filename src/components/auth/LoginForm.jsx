import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { signInSchema } from "../../utils/validation.util.js";
import AuthInput from "./AuthInput.jsx";
import { useDispatch, useSelector } from "react-redux";
import { PulseLoader } from "react-spinners";
import { Link, useNavigate } from "react-router-dom";
import "../../styles/style.css"
import { loginUser } from "../../features/userSlice.js";

export default function RegisterForm() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { status, error } = useSelector((state) => state.user);
    const { register, handleSubmit, watch, formState: { errors } } = useForm({
        resolver: yupResolver(signInSchema),
    });
    const onSubmit = async(values) => {
        let res = await dispatch(loginUser({...values}));
        if(res?.payload?.user){
            navigate("/");
        }
    };

    return (
        <div className="h-screen w-full flex items-center justify-center overflow-hidden bg-gray-100 dark:bg-dark_bg_1">
            <div className="abstract-1"></div>
            <div className="abstract-2"></div>
            <div className="max-w-md w-full bg-white dark:bg-dark_bg_2 rounded-xl shadow-lg p-8 space-y-6">
                <div className="text-center">
                    <h1 className="text-3xl font-bold text-gray-800 dark:text-gray-200 justify-center animate-slide content-center">
                        <span className="text-green-500 animate-left">Welcome</span>
                        <span> &nbsp; </span>
                        <span className="animate-right" style={{ animationDelay: '0.0s' }}>Back</span>
                    </h1>
                    <p className="text-sm text-gray-600 dark:text-gray-400 justify-center origin-center">
                        <span>Sign In to Continue</span> 
                    </p>  
                </div>
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
                    <AuthInput
                        name="email"
                        type="text"
                        placeholder="Email Address"
                        register={register}
                        error={errors?.email?.message}
                    />
                    <AuthInput
                        name="password"
                        type="password"
                        placeholder="Password"
                        register={register}
                        error={errors?.password?.message}
                    />
                    {
                        error ? (
                        <div>
                            <p className="text-red-400">{error}</p>
                        </div>
                    ):null}
                    <button
                        className="w-full bg-green-500 text-gray-100 p-4 rounded-full font-semibold focus:outline-none hover:bg-green-600 shadow-lg transition duration-300"
                        type="submit"
                    >
                        {status === "loading" ? <PulseLoader color="#fff" /> : "Sign In"}
                    </button>
                    <p className="text-center text-sm text-gray-600 dark:text-gray-400">
                        Don't have an account?{" "}
                        <Link to="/register" className="text-green-500 hover:underline">
                            Sign Up
                        </Link>
                    </p>
                </form>
            </div>
        </div>
    );
}
