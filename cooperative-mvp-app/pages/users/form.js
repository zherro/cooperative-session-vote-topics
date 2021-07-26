import axios from "axios"
import { useState } from "react"
import { CardTitle, Button, Col, Container, Form, FormGroup, Input, Label, Row, Alert } from "reactstrap"
import Header from "../../components/header"
import CONFIG_API from "../../const/confi"
import { useForm } from 'react-hook-form';
import { useRouter } from "next/dist/client/router"
import cpf from "../../const/cpf"
import makeid from "../../const/randonId"
import { generateName } from "../../const/dataGenerator"
import { postApi } from "../../const/apiCall"

const UserForm = () => {
	let defaultValues = {
		password: makeid(5),
		username: makeid(5),
		personDoc: cpf(),
		personName: generateName()
	}

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
		
		formData.person  = {
			doc: formData.personDoc,
			name: formData.personName
		}

		const res = await postApi(event, 'users', errorMessage, setErrorMessage,
		'/users', formData, router);		
	});

	const generateCPF = (e) => {
		e.preventDefault();
		defaultValues.personDoc = 'teste'
	}


	return <>
		<Container>
			<Header />
			<Row>
				<Col>
					<div style={{ maxWidth: '400px', margin: "0 auto" }}>
						<h3 className="text-center">Registro de usuário</h3>
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
							<Label for="username" sm={2}>Login</Label>
							<Col sm={10}>
								<input className="form-control" {...register('username')} type="text" name="username" id="username" placeholder="login..." />
							</Col>
						</FormGroup>
						<FormGroup row className="mt-3">
							<Label for="password" sm={2}>Senha</Label>
							<Col sm={10}>
								<input className="form-control"  {...register('password')} type="password" name="password" id="password" placeholder="senha..." />
							</Col>
						</FormGroup>
						<FormGroup row className="mt-3">
							<Label for="name" sm={2}>Nome</Label>
							<Col sm={10}>
								<input className="form-control"  {...register('personName')} type="text" name="personName" id="personName" placeholder="nome..." />
							</Col>
						</FormGroup>
						<FormGroup row className="mt-3">
							<Label for="doc" sm={2}>CPF/CNPJ</Label>
							<Col sm={10}>
								<input className="form-control"  {...register('personDoc')} type="text" name="personDoc" id="personDoc" placeholder="documento..." />
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