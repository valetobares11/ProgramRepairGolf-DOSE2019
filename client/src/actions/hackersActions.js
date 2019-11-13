import axios from 'axios';
import {
  ADD_HACKER,
  DELETE_HACKER,
  FETCH_HACKER_REQUEST,
  FETCH_HACKER_SUCCESS,
  FETCH_HACKER_FAILURE,
  FETCH_HACKERS_REQUEST,
  FETCH_HACKERS_SUCCESS,
  FETCH_HACKERS_FAILURE,
} from '../constants/ActionTypes'

export const cretateHacker = (hacker) => {
  return {
    type: ADD_HACKER,
    hacker
  }
}

export const deleteHacker = (id) => {
  return {
    type: DELETE_HACKER,
    id
  }
}

const fetchHackersRequest = () => {
  return {
    type: FETCH_HACKERS_REQUEST
  }
}

const fetchHackersSucess = hackers => {
    return {
        type: FETCH_HACKERS_SUCCESS,
        payload: hackers
    }
}

const fetchHackersFailure = error => {
    return {
        type: FETCH_HACKERS_FAILURE,
        payload: error
    }
}

export const fetchHackers = () => {
  return function(dispatch, getState) {
    if (getState().hackers.data.length === 0) {
      dispatch(fetchHackersRequest())

      axios.get('https://swapi.co/api/people/')
        .then( res =>{
          dispatch(fetchHackersSucess(res.data.results))
        })
        .catch(error => {
          dispatch(fetchHackersFailure(error.message))
        })
    }
  }
}

const fetchHackerRequest = () => {
  return {
    type: FETCH_HACKER_REQUEST
  }
}

const fetchHackerSucess = hacker => {
    return {
        type: FETCH_HACKER_SUCCESS,
        payload: hacker
    }
}

const fetchHackerFailure = error => {
    return {
        type: FETCH_HACKER_FAILURE,
        payload: error
    }
}

export const fetchHacker = (hacker_id) => {
  return function(dispatch) {
    dispatch(fetchHackerRequest())

    axios.get('https://swapi.co/api/people/' + hacker_id)
      .then( res =>{
        dispatch(fetchHackerSucess(res.data))
      })
      .catch(error => {
        dispatch(fetchHackerFailure(error.message))
      })
  }
}
