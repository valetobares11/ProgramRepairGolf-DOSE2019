import React,{Component} from "react";

export default class CreateAccount extends Component{
  state = {
    username: '',
    password: '',
    email: ''
  }

  constructor(){
    super();
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange = (e) => {
    // console.log(e.target.id, e.target.value);
    this.setState({
      [e.target.id]: e.target.value
    });
  }

  handleSubmit = (e) => {
    //console.log(this.state.username);
    this.props.newAccount(this.state.username, this.state.password, this.state.email);
    e.preventDefault();
  }

  render () {
    return (
      <div>
            <form onSubmit={this.handleSubmit}>
               <label htmlFor="username">Username:</label>
                <input type="text" id="username" onChange={this.handleChange} />
               <label htmlFor="password">Password:</label>
                <input type="text" id="password" onChange={this.handleChange} />  
                <label htmlFor="email">Email:</label>
                <input type="text" id="email" onChange={this.handleChange} />
              
            <button type="submit"> Crear Cuenta </button>
            </form>
         </div>
 		
     );
  }
}


 

