import React from 'react'
//import { Link } from 'react-router-dom'
import CreateAccount from './CreateAccount';

const Users = ({ users, newAccount, deleteUser }) => {
    // const userList = users.map(user => {
    //   const id = user.username.slice(0, -1).split('/').pop();
    //   return (
    //     <div className="user card" key={user.username}>
    //       <div className="card-content">
    //         <span className="card-title">
    //           <Link to={'/' + id} user={user}>{ user.username }</Link>
    //         </span>
    //         <p>Username: { user.username }</p>
    //         <button onClick={() => deleteUser(user.id)}>Delete</button>
    //       </div>
    //     </div>
    //   )
    // });

    const userList = [];
    return (
      <div className="post">
        <div className="user-list">
          { userList }
        </div>
        <CreateAccount newAccount={newAccount}/>
      </div>
    );
  }

  export default Users