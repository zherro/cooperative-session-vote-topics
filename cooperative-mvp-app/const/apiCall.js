import axios from "axios";
import React from "react";
import CONFIG_API from "./confi";


export const getApi = async (block, uri, setData, errorMessage, setErrorMessage) => {
	block(true);
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
			block(false)
			console.log(`Show data fetched. Count: ${data.length}`)
		} else {
			block(false)
			throw new Error(await res);
		}
	} catch (err) {
		block(false)
		if(!err.response) {
			setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')
		}else {
			console.table(err.response.data)
			setErrorMessage(err.response.data?.msg)
		}
	}
};

export const postApi = async (
	block, event, uri, errorMessage, setErrorMessage, redirect, data, router
) => {
	block(true)
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

		block(false)
		console.table(res);
		if (res.status > 199  && res.status < 300) {
			block(false)
			if(redirect && redirect.length > 0)
				router.push(redirect);
		} else {
			throw new Error(await res.text());
		}
	} catch (err) {
		block(false)
		if(!err.message) {
			setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')
		}else {
			setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')
			console.error(err.message)
			await setErrorMessage(JSON.parse(err.message).msg)
		}
	}
}

export const patchApi = async (
	block, event, uri, errorMessage, setErrorMessage, redirect, data, router
) => {
	block(true)
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
		block(false)
		console.table(res);
		if (res.status > 199  && res.status < 300) {
			if(redirect && redirect.length > 0)
				router.push(redirect);
		} else {
			throw new Error(await res.text());
		}
	} catch (err) {
		block(false)
		if(!err.message) {
			setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')
		}else {
			setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')
			console.error(err.message)
			await setErrorMessage(JSON.parse(err.message).msg)
		}
	}
}