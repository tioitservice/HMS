import { useForm } from "react-hook-form";

export default function PatientRegistration() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log("Patient Data:", data);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="bg-white p-10 rounded-2xl shadow-2xl max-w-lg w-full border border-gray-200">
        <h2 className="text-3xl font-bold text-center mb-8 text-gray-800">Patient Registration</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
          {/* Name Field */}
          <div>
            <label className="block text-gray-700 font-medium">Name</label>
            <input
              type="text"
              {...register("name", { required: "Name is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-400 shadow-sm"
            />
            {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>}
          </div>
          
          {/* Address Field */}
          <div>
            <label className="block text-gray-700 font-medium">Address</label>
            <input
              type="text"
              {...register("address", { required: "Address is required" })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-400 shadow-sm"
            />
            {errors.address && <p className="text-red-500 text-sm mt-1">{errors.address.message}</p>}
          </div>
          
          {/* Phone Field */}
          <div>
            <label className="block text-gray-700 font-medium">Phone</label>
            <input
              type="tel"
              {...register("phone", {
                required: "Phone number is required",
                pattern: {
                  value: /^[0-9]{10}$/,
                  message: "Enter a valid 10-digit phone number",
                },
              })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-400 shadow-sm"
            />
            {errors.phone && <p className="text-red-500 text-sm mt-1">{errors.phone.message}</p>}
          </div>
          
          {/* Password Field */}
          <div>
            <label className="block text-gray-700 font-medium">Password</label>
            <input
              type="password"
              {...register("password", {
                required: "Password is required",
                minLength: {
                  value: 6,
                  message: "Password must be at least 6 characters long",
                },
              })}
              className="mt-2 p-3 w-full border rounded-lg focus:ring-2 focus:ring-blue-400 shadow-sm"
            />
            {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
          </div>
          
          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 transition-all shadow-md"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
}
