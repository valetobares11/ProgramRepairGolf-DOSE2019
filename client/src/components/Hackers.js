import React from 'react'
import { Link } from 'react-router-dom'
import AddHacker from './AddHacker';

const Hackers = ({ hackers, addHacker, deleteHacker }) => {
    const hackerList = hackers.map(hacker => {
      const id = hacker.url.slice(0, -1).split('/').pop();
      return (
        <div className="hacker card" key={hacker.url}>
          <div className="card-content">
            <span className="card-title">
              <Link to={'/' + id} hacker={hacker}>{ hacker.name }</Link>
            </span>
            <p>Skills: { hacker.skill }</p>
            <div>Age: { hacker.age }</div>
            <button onClick={() => deleteHacker(hacker.id)}>Delete</button>
          </div>
        </div>
      )
    });

    return (
      <div className="post">
        <div className="hacker-list">
          { hackerList }
        </div>
        <AddHacker addHacker={addHacker}/>
      </div>
    );
  }

  export default Hackers