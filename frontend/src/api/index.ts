import axios from 'axios';
// import {Profile} from "@/types";

//
export async function fetchProfile() {
	try {
		const result = await axios.get('http://localhost:8080/api/profile');
		return result.data
	} catch (e) {
		console.log(e)
	}
}
