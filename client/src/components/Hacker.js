import React, { Component } from 'react'

class Hacker extends Component {
  state = {
    id: null
  }

  componentDidMount(){
    let id = this.props.match.params.hacker_id;
    this.setState({
      id
    })
  }

  render() {
    return (
      <div className="container">
        Congratullations, you're the hacker #<span>{this.state.id}</span>
      </div>
    )
  }
}

export default Hacker