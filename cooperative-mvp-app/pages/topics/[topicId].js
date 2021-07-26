import { useRouter } from "next/dist/client/router"
import { useEffect, useState } from "react"
import { Alert, Button, Col, Container, Row } from "reactstrap"
import Header from "../../components/header"
import { getApi, postApi } from "../../const/apiCall"

const Session = () => {
	const router = useRouter()
	const { topicId } = router.query
	const [data, setData] = useState(null)
	const [errorMessage, setErrorMessage] = useState('');
	const [okMessage, setOkMessage] = useState(false);


	useEffect(() => {
		if (topicId)
			getApi('sessao/opened/' + topicId, setData, errorMessage, setErrorMessage)
	}, [topicId]);

	const registerVote = async (e, vote) => {
		let d = {
			session: data.id,
			user: localStorage.getItem('userId'),
			vote: vote
		};
		await postApi(e, 'voto',
			errorMessage, setErrorMessage,
			'', d, router);

		if (!errorMessage || errorMessage.length <= 0) {
			setOkMessage(true);
		}
	}
	return <>
		<Container>
			<Header />

			<div style={{ maxWidth: '400px', margin: "0 auto" }}>
				<h3 className="text-center my-3  pb-3">Votação da pauta:
					<span className="text-primary"> {data?.topic?.theme}</span>
				</h3>
			</div>
			<div className="border-bottom mb-3"></div>
			{
				(okMessage || errorMessage?.length > 0) &&
				<div className="mt-3">
					<Alert color={okMessage && errorMessage?.length <= 0 ? 'success' : 'warning'}>
						{errorMessage}
						{
							okMessage && errorMessage?.length <= 0 &&
							'Seu voto foi registrado!'
						}
					</Alert>
				</div>
			}
			{data && <>
				<Row>
					<Col>
						<p><b>Descrição: </b>{data?.topic?.description}</p>
					</Col>
				</Row>
				<Row>
					<Col>
						<p><b>Sessão: </b>{data?.name}</p>
					</Col>
				</Row>
				<Row>
					<Col>
						<p><b>Info: </b>{data?.info}</p>
					</Col>
				</Row>
			</>
			}
			<Row>
				<Col className="text-center">
					{data && <>
						<Button onClick={(e) => registerVote(e, 'YES')} className="m-2" color="success">Concordo</Button>
						<Button onClick={(e) => registerVote(e, 'NO')} className="m-2" color="danger">Discordo</Button>
					</>
					}
					<br />
					<a className="btn btn-link" href="/topics">voltar para pautas</a>
				</Col>
			</Row>
		</Container>
	</>
}

export default Session;