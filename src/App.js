import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import './App.css';
import TeamMember from './TeamMember';
import MessagePage from './MessagePage';

function App() {
  return (
    <Router>
      <div className="app-container">
        <header className="app-header">
          <nav className="navbar">
            <ul className="navbar-list">
              <li className="navbar-item">
                <Link to="/" className="navbar-link">Team Member</Link>
              </li>
              <li className="navbar-item">
                <Link to="/messages" className="navbar-link">Messages</Link>
              </li>
            </ul>
          </nav>
        </header>
        <main>
          <Routes>
            <Route path="/" element={<TeamMember />} />
            <Route path="/messages" element={<MessagePage />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
