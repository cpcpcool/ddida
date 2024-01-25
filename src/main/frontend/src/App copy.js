import './App.css';
import './reset.css';
import './layout.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from 'axios';

import Mapview from './view/Mapview';

function App() {
	const [message, setMessage] = useState([]);
	useEffect(() => {
		fetch("/api/hello")
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				setMessage(data);
			});
	}, []);
	return (
		<div className="App">
			<div>
				<h1>뛰맵</h1>
				<Router>
					<Routes>
						{/* 요청된 경로로 페이지 이동 : 특정 컴포넌트 실행 */}
						<Route path="/" element={<Mapview />} />
					</Routes>
				</Router>
			</div>
		</div>
	);
}

export default App;
