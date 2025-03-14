import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log("Registration Data:", data);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
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

          {/* Email Field */}
          <div>
            <label className="block text-gray-600 font-semibold">Email</label>
            <input
              type="email"
              {...register("email", { required: "Email is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>}
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
              {...register("phone", { required: "Phone number is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-500 shadow-sm transition-all"
            />
            {errors.phone && <p className="text-red-500 text-sm mt-1">{errors.phone.message}</p>}
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
