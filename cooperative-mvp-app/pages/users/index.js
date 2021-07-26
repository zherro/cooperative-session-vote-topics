import { useEffect, useState } from "react";
import { Button, Col, Container, Modal, ModalBody, ModalFooter, ModalHeader, NavLink, Row, Table } from "reactstrap"
import Header from "../../components/header"
import Pagination from "../../components/Pagination";
import { getApi } from "../../const/apiCall";

const Users = () => {

	const [users, setUsers] = useState({})
	const [user, setToAuth] = useState({})
	const [errorMessage, setErrorMessage] = useState('');
	const [modal, setModal] = useState(false);

	const toggle = () => setModal(!modal);
	const getUsers = async (size, page) => {
		const res = await getApi(`users?size=${size}&page=${page}`,
			setUsers, errorMessage, setErrorMessage)
	}

	useEffect(() => {
		getUsers(5, 1)
	}, []);

	const auth = (user) => {
		localStorage.setItem('userId', user.id);
		localStorage.setItem('userName', user.person.name);
		localStorage.setItem('userDoc', user.person.doc);
		localStorage.setItem('userLogin', user.username);
		setToAuth(user);
		setModal(true);
	}

	return (
		<>
			<Container>
				<Header updateUserInfo={user} />
				<div style={{ maxWidth: '400px', margin: "0 auto" }}>
					<h3 className="text-center my-3  pb-3">Usuários</h3>
				</div>
				<div className="border-bottom mb-3"></div>
				<Row>
					<Col className="text-center">
						<a href="/users/form" style={{ maxWidth: '200px' }} className="btn btn-success text-white" >+ Adicionar usuário</a>
					</Col>
				</Row>
				<Row>
					<Col>
						<Table striped className="mt-3">
							<thead>
								<tr>
									<th>#</th>
									<th>Nome</th>
									<th>Documento</th>
									<th>Login</th>
								</tr>
							</thead>
							<tbody>
								{
									users?.content?.map((u) => {
										return <>
											<tr key={u.id}>
												<td></td>
												<td>{u.person?.name}</td>
												<td>{u.person?.doc}</td>
												<td>{u.username}</td>
											</tr>
											<tr key={u.id + 'a'}>
												<td colSpan="4" className="text-end">
													<Button onClick={() => auth(u)} size="sm" color="primary">Autenticar usuário</Button>
												</td>
											</tr>
										</>
									})
								}

								{
									!users?.content &&
									<tr>
										<td colSpan="4">
											Não há registros!
										</td>
									</tr>
								}

							</tbody>
						</Table>
					</Col>
				</Row>
				<Pagination
					fecthData={(size, page) => getUsers(size, page)}
					page={users.page}
					size={5}
					totalPages={users.totalPages}
				/>

				<Modal isOpen={modal} toggle={toggle} className="secondary">
					<ModalHeader toggle={toggle}>Usuário autenticado</ModalHeader>
					<ModalBody>
						Usuario autenticado, agora você pode votar em uma sessão!
					</ModalBody>
					<ModalFooter className="text-center">
						<a className="btn btn-link" href="/topics">ir para pautas</a>
						<Button color="secondary" onClick={toggle}>OK</Button>
					</ModalFooter>
				</Modal>
			</Container>
		</>
	);
}

export default Users;