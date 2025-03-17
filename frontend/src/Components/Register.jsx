import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { fetchRequest } from "../utility/apiCall";
import bgLogin from "../assets/bglogin.jpg";
import {toast} from "react-hot-toast"
export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();
  const onSubmit = async(data) => {
    console.log("Registration Data:", data);
    // TODO: make an API call here to register the user
    
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"auth/register","POST",data)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        // set the token to ls
        toast.success("Registration successful")
        navigate("/login")
      }
    
    
  };

  return (
    <div
      className="min-h-screen flex flex-col items-center justify-center bg-cover bg-center p-6"
      style={{ backgroundImage: `url(${bgLogin})` }}
    >
      <h1 className="text-5xl font-extrabold text-white mb-8 text-center shadow-lg">
        Hospital Management System
      </h1>
      <div className="bg-white p-10 rounded-xl shadow-2xl max-w-xl w-full border border-gray-300">
        <h2 className="text-4xl font-extrabold text-center mb-6 text-gray-900">Register</h2>
        <p className="text-center text-gray-500 mb-6">Create your patient account</p>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
          {/* Name Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Name</label>
            <input
              type="text"
              {...register("name", { required: "Name is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>}
          </div>

          {/* Username Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Username</label>
            <input
              type="text"
              {...register("username", {
                required: "Username is required",
                minLength: {
                  value: 4,
                  message: "Username must be at least 4 characters",
                },
                pattern: {
                  value: /^[a-zA-Z0-9_]+$/,
                  message: "Username can only contain letters, numbers, and underscores",
                },
              })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.username && <p className="text-red-500 text-sm mt-1">{errors.username.message}</p>}
          </div>

          {/* Address Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Address</label>
            <textarea
              {...register("address", { required: "Address is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all h-24"
            ></textarea>
            {errors.address && <p className="text-red-500 text-sm mt-1">{errors.address.message}</p>}
          </div>

          {/* Phone Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Phone</label>
            <input
              type="tel"
              {...register("phoneNumber", {
                required: "Phone number is required",
                pattern: {
                  value: /^[0-9]{10,15}$/,
                  message: "Phone number must be between 10 to 15 digits",
                },
              })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.phone && <p className="text-red-500 text-sm mt-1">{errors.phone.message}</p>}
          </div>

          {/* Password Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Password</label>
            <input
              type="password"
              {...register("password", {
                required: "Password is required",
                minLength: {
                  value: 6,
                  message: "Password must be at least 6 characters",
                },
              })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 transition-all shadow-lg font-semibold text-lg"
          >
            Register
          </button>

          {/* Already have an account? Login Link */}
          <p className="text-center text-gray-600 mt-4">
            Already have an account? <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
          </p>
        </form>
      </div>
    </div>
  );
}
