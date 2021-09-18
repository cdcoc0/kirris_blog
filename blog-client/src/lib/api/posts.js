import client from './client';
import qs from 'qs';

export const writePost = ({title, body, tags}) => 
    client.post('/auth', {title, body, tags});

export const readPost = id => client.get(`/api/posts/${id}`);

export const listPosts = ({tag, username, page}) => {
    const queryString = qs.stringify({
        tag,
        username,
        page,
    });
    return client.get(`/api/posts?${queryString}`);
}

export const updatePost = ({id, title, body, tags}) => 
    client.put(`/auth/own/${id}`, {
        title,
        body, 
        tags
    })

export const removePost = id => client.delete(`/auth/own/${id}`);