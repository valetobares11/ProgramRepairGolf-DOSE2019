import React from 'react';
import { Link, NavLink, withRouter } from 'react-router-dom'

const Navbar = (props) => {
  return (
    <nav className="nav-wrapper red darken-3">
    <div className="container">
      <Link className="brand-logo" to="/">Program Repair Game</Link>
      <ul className="right">
        <li><NavLink exact to="/">Home</NavLink></li>
        <li><NavLink to='/about'>About</NavLink></li>
        <li><NavLink to='/hackers'>Hackers</NavLink></li>
      </ul>
    </div>
  </nav> 
  )
}

export default withRouter(Navbar)