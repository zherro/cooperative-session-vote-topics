import axios from "axios"
import { useState } from "react"
import { CardTitle, Button, Col, Container, Form, FormGroup, Input, Label, Row, Alert } from "reactstrap"
import Header from "../../components/header"
import { topic } from "../../const/dataGenerator"
import CONFIG_API from "../../const/confi"
import { useForm } from 'react-hook-form';
import { useRouter } from "next/dist/client/router"
import Block from "../../components/block"

const UserForm = () => {

	const mock = topic();

	let defaultValues = {
		description: mock?.description,
		theme: mock?.theme
	}

	const [block, setBlock] = useState(false)
	const [errorMessage, setErrorMessage] = useState('');
	const router = useRouter();

	const {
		handleSubmit,
		register,
		formState: { errors },
	} = useForm({
		defaultValues: {
			...defaultValues
		},
	});

	const onSubmit = handleSubmit(async (formData, event) => {
		setBlock(true)
		try {
			event.preventDefault();
			if (errorMessage) setErrorMessage('');

			console.table(formData);

			const res = await fetch(CONFIG_API() + "pauta", {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(formData),
			});

			setBlock(false)
			console.table(res);
			if (res.status == 201) {
				router.push('/topics');
			} else {
				setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')
				throw new Error(await res.text());
			}
		} catch (err) {
			setBlock(false)
			setErrorMessage('iiiihh! Deu ruin. Provavelmente a api está off.')

		}

	});

	const generateCPF = (e) => {
		e.preventDefault();
		defaultValues.personDoc = 'teste'
	}


	return <>
		<Block show={block} />
		<Container>
			<Header />
			<Row>
				<Col>
					<div style={{ maxWidth: '400px', margin: "0 auto" }}>
						<h3 className="text-center">Registro de pauta</h3>
					</div>
					{
						errorMessage?.length > 0 &&
						<div style={{ maxWidth: '400px', margin: "0 auto" }}>
							<Alert color="warning">
								{errorMessage}
							</Alert>
						</div>
					}
					<form onSubmit={(event) => onSubmit(event)} style={{ maxWidth: '400px', margin: "0 auto" }}>
						<FormGroup row className="mt-3">
							<Label for="theme" sm={2}>Tema</Label>
							<Col sm={10}>
								<textarea rows="5" className="form-control" {...register('theme')} type="text" name="theme" id="theme" placeholder="Tema da pauta...">

								</textarea>
							</Col>
						</FormGroup>
						<FormGroup row className="mt-3">
							<Label for="password" sm={2}>Descrição</Label>
							<Col sm={10}>
								<textarea rows="10" className="form-control" {...register('description')} type="text" name="description" id="description" placeholder="Tema da pauta...">

								</textarea>
							</Col>
						</FormGroup>
						<br />
						<div>
							<small>** valores gerados automaticamente para fim de testes!</small>
						</div>
						<div className="text-end mt-3">
							<Button type="submit" color="warning" >Salvar</Button>
						</div>
					</form>
				</Col>
			</Row>
		</Container>
	</>
}

export default UserForm;