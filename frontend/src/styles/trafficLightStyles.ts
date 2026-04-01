import React from 'react';

export const containerStyle: React.CSSProperties = {
    background: '#000',
    padding: '8px',
    borderRadius: '8px',
    display: 'flex',
    flexDirection: 'column',
    gap: '6px',
    width: '32px',
    height: '100px',
    border: '2px solid #222',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.7), inset 0 0 5px rgba(0,0,0,0.5)',
    alignItems: 'center',
    justifyContent: 'center'
};

export const bulb: React.CSSProperties = {
    width: '24px',
    height: '24px',
    borderRadius: '50%',
    boxShadow: '0 0 8px currentColor, inset 0 -2px 4px rgba(0,0,0,0.3)',
    transition: 'all 0.3s',
    flexShrink: 0
};

