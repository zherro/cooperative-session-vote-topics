import axios from "axios";
import { useRouter } from "next/dist/client/router";
import { useEffect, useState } from "react";
import { Alert, Button, Col, Container, NavLink, Row, Table } from "reactstrap"
import Block from "../../components/block";
import Header from "../../components/header"
import Pagination from "../../components/Pagination";
import { getApi, patchApi } from "../../const/apiCall";

const Users = () => {

	const [data, setData] = useState({})
	const [errorMessage, setErrorMessage] = useState('');
	const router = useRouter();
	const [block, setBlock] = useState(false)

	useEffect(async () => {
		getApi(setBlock, `sessao?size=${5}`, setData, errorMessage, setErrorMessage)
	}, []);

	const fetchData = async(size, page) => {
		getApi(setBlock, `sessao?size=${size}&page=${page}`, setData, errorMessage, setErrorMessage)
	}

	return (
		<>
		<Block  show={block} />
			<Container>
				<Header />
				<div style={{ maxWidth: '400px', margin: "0 auto" }}>
					<h3 className="text-center my-3  pb-3">Sessões</h3>
				</div>
				<div className="border-bottom mb-3"></div>
				<Row>
					<Col className="text-center">
						<a href="/topics" style={{ maxWidth: '200px' }} className="btn btn-secondary text-white" >
							Ver pautas
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
									<th>Info</th>
									<th>Tema</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								{
									data?.content?.map((u) => {
										return <tr>
											<td>{u.name}</td>
											<td>{u.info}</td>
											<td>{u.topic.theme}</td>
											<td>
												<div className={'d-flex align-items-center align-self-center badge bg-' + (
													!u.active ? 'danger'
														: u.running ? 'primary'
															: 'danger'
												)
												}>
													{
														!u.active
															? 'Cancelado'
															: u.running
																? 'Em andamento'
																: 'Encerrado'
													}
												</div>
												{
													(u.running || !u.ended) &&
													u.active && <>
														<br />
														<a className="btn btn-link" href={'/topics/' + u.topic.id}>ver pauta</a>
													</>

												}

											</td>
										</tr>
									})
								}
								{
									!data?.content &&
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
					fecthData={(size, page) => fetchData(size, page)}
					page={data.page}
					size={5}
					totalPages={data.totalPages}
				/>
			</Container>
		</>
	);
}

export default Users;