import axios from 'axios';
import {
  CREATE_ACCOUNT,
  DELETE_USER,
  FETCH_USER_REQUEST,
  FETCH_USER_SUCCESS,
  FETCH_USER_FAILURE,
  FETCH_USERS_REQUEST,
  FETCH_USERS_SUCCESS,
  FETCH_USERS_FAILURE,
} from '../../constants/constantsUser/ActionTypes'

export const createAccount = (user) => {
  return {
    type: CREATE_ACCOUNT,
    user
  }
}

export const deleteuser = (id) => {
  return {
    type: DELETE_USER,
    id
  }
}

const fetchUsersRequest = () => {
  return {
    type: FETCH_USERS_REQUEST
  }
}

const fetchUsersSucess = users => {
    return {
        type: FETCH_USERS_SUCCESS,
        payload: users
    }
}

const fetchUsersFailure = error => {
    return {
        type: FETCH_USERS_FAILURE,
        payload: error
    }
}

export const fetchUsers = () => {
  return function(dispatch, getState) {
    if (getState().users.data.length === 0) {
      dispatch(fetchUsersRequest())

      axios.get('http://localhost:55555/user/users')
        .then( res =>{
          console.log(res.data);
          dispatch(fetchUsersSucess(res.data))
        })
        .catch(error => {
          dispatch(fetchUsersFailure(error.message))
        })
    }
  }
}

const fetchUserRequest = () => {
  return {
    type: FETCH_USER_REQUEST
  }
}

const fetchUserSucess = user => {
    return {
        type: FETCH_USER_SUCCESS,
        payload: user
    }
}

const fetchUserFailure = error => {
    return {
        type: FETCH_USER_FAILURE,
        payload: error
    }
}
export const newAccount = (user, pass, email) =>{
  return function(dispatch) {
    dispatch(fetchUserRequest())
    console.log(user);
    console.log(pass);
     fetch('http://localhost:55555/user/signUp', {
      method: 'POST',
       body:JSON.stringify({'username': user, 'password':pass, 'email_address':email})
      })
     .then( res => {
      console.log(res);
        dispatch(fetchUserSucess(res.data))
     })
     .catch( error => {
      console.log(error);
        dispatch(fetchUserFailure(error.message))
     })
  }
}
export const login = (user, pass) =>{
  return function(dispatch) {
    dispatch(fetchUserRequest())
    console.log(user);
    console.log(pass);
     fetch('http://localhost:55555/user/login', {
      method: 'POST',
      body:JSON.stringify({'username': user, 'password':pass})
      })
     .then( res => {
      console.log(res);
      dispatch(fetchUserSucess(res.data))
     })
     .catch( error => {
      console.log(error);
      dispatch(fetchUserFailure(error.message))
     })
  }
}
export const fetchUser = (user_id) => {
  return function(dispatch) {
    dispatch(fetchUserRequest())

    axios.get('https://swapi.co/api/people/' + user_id)
      .then( res =>{
        dispatch(fetchUserSucess(res.data))
      })
      .catch(error => {
        dispatch(fetchUserFailure(error.message))
      })
  }
}
