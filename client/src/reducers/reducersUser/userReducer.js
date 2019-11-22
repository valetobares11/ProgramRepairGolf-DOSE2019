    import {
    FETCH_USER_REQUEST,
    FETCH_USER_SUCCESS,
    FETCH_USER_FAILURE,
} from '../../constants/constantsUser/ActionTypes'

const initUserState = {
    data: {},
    loading: false,
    error: ''
}

const userReducer = (state = initUserState, action) => {
    switch(action.type) {
        case FETCH_USER_REQUEST:
            return {
                ...state,
                loading: true
            }

        case FETCH_USER_SUCCESS:
            // aumentating USER adding 'id'
            /*const user = {
               ...action.payload,
                id: action.payload.username.slice(0, -1).split('/').pop()
            }*/
            return {
                ...state,
                loading: false,
                data: action.payload,
                error: ''
            }

        case FETCH_USER_FAILURE:
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

export default userReducer;
