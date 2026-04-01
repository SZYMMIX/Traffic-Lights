import React from 'react';

export const pageStyle: React.CSSProperties = {
    display: 'flex',
    height: '100vh',
    background: '#ffffff',
    fontFamily: 'Inter, system-ui, sans-serif'
};

export const sidebar: React.CSSProperties = {
    width: '280px',
    padding: '40px 30px',
    borderRight: '1px solid #e0e0e0',
    background: '#24242B',
    display: 'flex',
    flexDirection: 'column',
    gap: '30px',
    color: '#333'
};

export const viewPort: React.CSSProperties = {
    flexGrow: 1,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: '60px',
    background: '#393945'
};

export const grid: React.CSSProperties = {
    display: 'grid',
    gridTemplateAreas: '". N ." "W C E" ". S ."',
    gridTemplateColumns: '200px 200px 200px',
    gridTemplateRows: '280px 200px 280px',
    alignItems: 'center',
    justifyItems: 'center',
    gap: '20px'
};

export const centerSquare: React.CSSProperties = {
    gridArea: 'C',
    width: '280px',
    height: '280px',
    background: '#2a2a2a',
    position: 'relative',
    border: 'none',
    borderRadius: '4px',
    zIndex: 1,
    boxShadow: 'inset 0 0 20px rgba(0,0,0,0.5)'
};

export const mainBtn: React.CSSProperties = {
    padding: '16px',
    background: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
    fontWeight: 'bold',
    fontSize: '16px',
    boxShadow: '0 2px 4px rgba(0,123,255,0.3)',
    transition: 'transform 0.2s, box-shadow 0.2s'
};

export const resetBtn: React.CSSProperties = {
    padding: '12px',
    background: '#AD2D2D',
    color: '#fff',
    border: '1px solid #999',
    borderRadius: '8px',
    cursor: 'pointer',
    fontWeight: 'bold',
    transition: 'all 0.2s'
};

export const sidebarTitle: React.CSSProperties = {
    margin: 0,
    color: '#fff'
};

export const sidebarSubtitle: React.CSSProperties = {
    fontSize: '12px',
    color: '#666'
};

export const roadSectionWrapper: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    position: 'relative',
    zIndex: 10
};


