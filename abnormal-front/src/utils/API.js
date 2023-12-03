import axios from "axios";

const BASE_URL = "http://192.168.45.145:8080";

const API = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

export default API;