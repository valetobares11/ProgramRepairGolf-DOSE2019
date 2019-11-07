import { combineReducers } from 'redux'
import hackersReducer from './hackersReducer'
import hackerReducer from './hackerReducer'

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
    hackers: hackersReducer,
    hacker: hackerReducer,
})

export default rootReducer
