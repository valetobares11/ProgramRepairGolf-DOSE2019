import React, { Component } from 'react'

export default class AddHacker extends Component {
  state = {
    name: null,
    age: null,
    skill: null
  }

  handleChange = (e) => {
    // console.log(e.target.id, e.target.value);
    this.setState({
      [e.target.id]: e.target.value
    });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.addHacker(this.state);
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <label htmlFor="name">Name:</label>
          <input type="text" id="name" onChange={this.handleChange} />
          <label htmlFor="age">Age:</label>
          <input type="text" id="age" onChange={this.handleChange} />
          <label htmlFor="skill">Skill:</label>
          <input type="text"id="skill" onChange={this.handleChange} />

          <button>Submit</button>
        </form>
      </div>
    )
  }
}