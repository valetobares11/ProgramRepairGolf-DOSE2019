import {
    FETCH_HACKERS_REQUEST,
    FETCH_HACKERS_SUCCESS,
    FETCH_HACKERS_FAILURE,
    ADD_HACKER,
    DELETE_HACKER,
} from '../constants/ActionTypes'

const initHackersState = {
    data: [],
    count: 0,
    loading: false,
    error: ''
}

const hackersReducer = (state = initHackersState, action) => {
    switch(action.type) {
        case FETCH_HACKERS_REQUEST:
            return {
                ...state,
                loading: true
            }

        case FETCH_HACKERS_SUCCESS:
            // aumentating hackers adding 'id'
            const hackers = action.payload.map(hacker => ({
                ...hacker,
                id: hacker.url.slice(0, -1).split('/').pop()
            }))
            return {
                loading: false,
                data: hackers,
                count: hackers.length,
                error: ''
            }

        case FETCH_HACKERS_FAILURE:
            return {
                ...state,
                loading: false,
                data: [],
                count: 0,
                error: action.payload,
            }

        case ADD_HACKER:
            return {
                ...state,
                data: [...state.data, action.payload],
                count: state.count + 1
            }

        case DELETE_HACKER:
            return {
                ...state,
                count: state.count - 1,
                data: state.data.filter(hacker => hacker.id !== action.id)
            }

        default:
            return state
    }
}

export default hackersReducer;
