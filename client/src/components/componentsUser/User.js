import React, { Component } from 'react'
import { connect } from 'react-redux';
import { fetchUsers }  from '../../actions/actionsUser/usersActions';

class User extends Component {
  componentDidMount() {
    this.props.fetchUser(this.props.match.params.user_id)
  }

  render() {
    const { user } = this.props
    return (
      user ? (
        <div className="container">
          <h1>You're the user</h1>
          <h2>#{this.props.user.id}</h2>
          <h4>{this.props.user.name}</h4>
        </div>
      ) : (
        <div>There is no user in the store... refetching</div>
      )
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    user: state.user.data
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchUsers: (id) => {
      dispatch(fetchUsers(id))
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(User)
