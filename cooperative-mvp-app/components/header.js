import { useRouter } from "next/dist/client/router";
import { useEffect, useState } from "react";
import { Col, Row, Button } from "reactstrap";

const Header = ({ updateUserInfo }) => {
	const router = useRouter();
	const [userInfo, setUserInfo] = useState({});

	const getUserInfo = () => {
		return {
			userId: localStorage.getItem('userId'),
			userName: localStorage.getItem('userName'),
			userDoc: localStorage.getItem('userDoc'),
			userLogin: localStorage.getItem('userLogin'),
		}
	}

	const logout = () => {		
		localStorage.removeItem('userId');
		localStorage.removeItem('userName');
		localStorage.removeItem('userDoc');
		localStorage.removeItem('userLogin');
		setUserInfo(getUserInfo());
	}

	useEffect(() => {
		setUserInfo(getUserInfo())
	}, []);

	useEffect(() => {
		setUserInfo(getUserInfo())
	}, [updateUserInfo])

	return (
		<>
			<Row>
				<Col className="text-end p-3">
					{
						userInfo?.userId &&
						<>
							<span><b>{userInfo.userName}</b></span>
							<br />
							<small><b>doc: </b> {userInfo.userDoc}</small>
							<br />
							<button onClick={() => logout()} className="p-0 btn btn-link">Sair</button>
						</>
					}
					{
						!userInfo.userId &&
						<>
							<span className="text-danger">Não há usuário autenticado!</span>
							<br />
							<a className="btn btn-link" href="/users">Login</a>
						</>
					}
				</Col>
			</Row>

			<Row>
				<Col className="text-center my-3  pb-3 border-bottom">
					<Button onClick={() => router.push('/users')} color="primary" className="mx-3" outline>
						Usuários
					</Button>
					<Button onClick={() => router.push('/topics')} color="primary" className="mx-3" outline>
						Pautas
					</Button>
					<Button onClick={() => router.push('/sessions')} color="primary" className="mx-3" outline>
						Sessões
					</Button>
				</Col>
			</Row>
		</>
	)
}

export default Header;