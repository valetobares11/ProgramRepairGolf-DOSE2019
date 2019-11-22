import { combineReducers } from 'redux'
import usersReducer from './usersReducer'
import userReducer from './userReducer'

// STATE
//
// {
//    hackers: {
//      data: [],
//      count: 0,
//      loading: false,
//      error: ''
//     },
//    hacker: {
//      data: {},
//      loading: false,
//      error: ''
//    }
// }

const rootReducer = combineReducers({
    users: usersReducer,
    user: userReducer,
})

export default rootReducer
