import {
    FETCH_HACKER_REQUEST,
    FETCH_HACKER_SUCCESS,
    FETCH_HACKER_FAILURE,
} from '../constants/ActionTypes'

const initHackerState = {
    data: {},
    loading: false,
    error: ''
}

const hackerReducer = (state = initHackerState, action) => {
    switch(action.type) {
        case FETCH_HACKER_REQUEST:
            return {
                ...state,
                loading: true
            }

        case FETCH_HACKER_SUCCESS:
            // aumentating hacker adding 'id'
            const hacker = {
                ...action.payload,
                id: action.payload.url.slice(0, -1).split('/').pop()
            }
            return {
                ...state,
                loading: false,
                data: hacker,
                error: ''
            }

        case FETCH_HACKER_FAILURE:
            return {
                ...state,
                loading: false,
                data: {},
                error: action.payload,
            }

        default:
            return state
    }
}

export default hackerReducer;
