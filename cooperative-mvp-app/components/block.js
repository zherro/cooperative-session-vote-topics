import { useEffect, useState } from "react";

const Block = ({ show }) => {


	const [agoraSim, setAgoraAssim] = useState(false);

	function sleep(ms) {
		return new Promise(resolve => setTimeout(resolve, ms));
	}

	useEffect(async () => {
		if (!show)
			await sleep(1000)
		setAgoraAssim(show)
	}, [show])

	return <>
		<div id="block-screem"
			style={{
				display: agoraSim ? 'block' : 'none'
			}}>
			<div style={
				{
					paddingTop: '40vh',
					margin: '0 auto 0 auto',
					textAlign: 'center',
					width: '100%',
					position: 'absolute',
					top: 0, left: 0, right: 0, bottom: 0,
					backgroundColor: 'rgba(5, 64, 158, 15%)',
					zIndex: '99999999999000000'
				}
			}>
				<img style={{
				}} src="/clock.svg" width="150" />
			</div>
		</div>
	</>
}

export default Block;