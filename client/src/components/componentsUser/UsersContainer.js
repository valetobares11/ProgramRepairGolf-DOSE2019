import React, { Component } from 'react';
import { connect } from 'react-redux';
import Users from './Users';
import logo from '../../logo.svg';
import { fetchUsers, newAccount, login}  from '../../actions/actionsUser/usersActions';


class UsersContainer extends Component {
	componentDidMount() {
		this.props.fetchUsers()  
	}

	render() {
		return this.props.loading ? (
      <img src={logo} className="App-logo" alt="logo" />
		) : (
			<Users
				users={this.props.users}
				newAccount={this.props.newAccount}
				deleteUser={this.props.deleteUser}
        login={this.props.login}
			/>
		)
	}
}

const mapStateToProps = (state) => {
  return {
    users: state.users.data,
    loading: state.users.loading
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    deleteuser: (id) => {
      dispatch({type: 'DELETE_USER', id: id })
    },
    newAccount: (user, pass, email) => {
      dispatch(newAccount(user, pass, email))
    },
    fetchUsers: () => {
      dispatch(fetchUsers())
    },
    login: (user, pass) => {
      dispatch(login(user, pass))
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(UsersContainer)
