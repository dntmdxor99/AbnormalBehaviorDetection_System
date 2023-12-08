import axios from "axios";

<<<<<<< HEAD
const BASE_URL = "http://172.20.5.89:8080";
=======
const BASE_URL = "http://172.20.5.197:8080";
>>>>>>> 4895692b4d711043d9334eb95c809d5040ebe841

const API = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

export default API;