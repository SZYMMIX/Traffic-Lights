import React from 'react';
import type { LightState } from '../types';
import { containerStyle, bulb } from '../styles/trafficLightStyles';
interface Props { state: LightState; }

export const TrafficLight: React.FC<{ state: LightState, rotation: string  }> = ({ state, rotation }) => {
    const isRed = state === 'RED' || state === 'RED_YELLOW';
    const isYellow = state === 'YELLOW' || state === 'RED_YELLOW';
    const isGreen = state === 'GREEN';

    return (
        <div style={{...containerStyle, transform: 'rotate(180deg)'}}>
            <div style={{ ...bulb, backgroundColor: isRed ? '#ff3b30' : '#333' }} />
            <div style={{ ...bulb, backgroundColor: isYellow ? '#ffcc00' : '#333' }} />
            <div style={{ ...bulb, backgroundColor: isGreen ? '#4cd964' : '#333' }} />
        </div>
    );
};
