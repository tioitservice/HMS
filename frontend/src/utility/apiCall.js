export async function fetchRequest(url, method = "GET", body = null, headers = {}) {
    try {
      const token = localStorage.getItem("token");
      if (token) {
        headers.Authorization = `Bearer ${token}`;
      }
      const config = {
        method,
        headers: {
          "Content-Type": "application/json",
          ...headers,
        },
      };
      
      if (body) {
        if(typeof body === "string"){
          config.body = body
        }else{
        config.body = JSON.stringify(body);
      }
    }
  
      console.log(config)
      const response = await fetch(url, config);
      const data = await response.json();
  
      if (!response.ok) {
        throw new Error(data.message || "Something went wrong");
      }
  
      return { success: true, data };
    } catch (error) {
      console.error("Fetch Error:", error);
      return { success: false, error: error.message };
    }
  }
  