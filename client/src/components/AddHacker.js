import React, { Component } from 'react'

export default class AddHacker extends Component {
  state = {
    name: null,
    heigth: null,
    url: null
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
          <label htmlFor="age">heigth:</label>
          <input type="text" id="heigth" onChange={this.handleChange} />
          <label htmlFor="skill">url:</label>
          <input type="text"id="url" onChange={this.handleChange} />

          <button>Submit</button>
        </form>
      </div>
    )
  }
}