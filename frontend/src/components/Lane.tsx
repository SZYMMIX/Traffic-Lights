import React, { useEffect, useState } from 'react';
import {TrafficLight} from './TrafficLight'
import type { RoadDirection, WayType, LightState, LaneSnapshot } from '../types';
import { laneStyle, headerStyle, labelStyle, roadSurface, car, addBtn } from '../styles/laneStyles';

interface Props {
    type: WayType;
    data: LaneSnapshot;
    direction: RoadDirection;
    onAdd: () => void;
}

export const Lane: React.FC<Props> = ({ type, data, direction, onAdd }) => {
    const label = type === 'FORWARD' ? '↓' : type === 'LEFT_TURN' ? '>' : '<';
    const [prevCount, setPrevCount] = useState(data.vehicleCount);
    const [leavingCars, setLeavingCars] = useState<number[]>([]);

    const inverseRotation = {
        NORTH: 'rotate(0deg)',
        SOUTH: 'rotate(180deg)',
        EAST: 'rotate(-90deg)',
        WEST: 'rotate(90deg)'
    }[direction];

    useEffect(() => {
        if (data.vehicleCount < prevCount) {
            const leavingId = Date.now();
            setLeavingCars(prev => [...prev, leavingId]);
            setTimeout(() => {
                setLeavingCars(prev => prev.filter(id => id !== leavingId));
            }, 1000);
        }
        setPrevCount(data.vehicleCount);
    }, [data.vehicleCount, prevCount]);

    return (
        <div style={laneStyle}>
            <style>{`
                @keyframes leaveIntersection {
                    0% { bottom: 10px; opacity: 1; }
                    100% { bottom: -200px; opacity: 0; }
                }
                .car-leaving {
                    animation: leaveIntersection 1s cubic-bezier(0.25, 0.46, 0.45, 0.94) forwards !important;
                    z-index: 100;
                }
                .car-queue-badge {
                    position: absolute;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%) ${inverseRotation};
                    color: white;
                    font-weight: bold;
                    font-size: 14px;
                    z-index: 10;
                }
            `}</style>
            
            <div style={headerStyle}>
                <button onClick={onAdd} style={addBtn}>+</button>
                <span style={labelStyle}>{label}</span>
            </div>

            <div style={roadSurface}>
                {Array.from({ length: Math.min(data.vehicleCount, 2) }).map((_, i) => (
                    <div key={`car-${i}`} style={{ ...car, bottom: i * 50 + 10 }}>
                        {i === 1 && data.vehicleCount > 2 && (
                            <div className="car-queue-badge">+{data.vehicleCount - 2}</div>
                        )}
                    </div>
                ))}
                {leavingCars.map(id => (
                    <div key={`leaving-${id}`} className="car-leaving" style={{ ...car }} />
                ))}
            </div>

            <TrafficLight state={data.light} />
        </div>
    );
};