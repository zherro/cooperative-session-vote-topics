import React from "react";

const Pagination = ({ fecthData, size, page, totalPages }) => {

	const runFetch = (page) => {
		let p = page <= 0 ? 1 : page;
		p = p > totalPages ? totalPages : p;
		fecthData(size, p);
	}

	return <nav aria-label="Page navigation example" className="float-end">
		
		<ul className="pagination float-end">
			<li className={'page-item ' + (page == 1 ? 'disabled' : '')}>
				<a className="page-link" onClick={() => runFetch(page - 1)} aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<li className={'page-item ' + (page == totalPages ? 'disabled' : '')}>
				<a className="page-link" onClick={() => runFetch(page + 1)} aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</ul>

		<small className="float-end m-2 mx-3">{'Página ' + page + ' de ' + totalPages}</small>
	</nav>
}

export default Pagination;