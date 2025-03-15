import { useForm } from "react-hook-form";
import { Link, Meta, useNavigate } from "react-router-dom";
import bgLogin from "../assets/bglogin.jpg";
import toast from "react-hot-toast";
import { fetchRequest } from "../utility/apiCall";

export default function Login() {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    console.log("Login Data:", data);
    // TODO: make an API call here to authenticate the user
    /*
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"auth/login","POST",data)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        // set the token to ls
        localStorage.setItem("token",response.data.token)
      }
    */
    // you'll get a token here
    localStorage.setItem("token", "your-token-here");
    // make an API call to get user details 
    /*
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"auth/getUserByUsername/"+data.username,"POST",data)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        // set the token to ls
        localStorage.setItem("user",response.data)
      }
    */
    const fakeUser = { username: data.username, role: "ADMIN" }; // or "ADMIN"
    localStorage.setItem("user", JSON.stringify(fakeUser));
    toast.success("Login successful");
    navigate("/dashboard");
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-cover bg-center p-6" style={{ backgroundImage: `url(${bgLogin})` }}>
      <h1 className="text-5xl font-extrabold text-white mb-8 text-center shadow-lg">Hospital Management System</h1>
      <div className="bg-white p-10 rounded-xl shadow-2xl max-w-xl w-full border border-gray-300">
        <h2 className="text-4xl font-extrabold text-center mb-6 text-gray-900">Sign In</h2>
        <p className="text-center text-gray-500 mb-6">Access your account</p>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
          {/* Username Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Username</label>
            <input
              type="text"
              {...register("username", { required: "Username is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.username && <p className="text-red-500 text-sm mt-1">{errors.username.message}</p>}
          </div>
          
          {/* Password Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Password</label>
            <input
              type="password"
              {...register("password", { required: "Password is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
          </div>
          
          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 transition-all shadow-lg font-semibold text-lg"
          >
            Sign In
          </button>
          
          {/* Not a member? Register Link */}
          <p className="text-center text-gray-600 mt-4">
            Not a member? <Link to="/register" className="text-blue-600 hover:underline">Register</Link>
          </p>
        </form>
      </div>
    </div>
  );
}
