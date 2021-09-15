import { createAction, handleActions } from "redux-actions";
import produce from 'immer';
import createRequestThunk, { createRequestActionTypes } from "../lib/createRequestThunk";
import * as authApi from '../lib/api/auth';

const CHANGE_FIELD = 'auth/CHANGE_FIELD';
const INITIALIZE_FORM = 'auth/INITIALIZE_FORM';

const [REGISTER, REGISTER_SUCCESS, REGISTER_FAILURE] = createRequestActionTypes(
    'auth/REGISTER'
);
const [LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE] =  createRequestActionTypes(
    'auth/LOGIN'
);

export const changeField = createAction(CHANGE_FIELD, ({form, key, value}) => ({
    form, //register | login
    key, //usernmae | password | passwordConfirm
    value //실제 바꾸려는 값
}));
export const initializeForm = createAction(INITIALIZE_FORM, form => form); //register || login

export const register = createRequestThunk(REGISTER, authApi.register);
export const login = createRequestThunk(LOGIN, authApi.login);

const initialState = {
    register: {
        username: '',
        password: '',
        passwordConfirm: ''
    },
    login: {
        username: '',
        password: ''
    },
    auth: null,
    authError: null,
};

const auth = handleActions(
    {
        [CHANGE_FIELD]: (state, {payload: {form, key, value}}) => 
            produce(state, draft => {
                draft[form][key] = value;
            }
        ),
        [INITIALIZE_FORM]: (state, {payload: form}) => ({
            ...state,
            [form]: initialState[form],
        }),
        [REGISTER_SUCCESS]: (state, {payload: auth}) => ({
            ...state,
            authError: null,
            auth,
        }),
        [REGISTER_FAILURE]: (state, {payload: e}) => ({
            ...state,
            authError: e,
            auth: null,
        }),
        [LOGIN_SUCCESS]: (state, {payload: auth}) => ({
            ...state,
            authError: null,
            auth,
        }),
        [LOGIN_FAILURE]: (state, {payload: e}) => ({
            ...state,
            authError: e,
            auth: null,
        }),
    },
    initialState
);

export default auth;