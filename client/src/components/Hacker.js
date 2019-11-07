import React, { Component } from 'react'
import { connect } from 'react-redux';
import { fetchHacker } from '../actions/hackersActions'

class Hacker extends Component {
  componentDidMount() {
    this.props.fetchHacker(this.props.match.params.hacker_id)
  }

  render() {
    const { hacker } = this.props
    return (
      hacker ? (
        <div className="container">
          <h1>You're the hacker</h1>
          <h2>#{this.props.hacker.id}</h2>
          <h4>{this.props.hacker.name}</h4>
        </div>
      ) : (
        <div>There is no hacker in the store... refetching</div>
      )
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    hacker: state.hacker.data
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchHacker: (id) => {
      dispatch(fetchHacker(id))
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Hacker)
