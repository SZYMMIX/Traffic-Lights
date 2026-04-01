import React from 'react';

export const laneStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    width: '70px',
    height: '320px',
    gap: '8px',
    zIndex: '100'
};

export const headerStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    gap: '6px'
};

export const labelStyle: React.CSSProperties = {
    fontSize: '16px',
    fontWeight: 'bold',
    color: '#fff',
    background: '#333',
    padding: '4px 10px',
    borderRadius: '4px'
};

export const roadSurface: React.CSSProperties = {
    flexGrow: 1,
    width: '60px',
    background: '#2a2a2a',
    position: 'relative',
    borderLeft: '3px solid #666',
    borderRight: '3px solid #666',
    borderRadius: '4px',
    overflow: 'visible',
    boxShadow: 'inset 0 0 8px rgba(0,0,0,0.4)'
};

export const car: React.CSSProperties = {
    position: 'absolute',
    left: '50%',
    transform: 'translateX(-50%)',
    width: '24px',
    height: '44px',
    background: '#ff3b30',
    borderRadius: '4px',
    transition: 'bottom 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94), opacity 0.5s',
    boxShadow: '0 2px 6px rgba(0,0,0,0.3)',
    border: '1px solid #cc0000'
};

export const addBtn: React.CSSProperties = {
    border: 'none',
    background: '#446CE3',
    color: '#fff',
    cursor: 'pointer',
    borderRadius: '6px',
    padding: '8px 12px',
    fontWeight: 'bold',
    fontSize: '14px',
    transition: 'all 0.2s',
    boxShadow: '0 2px 4px rgba(68,108,227,0.3)'
};