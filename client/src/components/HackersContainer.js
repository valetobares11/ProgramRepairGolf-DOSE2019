import React, { Component } from 'react';
import { connect } from 'react-redux';
import Hackers from './Hackers';
import logo from '../logo.svg';
import { fetchHackers } from '../actions/hackersActions';

class HackersContainer extends Component {
	componentDidMount() {
		this.props.fetchHackers()
	}

	render() {
		return this.props.loading ? (
      <img src={logo} className="App-logo" alt="logo" />
		) : (
			<Hackers
				hackers={this.props.hackers}
				addHacker={this.props.addHacker}
				deleteHacker={this.props.deleteHacker}
			/>
		)
	}
}

const mapStateToProps = (state) => {
  return {
    hackers: state.hackers.data,
    loading: state.hackers.loading
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    deleteHacker: (id) => {
      dispatch({type: 'DELETE_HACKER', id: id })
    },
    addHacker: (hacker) => {
      dispatch({type: 'ADD_HACKER', payload: hacker })
    },
    fetchHackers: () => {
      dispatch(fetchHackers())
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(HackersContainer)
