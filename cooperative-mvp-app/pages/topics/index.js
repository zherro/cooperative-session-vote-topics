import axios from "axios";
import { useRouter } from "next/dist/client/router";
import { route } from "next/dist/next-server/server/router";
import { useEffect, useState } from "react";
import { Alert, Button, Col, Container, NavLink, Row, Table } from "reactstrap"
import Header from "../../components/header"
import Pagination from "../../components/Pagination";
import { getApi, patchApi, postApi } from "../../const/apiCall";
import CONFIG_API from "../../const/confi";
import makeid from "../../const/randonId";

const Users = () => {

	const [users, setData] = useState({})
	const [errorMessage, setErrorMessage] = useState('');
	const router = useRouter();

	useEffect(async () => {
		const res = getApi(`pauta?size=5`, setData, errorMessage, setErrorMessage)
	}, []);

	const closeTopic = async (event, topic) => {
		await patchApi(event, 'pauta/' + topic, errorMessage, setErrorMessage, '', {}, router);
		fetchData( users.size, users.page)
	}

	const fetchData = async ( size, page) => {
		await getApi(`pauta?size=${size}&page=${page}`, setData, errorMessage, setErrorMessage)
	}

	const openSession = async (event, topicId) => {
		const data = {
			durationMinutes: 5,
			info: "Sessão de votação",
			name: "COD: " + makeid(5),
			topicId: topicId
		}
		await postApi(
			event, 'sessao',
			errorMessage, setErrorMessage,
			'/topics/' + topicId, data, router
		)
	}

	const lpad = (value) => {
		return value < 10 ? '0' + value : value
	}

	return (
		<>
			<Container>
				<Header />
				<div style={{ maxWidth: '400px', margin: "0 auto" }}>
					<h3 className="text-center my-3  pb-3">Pautas</h3>
				</div>
				<div className="border-bottom mb-3"></div>
				<Row>
					<Col className="text-center">
						<a href="/topics/form" style={{ maxWidth: '200px' }} className="btn btn-success text-white" >
							+ Adicionar Pauta
						</a>
					</Col>
				</Row>
				{
					errorMessage?.length > 0 &&
					<div className="mt-3">
						<Alert color="warning">
							{errorMessage}
						</Alert>
					</div>
				}
				<Row>
					<Col>
						<Table striped className="mt-3">
							<thead>
								<tr>
									<th>#</th>
									<th>Tema</th>
								</tr>
							</thead>
							<tbody>
								{
									users?.content?.map((u) => {
										return <>
											<tr>
												<td></td>
												<td>
													{u.theme}
												</td>
											</tr>
											<tr>
												<td>
												</td>
												<td>
													{u.open &&
														<Row>
															<Col sm={12} md={6}>
																{
																	!u.session &&
																	<div className="text-danger">Nenhuma sessão ativa registrada!</div>
																}
																{
																	u.session && u.timeLeft?.started &&
																	<>
																		<small className="text-success float-start">Sessão em andamento!</small>
																		<small className="float-end"><b>Duração:</b> {u.session.durationMinutes} minutos</small>
																		<br />
																		<small className="float-start">
																			<b>{ u.timeLeft?.started ? 'Tempo restante: ' : 'Abertura em: ' }</b>
																			{u.timeLeft?.days > 0 ? u.timeLeft.days + ' dias, ' : ''}
																			{u.timeLeft?.hours > 0 ? u.timeLeft.days + ' horas e ' : ''}
																			{u.timeLeft?.minutes > 0
																				|| u.timeLeft?.seconds > 0
																				? lpad(u.timeLeft.minutes) + ':' + lpad(u.timeLeft.seconds) + ' minutos '
																				: ''
																			}
																		</small>
																	</>
																}
															</Col>
															<Col sm={12} md={6}>
																<div className="float-end">
																	<Button onClick={(e) => router.push('/topics/' + u.id)} color="primary" size="sm" className="mx-2" >Votar</Button>
																	<Button onClick={(e) => openSession(e, u.id)} color="success" size="sm" >Criar sessão</Button>
																	<Button onClick={(e) => closeTopic(e, u.id)} className="mx-2" size="sm" >Fechar Pauta</Button>
																</div>
															</Col>
														</Row>
													}
													{
														!u.open && u.result.length <= 0
															&& <small className="badge bg-danger">Pauta revogada</small>
													}
													{
														!u.open && u.result.length > 0
															&& <>
																<small className="float-start badge bg-success">Pauta fechada</small>
																<div className="float-start mx-3">
																	{
																		u.result.map((v) => {
																			if(v.vote === 'YES') {
																				return <span className="text-success px-3"> {v.total} <b>SIM</b></span>
																			}
																			return <span className="px-3"> {v.total} <b>NÃO</b></span>
																		})
																	}
																	
																</div>
															</>
													}
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
					<Pagination
						fecthData={(size, page) => fetchData(size, page)}
						page={users.page}
						size={5}
						totalPages={users.totalPages}
					/>
				</Row>
			</Container>
		</>
	);
}

export default Users;