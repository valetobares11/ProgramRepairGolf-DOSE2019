import React, { Component } from 'react'
import axios from 'axios';

class Hacker extends Component {
  state = {
    id: null,
    url: null,
    heigth: null
  }

  componentDidMount(){
    const id = this.props.match.params.hacker_id;

    axios.get('https://swapi.co/api/people/' + id)
      .then(res =>
        this.setState({
          ...res.data
        })
      )
  }

  render() {
    return (
      <div className="container">
        Congratullations, you're the hacker <span>{this.state.name}</span>
      </div>
    )
  }
}

export default Hacker