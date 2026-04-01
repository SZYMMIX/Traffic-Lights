export type RoadDirection = 'NORTH' | 'SOUTH' | 'EAST' | 'WEST';
export type WayType = 'FORWARD' | 'LEFT_TURN' | 'RIGHT_TURN';
export type LightState = 'GREEN' | 'YELLOW' | 'RED' | 'RED_YELLOW';

export interface LaneSnapshot {
    light: LightState;
    vehicleCount: number;
}

export interface IntersectionSnapshot {
    lanes: Record<RoadDirection, Record<WayType, LaneSnapshot>>;
}