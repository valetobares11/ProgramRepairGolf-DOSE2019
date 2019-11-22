import React,{Component} from "react";

export default class CreateAccount extends Component{
  state = {
    username: null,
    password: null
  
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
    this.props.login(this.state.username, this.state.password);
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
              <button type="submit"> Crear Cuenta </button>
            </form>
         </div>
 		
     );
  }
}


 

