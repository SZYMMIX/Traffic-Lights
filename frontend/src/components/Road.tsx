import React from 'react';
import {Lane} from './Lane';
import { RoadDirection, WayType, LightState, LaneSnapshot } from '../types';

interface Props {
    dir: RoadDirection;
    lanes: Record<WayType, LaneSnapshot>;
    onAddVehicle: (dir: RoadDirection, type: WayType) => void;
}

const Road: React.FC<Props> = ({ dir, lanes, onAddVehicle }) => {
    const rotation = {
        NORTH: 'rotate(180deg)',
        SOUTH: 'rotate(0deg)',
        EAST: 'rotate(270deg)',
        WEST: 'rotate(90deg)'
    }[dir];

    return (
        <div className="road-container" style={{ transform: rotation }}>
            <div className="lanes-wrapper">
                <Lane direction={dir} type="LEFT_TURN" data={lanes.LEFT_TURN} onAdd={() => onAddVehicle(dir, 'LEFT_TURN')} />
                <Lane direction={dir} type="FORWARD" data={lanes.FORWARD} onAdd={() => onAddVehicle(dir, 'FORWARD')} />
                <Lane direction={dir} type="RIGHT_TURN" data={lanes.RIGHT_TURN} onAdd={() => onAddVehicle(dir, 'RIGHT_TURN')} />
            </div>
            <style>{`
        .road-container { width: 156px; display: flex; justify-content: center; }
        .lanes-wrapper { display: flex; background: #333; padding: 2px; border-radius: 4px; border-bottom: 5px solid #555; }
      `}</style>
        </div>
    );
};

export default Road;