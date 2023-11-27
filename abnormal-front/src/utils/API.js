import axios from "axios";

const BASE_URL = "https://172.20.38.162:8080";

const API = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

export default API;