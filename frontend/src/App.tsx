import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Lane } from './components/Lane';
import type { RoadDirection, WayType, LightState, LaneSnapshot, IntersectionSnapshot } from './types';
import { pageStyle, sidebar, viewPort, grid, centerSquare, mainBtn, resetBtn, sidebarTitle, roadSectionWrapper } from './styles/appStyles';

const API_BASE = "http://localhost:8080/api/simulation";

const App: React.FC = () => {
    const [state, setState] = useState<IntersectionSnapshot | null>(null);
    const [vId, setVId] = useState(1);

    const fetchState = async () => {
        try {
            const res = await axios.get(`${API_BASE}/state`);
            setState(res.data);
        } catch (err) { console.error(err); }
    };

    const handleAdd = async (start: RoadDirection, type: WayType) => {
        const targets: Record<RoadDirection, Record<WayType, RoadDirection>> = {
            NORTH: { FORWARD: 'SOUTH', LEFT_TURN: 'EAST', RIGHT_TURN: 'WEST' },
            SOUTH: { FORWARD: 'NORTH', LEFT_TURN: 'WEST', RIGHT_TURN: 'EAST' },
            EAST: { FORWARD: 'WEST', LEFT_TURN: 'SOUTH', RIGHT_TURN: 'NORTH' },
            WEST: { FORWARD: 'EAST', LEFT_TURN: 'NORTH', RIGHT_TURN: 'SOUTH' }
        };
        await axios.post(`${API_BASE}/add-vehicle`, { id: `v${vId}`, start, end: targets[start][type] });
        setVId(vId + 1);
        fetchState();
    };

    const doStep = async () => {
        try {
            const res = await axios.post(`${API_BASE}/step`);
            setState(res.data.state);
        } catch (err) {
            console.error('Step failed:', err);
        }
    };

    const handleReset = async () => {
        try {
            const res = await axios.post(`${API_BASE}/reset`);
            setState(res.data);
            setVId(1);
        } catch (err) {
            console.error('Reset failed:', err);
        }
    };

    useEffect(() => { fetchState(); }, []);

    if (!state) return <div style={{ padding: '50px' }}>Loading...</div>;

    return (
        <div style={pageStyle}>
            <div style={sidebar}>
                <h2 style={sidebarTitle}>Traffic Simulation</h2>
                <button onClick={doStep} style={mainBtn}>NEXT STEP</button>
                <button onClick={handleReset} style={resetBtn}>Reset</button>
            </div>

            <div style={viewPort}>
                <div style={grid}>
                    <div style={{ ...roadSectionWrapper, gridArea: 'N', justifyContent: 'center', marginBottom: '60px' }}>
                        <div style={{ transform: 'rotate(0deg)' }}>
                            <RoadSection dir="NORTH" data={state.lanes.NORTH} onAdd={handleAdd} />
                        </div>
                    </div>
                    <div style={{ ...roadSectionWrapper, gridArea: 'W', justifyContent: 'flex-end', marginRight: '60px' }}>
                        <div style={{ transform: 'rotate(-90deg)' }}>
                            <RoadSection dir="WEST" data={state.lanes.WEST} onAdd={handleAdd} />
                        </div>
                    </div>
                    <div style={centerSquare}>
                    </div>
                    <div style={{ ...roadSectionWrapper, gridArea: 'E', justifyContent: 'flex-start', marginLeft: '60px' }}>
                        <div style={{ transform: 'rotate(90deg)' }}>
                            <RoadSection dir="EAST" data={state.lanes.EAST} onAdd={handleAdd} />
                        </div>
                    </div>
                    <div style={{ ...roadSectionWrapper, gridArea: 'S', justifyContent: 'center', marginTop: '60px' }}>
                        <div style={{ transform: 'rotate(180deg)' }}>
                            <RoadSection dir="SOUTH" data={state.lanes.SOUTH} onAdd={handleAdd} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

const RoadSection = ({ dir, data, onAdd }: any) => (
    <div style={{ display: 'flex', gap: '2px', background: '#1a1a1a', padding: '6px', borderRadius: '6px', boxShadow: '0 2px 8px rgba(0,0,0,0.2)' }}>
        <Lane type="LEFT_TURN" data={data.LEFT_TURN} onAdd={() => onAdd(dir, 'LEFT_TURN')} />
        <Lane type="FORWARD" data={data.FORWARD} onAdd={() => onAdd(dir, 'FORWARD')} />
        <Lane type="RIGHT_TURN" data={data.RIGHT_TURN} onAdd={() => onAdd(dir, 'RIGHT_TURN')} />
    </div>
);

export default App;