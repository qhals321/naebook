import axios, { AxiosInstance } from 'axios';

const instance: AxiosInstance = axios.create({
	baseURL: 'http://localhost:8080/api',
	headers: {
		'Access-Control-Allow-Origin': '*',
		'Content-Type': 'application/json;charset=utf-8',
	},
	timeout: 100000,
})

export async function fetchProfile() {
	try {
		const { data } = await instance.get('/profile');
		return data
	} catch (e) {
		console.log(e)
	}
}
