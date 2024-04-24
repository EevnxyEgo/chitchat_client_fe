import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { signUpSchema } from "../../utils/validation.util.js";
import AuthInput from "./AuthInput.jsx";
import { useDispatch, useSelector } from "react-redux";
import { PulseLoader } from "react-spinners";
import { Link, useNavigate } from "react-router-dom";
import "../../styles/style.css"
import { registerUser } from "../../features/userSlice.js";

export default function RegisterForm() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { status, error } = useSelector((state) => state.user);
    const { register, handleSubmit, watch, formState: { errors } } = useForm({
        resolver: yupResolver(signUpSchema),
    });
    const onSubmit = async(data) => {
        let res = await dispatch(registerUser({...data, picture: ""}))
        console.log(res);
        if(res.payload.user){
        navigate("/");
        }
    };
    //console.log("values", watch());
    //console.log("errors", errors);

    return (
        <div className="h-screen w-full flex items-center justify-center overflow-hidden bg-gray-100 dark:bg-dark_bg_1">
            <div className="abstract-1"></div>
            <div className="abstract-2"></div>
            <div className="max-w-md w-full bg-white dark:bg-dark_bg_2 rounded-xl shadow-lg p-8 space-y-6">
                <div className="text-center">
                    <h1 className="text-3xl font-bold text-gray-800 dark:text-gray-200 justify-center animate-slide content-center">
                        <span className="text-green-500 animate-left">Get</span>
                        <span> &nbsp; </span>
                        <span className="animate-right" style={{ animationDelay: '0.0s' }}>Started</span>
                    </h1>
                    <p className="text-sm text-gray-600 dark:text-gray-400 justify-center origin-center">
                        <span>Sign Up and Join Us!</span> 
                    </p>  
                </div>
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
                    <AuthInput
                        name="name"
                        type="text"
                        placeholder="Full Name"
                        register={register}
                        error={errors?.name?.message}
                    />
                    <AuthInput
                        name="email"
                        type="text"
                        placeholder="Email Address"
                        register={register}
                        error={errors?.email?.message}
                    />
                    <AuthInput
                        name="status"
                        type="text"
                        placeholder="Status (Opsional)"
                        register={register}
                        error={errors?.status?.message}
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
                        {status === "loading" ? <PulseLoader color="#fff" /> : "Sign up"}
                    </button>
                    <p className="text-center text-sm text-gray-600 dark:text-gray-400">
                        Have an account?{" "}
                        <Link to="/login" className="text-green-500 hover:underline">
                            Sign In
                        </Link>
                    </p>
                </form>
            </div>
        </div>
    );
}
