import axios from "axios";
import CONFIG_API from "./confi";

export const getApi = async (uri, setData, errorMessage, setErrorMessage) => {
	if (errorMessage) setErrorMessage('');
	try {
		const res = await axios.get(CONFIG_API() + uri, {
			headers: {
				'Access-Control-Allow-Methods': 'PUT, POST, PATCH, DELETE, GET'
			}
		})
		if (res.status == 200) {
			const data = await res.data
			setData(data)
			console.log(`Show data fetched. Count: ${data.length}`)
		} else {
			throw new Error(await res);
		}
	} catch (err) {
		console.table(err.response.data)
		setErrorMessage(err.response.data?.msg)
	}
};

export const postApi = async (
	event, uri, errorMessage, setErrorMessage, redirect, data, router
) => {
	try {
		event.preventDefault();
		if (errorMessage) setErrorMessage('');

		console.table(data);

		const res = await fetch(CONFIG_API() + uri, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data),
		});
		console.table(res);
		if (res.status > 199  && res.status < 300) {
			if(redirect && redirect.length > 0)
				router.push(redirect);
		} else {
			throw new Error(await res.text());
		}
	} catch (err) {
		console.error(err.message)
		await setErrorMessage(JSON.parse(err.message).msg)
	}
}

export const patchApi = async (
	event, uri, errorMessage, setErrorMessage, redirect, data, router
) => {
	try {
		event.preventDefault();
		if (errorMessage) setErrorMessage('');

		console.table(data);

		const res = await fetch(CONFIG_API() + uri, {
			method: 'PATCH',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data),
		});
		console.table(res);
		if (res.status > 199  && res.status < 300) {
			if(redirect && redirect.length > 0)
				router.push(redirect);
		} else {
			throw new Error(await res.text());
		}
	} catch (err) {
		console.error(err.message)
		await setErrorMessage(JSON.parse(err.message).msg)
	}
}