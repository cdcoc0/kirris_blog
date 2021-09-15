import { createAction, handleActions } from "redux-actions";
import createRequestThunk, { createRequestActionTypes } from "../lib/createRequestThunk";
import * as authAPI from '../lib/api/auth';

const TEMP_SET_USER = 'user/TEMP_SET_USER';
const [CHECK, CHECK_SUCCESS, CHECK_FAILURE] = createRequestActionTypes(
    'user/CHECK'
);
const LOGOUT = 'user/LOGOUT';

export function checkFailure() {
    try {
        localStorage.removeItem('user');
    } catch(e) {
        console.log('error in localStorage');
    }
}

export const tempSetUser = createAction(TEMP_SET_USER, user => user);
export const check = createRequestThunk(CHECK, authAPI.check);
export const logout = createAction(LOGOUT);

export const logoutThunk = () => dispatch => {
    dispatch(logout());
    try {
        authAPI.logout();
        localStorage.removeItem('user');
    } catch(e) {
        console.log(e);
    }
}

const initialState = {
    user: null,
    checkError: null
};

const user = handleActions(
    {
        [TEMP_SET_USER]: (state, {payload: user}) => ({
            ...state,
            user,
        }),
        [CHECK_SUCCESS]: (state, {payload: user}) => ({
            ...state,
            user,
        }),
        [CHECK_FAILURE]: (state, {payload: error}) => {
            checkFailure();
            return ({
            ...state,
            user: null,
            checkError: error
        })},
        [LOGOUT]: state => ({
            ...state,
            user: null
        })
    },
    initialState
);

export default user;