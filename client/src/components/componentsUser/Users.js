import React from 'react'
//import { Link } from 'react-router-dom'
//import CreateAccount from './CreateAccount';
import Login from './Login';

const Users = ({ users, newAccount, deleteUser, login}) => {
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
       
        <Login login={login}/>
      </div>
    );
  }

  export default Users