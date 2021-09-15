import createRequestThunk, { createRequestActionTypes } from "../lib/createRequestThunk";
import * as postsAPI from '../lib/api/posts';
import { createAction, handleActions } from "redux-actions";

const [READ_POST, READ_POST_SUCCESS, READ_POST_FAILURE] = createRequestActionTypes('post/READ_POST');
const UNLOAD_POST = 'post/UNLOAD_POST';

export const readPost = createRequestThunk(READ_POST, postsAPI.readPost);
export const unloadPost = createAction(UNLOAD_POST);

const initialState = {
    post: null,
    error: null,
};

const post = handleActions(
    {
        [READ_POST_SUCCESS]: (state, {payload: post}) => ({
            ...state,
            post
        }),
        [READ_POST_FAILURE]: (state, {payload: error}) => ({
            ...state,
            error
        }),
        [UNLOAD_POST]: () => initialState
    },
    initialState
);

export default post;