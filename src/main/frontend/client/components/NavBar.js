import React from "react"
import { Link } from "react-router-dom"

const NavBar = () => {

  return (
    <div className="title-bar">
      <div className="top-bar-center">
        <ul className="menu">
          <li>
            <Link to="/pets">Home</Link>
          </li>
          <li>
            <Link to="/pets/Leeches">Leeches</Link>
          </li>
          <li>
            <Link to="/pets/Red%20Garras">Red Garras</Link>
          </li>
          <li>
            <Link to="/adoptions/new">Surrender Your Pet</Link>
          </li>
          <li>
            <Link to="/pending_applications">Pending Adoptions</Link>
          </li>
          <li>
            <Link to="/pets/happy_adopted_animals">Our Successful Adoptions</Link>
          </li>
        </ul>
      </div>
    </div>
  )
}

export default NavBar
