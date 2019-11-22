import {
    FETCH_USERS_REQUEST,
    FETCH_USERS_SUCCESS,
    FETCH_USERS_FAILURE,
    CREATE_ACCOUNT,
    DELETE_USER,
    LOGIN,
} from '../../constants/constantsUser/ActionTypes'

const initUsersState = {
    data: [],
    count: 0,
    loading: false,
    error: ''
}

const usersReducer = (state = initUsersState, action) => {
    switch(action.type) {
        case FETCH_USERS_REQUEST:
            return {
                ...state,
                loading: true
            }

        case FETCH_USERS_SUCCESS:
            // aumentating users adding 'id'
            console.log("reducer");
            
            const users = action.payload.map(user => ({
                name: user,
                id: 1
            }))
            console.log(users);
            return {
                loading: false,
                data: users,
                count: users.length,
                error: ''
            }

        case FETCH_USERS_FAILURE:
            return {
                ...state,
                loading: false,
                data: [],
                count: 0,
                error: action.payload,
            }

        case CREATE_ACCOUNT:
            return {
                ...state,
                data: [...state.data, action.payload],
                count: state.count + 1
            }
        case LOGIN:
            return {
                ...state,
                data: [...state.data, action.payload],
                count: state.count + 1
            }

        case DELETE_USER:
            return {
                ...state,
                count: state.count - 1,
                data: state.data.filter(user => user.id !== action.id)
            }

        default:
            return state
    }
}

export default usersReducer;
