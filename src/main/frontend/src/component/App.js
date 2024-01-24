import '../../src/App.css';
import '../../src/reset.css';
import '../../src/layout.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Mapview from './view/Mapview';

function App() {
	return (
		<div className="App">

			<Router>
				<Routes>
					{/* 요청된 경로로 페이지 이동 : 특정 컴포넌트 실행 */}
					<Route path="" element={<Mapview />} />
				</Routes>
			</Router>

		</div>
	);
}

export default App;
