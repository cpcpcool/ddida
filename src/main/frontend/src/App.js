import './App.css';
import './reset.css';
import './layout.css';
import React, { useEffect, useRef } from 'react';
import { ConfigProvider, Row, Col, Collapse } from 'antd';
import axios from 'axios';

function App() {

	const mapContainerRef = useRef(null);

	useEffect(() => {
		// initMap 함수에서 지도 초기화
		// eslint-disable-next-line no-undef
		const initMap = () => {
			const mapOptions = {
				center: new naver.maps.LatLng(37.3595704, 127.105399),
				zoom: 15
			};

			const map = new naver.maps.Map(mapContainerRef.current, mapOptions);
		};

		// 호출
		initMap();
	}, []); // 빈 배열을 전달하여 한 번만 실행되도록 설정
	window.navermap_authFailure = function () {
		// 인증 실패 시 처리 코드 작성
		console.log('인증실패');
	}

	/* 콜랩스 */
	const text = `와아 리액트다`;
	const items = [
		{
			key: '1',
			label: '위치 검색',
			children: <p>{text}</p>,
		},
		{
			key: '2',
			label: '검색 결과',
			children: <p>{text}</p>,
		},
		{
			key: '3',
			label: 'etc',
			children: <p>{text}</p>,
		},
	];

	const onChange = (key) => {
		console.log(key);
	};

	return (
		<>
			<ConfigProvider
				theme={{
					token: {
						// Seed Token
						colorPrimary: '#2FA599',
						borderRadius: 0,
						fontFamily: 'SpoqaHanSansNeo',

						// Alias Token
						colorBgContainer: '#fefefe',
					},
				}}
			>
				<Row style={{ maxWidth: '1200px', margin: '0 auto', }}>
					<Col span={7} style={{ backgroundColor: '#ddd', }}>
						<div>
							<Collapse items={items} defaultActiveKey={['1']} onChange={onChange} />
						</div>
					</Col>
					<Col span={17} style={{ backgroundColor: '#aaa', minHeight: '800px', }}>
						<div ref={mapContainerRef} style={{ width: '850px', height: '800px' }}></div>

					</Col>
				</Row>
			</ConfigProvider>
		</>

	)
};

export default App